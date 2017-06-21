package pres.wisdom.controller.sale;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import pres.wisdom.dao.AccountDetailMapper;
import pres.wisdom.dao.AccountMapper;
import pres.wisdom.dao.GoodsMapper;
import pres.wisdom.dao.SaleDetailMapper;
import pres.wisdom.dao.SaleMapper;
import pres.wisdom.dao.StockMapper;
import pres.wisdom.entity.Account;
import pres.wisdom.entity.AccountDetail;
import pres.wisdom.entity.Goods;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Sale;
import pres.wisdom.entity.SaleDetail;
import pres.wisdom.entity.SaleDetailPage;
import pres.wisdom.entity.SalePage;
import pres.wisdom.entity.Stock;
import pres.wisdom.entity.StockPage;
import pres.wisdom.util.Token;
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/sale")
@SessionAttributes("page")
public class SaleController {
	@Resource
	private SaleMapper saleMapper;

	public void setSaleMapper(SaleMapper saleMapper) {
		this.saleMapper = saleMapper;
	}
	
	@Resource
	private GoodsMapper goodsMapper;

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}
	
	@Resource
	private StockMapper stockMapper;

	public void setStockMapper(StockMapper stockMapper) {
		this.stockMapper = stockMapper;
	}
	
	@Resource
	private SaleDetailMapper saleDetailMapper;
	
	public void setSaleDetailMapper(SaleDetailMapper saleDetailMapper) {
		this.saleDetailMapper = saleDetailMapper;
	}
	
	@Resource
	private AccountMapper accountMapper;

	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}
	
	@Resource
	private AccountDetailMapper accountDetailMapper;

	public void setAccountDetailMapper(AccountDetailMapper accountDetailMapper) {
		this.accountDetailMapper = accountDetailMapper;
	}

	// 对应/sale/saleSlipList/{type}/{customer}/{createTime}/{state}/{page}请求
	// 对应查询销售单列表
	@RequestMapping(value = "/saleSlipList/{type}/{customer}/{createTime}/{state}/{page}", method = RequestMethod.GET)
	public String saleExecute(
			@PathVariable("type") Integer type,
			@PathVariable("customer") String customer,
			@PathVariable("createTime") String createTime,
			@PathVariable("state") Integer state,
			@PathVariable("page") Integer p,
			Model model) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SalePage page = new SalePage();
		page.setType(type);
		page.setCustomer(customer);
		page.setState(state);
		page.setPage(p);
		if(!createTime.equals("*")){
			page.setCreateTime(sdf.parse(createTime));
		}
		List<SalePage> list = saleMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = saleMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		//将条件*号清空
		page.setCustomer(convert(page.getCustomer()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("saleSlip", list);
		model.addAttribute("totalRows", totalRows);
		return "/sale/saleSlipList";// 进入saleSlipList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}
	
	// 根据客户查询最大销售单单号
	public String findMaxNo(Integer customerId) {
		// 查询客户首字母大写
		String pinYin = saleMapper.findPinYin(customerId);
		// 查询当前日期，20140101
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);
		// 根据客户首字母和日期，查询客户当天是否有单据，
		String preNo = pinYin + today;
		SalePage page = new SalePage();
		page.setSaleNum(preNo);
		page.setCustomerId(customerId);
		page.setType(0);
		// 没有则001，有则取最大单号
		String maxNo = saleMapper.findMaxNo(page);
		if (maxNo == null || maxNo.length() <= 0) {
			System.out.println(preNo);
			maxNo = preNo + "001";
		} else {
			String lastNo = maxNo.substring(maxNo.length() - 3, maxNo.length());
			int no = Integer.parseInt(lastNo) + 1;
			maxNo = preNo + new DecimalFormat("000").format(no);
		}
		return maxNo;
	}
		
	// 对应/sale/saleStock/{goodsId}请求
	// 对应查询库存列表
	@RequestMapping(value = "/saleStock/{goodsId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchStock(
			@PathVariable("goodsId") Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
		Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
		if(stockPage == null || stockPage.equals("")){
			map.put("goodsNum", 0);
		}else{
			map.put("goodsNum", stockPage.getNum());
		}
		map.put("goodsPrice", goods.getPrice().toString());
		return map;
	}
	
	// 销售检查库存是否充足
	public boolean checkStock(Integer goodsId, Integer goodsNumber) {
		StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
		if (stockPage.getNum() >= goodsNumber) {
			return true;
		} else {
			return false;
		}
	}
	
	//保存
	@Token(remove=true)
	@RequestMapping(method = RequestMethod.POST, value = "/saleDetailSave")
	@ResponseBody
	public Map<String, Object> saleAdd(
			Integer[] goodsId,
			Integer[] number, 
			BigDecimal[] goodsPrice, 
			BigDecimal[] realPrice, 
			String[] remark, 
			String mainRemark,
			Integer customerId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询当前时间
		Date date = new Date();
		// 调用查询最大单号
		String maxNo = findMaxNo(customerId);

		Operator operator = (Operator) session.getAttribute("user");

		// 将数据汇总，插入主表
		Sale sale = new Sale();
		Integer sumSaleNumber = 0;
		BigDecimal sumSalePrice = new BigDecimal("0");
		for (int i = 0; i < goodsId.length; i++) {
			sumSaleNumber += number[i];
			sumSalePrice = sumSalePrice.add(new BigDecimal(sumSaleNumber).multiply(realPrice[i]));
		}
		sale.setSaleNum(maxNo);
		sale.setCustomerId(customerId);
		sale.setSumNumber(sumSaleNumber);
		sale.setSumPrice(sumSalePrice);
		sale.setCreateTime(date);
		sale.setCreateId(operator.getId());
		sale.setType(0);
		sale.setState(0);
		sale.setRemark(mainRemark);
		saleMapper.insert(sale);

		// 插入明细表
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setSaleNum(maxNo);
		boolean flag = false;
		for (int i = 0; i < goodsId.length; i++) {
			saleDetail.setCustomerId(customerId);
			saleDetail.setGoodsId(goodsId[i]);
			saleDetail.setNumber(number[i]);
			saleDetail.setPrice(goodsPrice[i]);
			saleDetail.setRealPrice(realPrice[i]);
			if(remark.length != 0){
				saleDetail.setRemark(remark[i]);
			}
			saleDetail.setType(0);
			flag = checkStock(goodsId[i], number[i]);
			if (flag) {
				saleDetailMapper.insert(saleDetail);
				map.put("success", true);
			} else {
				map.put("fail", "库存不足，请检查库存/销售商品数量!");
			}
		}
		return map;
	}
	
	// 删除单据
	@RequestMapping("/saleDelete/{saleId}")
	@ResponseBody
	@Transactional
	public boolean saleDelete(
			@PathVariable("saleId") Integer saleId) {
		// 获取主表信息
		SalePage salePage = saleMapper.selectBySaleId(saleId);
		int sale_flag = saleMapper.deleteByPrimaryKey(saleId);
		int saleDetail_flag = saleDetailMapper.deleteSaleDetail(salePage);
		if(sale_flag > 0 && saleDetail_flag > 0){
			return true;
		}else{
			return false;
		}
	}
	
	// 对应/sale/saleState/1请求
	// 用于查询单据状态
	@RequestMapping("/saleState/{saleId}")
	@ResponseBody
	public Integer checkSaleState(@PathVariable("saleId") Integer saleId) {
		int sate = saleMapper.selectStateByPrimaryKey(saleId);
		return sate;
	}
	
	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> saleUpdateState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			saleMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "sale/saleSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}

	//更新反审状态
	@RequestMapping(value="/updateBackState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> saleUpdateBackState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			saleMapper.updateBackStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("backState", true);
		map.put("servlet", "sale/saleSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
	
	//出库操作
	@RequestMapping("/saleReview/{saleId}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saleReview(
			@PathVariable("saleId") Integer saleId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取主表信息
		SalePage salePage = saleMapper.selectBySaleId(saleId);
		// 获取明细表信息
		List<SaleDetailPage> saleDetails = saleDetailMapper.selectBySale(salePage);
		boolean check_flag = false;
		boolean stock_flag = false;
		for (SaleDetailPage detail : saleDetails) {
			Integer goodsId = detail.getGoodsId();
			Integer goodsNumber = detail.getNumber();
			StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
			Stock stock = new Stock();
			
			check_flag = checkStock(goodsId, goodsNumber);
			if (check_flag) {
				stock.setGoodsId(goodsId);
				stock.setNum(stockPage.getNum() - detail.getNumber());
				stockMapper.updateByGoodsId(stock);
				map.put("stockSuccess", true);
				stock_flag = true;
			} else {
				map.put("fail", "库存不足，请检查销售商品数量!");
				stock_flag =false;
			}
		}
		if(stock_flag){
			// 查询当前时间
			Date date = new Date();
			Operator operator = (Operator) session.getAttribute("user");
			Sale sale = new Sale();
			sale.setId(saleId);
			sale.setReviewId(operator.getId());
			sale.setReviewTime(date);
			saleMapper.updateReviewStateByPrimaryKey(sale);
			Page page = (Page) session.getAttribute("page");
			map.put("servlet", "sale/saleSlipList/0/*/*/-1/" + page.getPage());
		}
		return map;
	}

	// 财务操作
	@RequestMapping("/saleAccount/{saleId}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public boolean saleAccount(
		@PathVariable("saleId") Integer saleId,
		HttpSession session) {
	// 获取主表信息
	SalePage salePage = saleMapper.selectBySaleId(saleId);
	// 获取明细表信息
	List<SaleDetailPage> saleDetails = saleDetailMapper.selectBySale(salePage);

	Account accountMain = accountMapper.selectByClientId(salePage.getCustomerId());

	BigDecimal sumSalePrice = new BigDecimal("0");

	AccountDetail accountDetail = null;

	// 查询当前时间
	Date date = new Date();

	for (SaleDetailPage detail : saleDetails) {
		BigDecimal goodsNumber = new BigDecimal(detail.getNumber());
		BigDecimal goodsPrice = goodsNumber.multiply(detail.getRealPrice());
		sumSalePrice = sumSalePrice.add(goodsPrice);
		accountDetail = new AccountDetail();
		accountDetail.setClientId(salePage.getCustomerId());
		accountDetail.setType(0);
		accountDetail.setListId(saleId);
		accountDetail.setListNum(salePage.getSaleNum());
		accountDetail.setListType(0);
		accountDetail.setListPrice(goodsPrice);
		accountDetail.setListNumber(detail.getNumber());
		accountDetail.setCreateTime(salePage.getCreateTime());
		accountDetail.setAccountTime(date);
		accountDetailMapper.insert(accountDetail);
		}
		// 更新余额表
		Account account = new Account();
		account.setClientId(salePage.getCustomerId());
		account.setType(0);
		// 余额表存在此供应商则更新；不存在则插入
		if (accountMain == null || accountMain.equals("")) {
			account.setBalance(sumSalePrice);
			accountMapper.insert(account);
		} else {
			sumSalePrice = sumSalePrice.add(accountMain.getBalance());
			account.setBalance(sumSalePrice);
			account.setId(accountMain.getId());
			accountMapper.updateByPrimaryKey(account);
		}
	
		// 更新销售单状态为财务审核
		Operator operator = (Operator) session.getAttribute("user");
		Sale sale = new Sale();
		sale.setId(saleId);
		sale.setAccountId(operator.getId());
		sale.setAccountTime(date);
		saleMapper.updateAccountStateByPrimaryKey(sale);
		return true;
	}
	
	// 修改单据信息操作
	@RequestMapping(value = "/saleSlipEdit/toEdit", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String,Object> saleEdit(
			Integer[] goodsId,
			Integer[] number, 
			BigDecimal[] goodsPrice, 
			BigDecimal[] realPrice, 
			String[] remark, 
			String mainRemark,
			Integer customerId,
			Integer saleId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询当前时间
		Date date = new Date();
		Operator operator = (Operator) session.getAttribute("user");
		// 查询原单据客户ID
		SalePage oldSale = saleMapper.selectBySaleId(saleId);
		Sale newSale = new Sale();
		// 调用查询最大单号
		String maxNo = "";
		if (oldSale.getCustomerId() != customerId) {
			maxNo = findMaxNo(customerId);
			newSale.setSaleNum(maxNo);
		} else {
			maxNo = oldSale.getSaleNum();
			newSale.setSaleNum(maxNo);
		}
		Integer sumSaleNumber = 0;
		BigDecimal sumSalePrice = new BigDecimal("0");
		for (int i = 0; i < goodsId.length; i++) {
			sumSaleNumber += number[i];
			sumSalePrice = sumSalePrice.add(new BigDecimal(sumSaleNumber).multiply(realPrice[i]));
		}
		// 删除主表
		saleMapper.deleteByPrimaryKey(saleId);
		// 将数据汇总，插入主表
		newSale.setCreateId(oldSale.getCreateId());
		newSale.setCreateTime(oldSale.getCreateTime());
		newSale.setCustomerId(customerId);
		newSale.setSumNumber(sumSaleNumber);
		newSale.setSumPrice(sumSalePrice);
		newSale.setUpdateTime(date);
		newSale.setUpdateId(operator.getId());
		newSale.setType(0);
		newSale.setState(0);
		newSale.setRemark(mainRemark);
		saleMapper.insert(newSale);

		// 删除明细表
		saleDetailMapper.deleteSaleDetail(oldSale);
		// 插入明细表
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setSaleNum(maxNo);
		boolean flag = false;
		for (int i = 0; i < goodsId.length; i++) {
			saleDetail.setCustomerId(customerId);
			saleDetail.setGoodsId(goodsId[i]);
			saleDetail.setNumber(number[i]);
			saleDetail.setType(0);
			saleDetail.setPrice(goodsPrice[i]);
			saleDetail.setRealPrice(realPrice[i]);
			if(remark.length != 0){
				saleDetail.setRemark(remark[i]);
			}
			flag = checkStock(goodsId[i], number[i]);
			if (flag) {
				saleDetailMapper.insert(saleDetail);
				map.put("edit", true);
			} else {
				map.put("fail", "库存不足，请检查库存/销售商品数量!");
			}
		}
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("servlet", "sale/saleSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
}