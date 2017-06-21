package pres.wisdom.controller.saleBack;

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
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/saleBack")
@SessionAttributes("page")
public class SaleBackController {
	@Resource
	private SaleMapper saleMapper;

	public void setSaleMapper(SaleMapper saleMapper) {
		this.saleMapper = saleMapper;
	}
	
	@Resource
	private SaleDetailMapper saleDetailMapper;

	public void setSaleDetailMapper(SaleDetailMapper saleDetailMapper) {
		this.saleDetailMapper = saleDetailMapper;
	}
	
	@Resource
	private StockMapper stockMapper;

	public void setStockMapper(StockMapper stockMapper) {
		this.stockMapper = stockMapper;
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
	
	@Resource
	private GoodsMapper goodsMapper;

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	// 对应/saleBack/saleBackSlipList/{type}/{customer}/{createTime}/{state}/{page}请求
	// 对应查询退货单列表
	@RequestMapping(value = "/saleBackSlipList/{type}/{customer}/{createTime}/{state}/{page}", method = RequestMethod.GET)
	public String saleBackExecute(
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
		model.addAttribute("saleBackSlip", list);
		model.addAttribute("totalRows", totalRows);
		return "/saleBack/saleBackSlipList";// 进入saleBackSlipList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}
	
	// 根据客户查询最大退货单单号
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
		page.setType(1);
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
	
	//检查指定客户退货数是否大于销售数
	public Integer checkSaleNum(Integer customerId, Integer goodsId){
		//查询指定客户销售数量
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setCustomerId(customerId);
		saleDetail.setGoodsId(goodsId);
		saleDetail.setType(0);
		int saleNum = saleDetailMapper.selectBySaleNum(saleDetail);
		//查询指定客户退货数量
		SaleDetail saleBackDetail = new SaleDetail();
		saleBackDetail.setCustomerId(customerId);
		saleBackDetail.setGoodsId(goodsId);
		saleBackDetail.setType(1);
		int saleBackNum = saleDetailMapper.selectBySaleNum(saleBackDetail);
		return saleNum - saleBackNum;
	}
	
	// 对应/saleBack/saleNum/{customerId}/{goodsId}请求
	// 对应查询销售列表
	@RequestMapping(value = "/saleNum/{customerId}/{goodsId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> stockExecute(
			@PathVariable("customerId") Integer customerId,
			@PathVariable("goodsId") Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleNum", checkSaleNum(customerId, goodsId));
		Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
		map.put("goodsPrice", goods.getPrice().toString());
		return map;
	}
	
	//保存
	@RequestMapping(method = RequestMethod.POST, value = "/saleBackDetailSave")
	@ResponseBody
	public Map<String, Object> saleBackAdd(
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
		sale.setType(1);
		sale.setState(0);
		sale.setRemark(mainRemark);
		saleMapper.insert(sale);
		int saleNum = 0;
		// 插入明细表
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setSaleNum(maxNo);
		for (int i = 0; i < goodsId.length; i++) {
			saleDetail.setCustomerId(customerId);
			saleDetail.setGoodsId(goodsId[i]);
			saleDetail.setNumber(number[i]);
			saleDetail.setPrice(goodsPrice[i]);
			saleDetail.setRealPrice(realPrice[i]);
			if(remark.length != 0){
				saleDetail.setRemark(remark[i]);
			}
			saleDetail.setType(1);
			saleNum = checkSaleNum(customerId, goodsId[i]);
			if (number[i] <= saleNum) {
				saleDetailMapper.insert(saleDetail);
				map.put("success", true);
			} else {
				map.put("fail", "超过可退数量，请检查该客户的销售数!");
			}
		}
		return map;
	}
		
	// 删除单据
	@RequestMapping("/saleBackDelete/{saleBackId}")
	@ResponseBody
	@Transactional
	public boolean saleBackDelete(
			@PathVariable("saleBackId") Integer saleBackId) {
		// 获取主表信息
		SalePage salePage = saleMapper.selectBySaleBackId(saleBackId);
		int saleBack_flag = saleMapper.deleteByPrimaryKey(saleBackId);
		int saleBackDetail_flag = saleDetailMapper.deleteSaleDetail(salePage);
		if(saleBack_flag > 0 && saleBackDetail_flag > 0){
			return true;
		}else{
			return false;
		}
	}
	
	// 对应/saleBack/saleBackState/1请求
	// 用于查询单据状态
	@RequestMapping("/saleBackState/{saleBackId}")
	@ResponseBody
	public Integer checkSaleBackState(@PathVariable("saleBackId") Integer saleBackId) {
		int sate = saleMapper.selectStateByPrimaryKey(saleBackId);
		return sate;
	}
	
	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> saleBackUpdateState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			saleMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "saleBack/saleBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}

	//更新反审状态
	@RequestMapping(value="/updateBackState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> saleBackUpdateBackState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			saleMapper.updateBackStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("backState", true);
		map.put("servlet", "saleBack/saleBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}
	
	//入操作
	@RequestMapping("/saleBackReview/{saleBackId}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saleBackReview(
			@PathVariable("saleBackId") Integer saleBackId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取主表信息
		SalePage salePage = saleMapper.selectBySaleBackId(saleBackId);
		// 获取明细表信息
		List<SaleDetailPage> saleDetails = saleDetailMapper.selectBySale(salePage);
		for (SaleDetailPage detail : saleDetails) {
			Integer goodsId = detail.getGoodsId();
			StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
			Stock stock = new Stock();
			stock.setGoodsId(goodsId);
			stock.setNum(stockPage.getNum() + detail.getNumber());
			stockMapper.updateByGoodsId(stock);
		}
		// 查询当前时间
		Date date = new Date();
		Operator operator = (Operator) session.getAttribute("user");
		Sale sale = new Sale();
		sale.setId(saleBackId);
		sale.setReviewId(operator.getId());
		sale.setReviewTime(date);
		saleMapper.updateReviewStateByPrimaryKey(sale);
		Page page = (Page) session.getAttribute("page");
		map.put("reviewSuccess", true);
		map.put("servlet", "saleBack/saleBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}

	// 财务操作
	@RequestMapping("/saleBackAccount/{saleBackId}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public boolean saleBackAccount(
		@PathVariable("saleBackId") Integer saleBackId,
		HttpSession session) {
		// 获取主表信息
		SalePage salePage = saleMapper.selectBySaleBackId(saleBackId);
		// 获取明细表信息
		List<SaleDetailPage> saleDetails = saleDetailMapper.selectBySale(salePage);
		
		Account accountMain = accountMapper.selectByClientId(salePage.getCustomerId());
		
		BigDecimal sumSaleBackPrice = new BigDecimal("0");
		
		AccountDetail accountDetail = null;
		
		// 查询当前时间
		Date date = new Date();
		
		for (SaleDetailPage detail : saleDetails) {
			BigDecimal goodsNumber = new BigDecimal(detail.getNumber());
			BigDecimal goodsPrice = goodsNumber.multiply(detail.getRealPrice());
			sumSaleBackPrice = sumSaleBackPrice.add(goodsPrice);
			accountDetail = new AccountDetail();
			accountDetail.setClientId(salePage.getCustomerId());
			accountDetail.setType(0);
			accountDetail.setListId(saleBackId);
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
		sumSaleBackPrice = accountMain.getBalance().subtract(sumSaleBackPrice);
		account.setBalance(sumSaleBackPrice);
		account.setId(accountMain.getId());
		accountMapper.updateByPrimaryKey(account);
	
		// 更新销售单状态为财务审核
		Operator operator = (Operator) session.getAttribute("user");
		Sale sale = new Sale();
		sale.setId(saleBackId);
		sale.setAccountId(operator.getId());
		sale.setAccountTime(date);
		saleMapper.updateAccountStateByPrimaryKey(sale);
		return true;
	}

	// 修改单据信息操作
	@RequestMapping(value = "/saleBackSlipEdit/toEdit", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String,Object> saleBackEdit(
			Integer[] goodsId,
			Integer[] number, 
			BigDecimal[] goodsPrice, 
			BigDecimal[] realPrice, 
			String[] remark, 
			String mainRemark,
			Integer customerId,
			Integer saleBackId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询当前时间
		Date date = new Date();
		Operator operator = (Operator) session.getAttribute("user");
		// 查询原单据客户ID
		SalePage oldSaleBack = saleMapper.selectBySaleBackId(saleBackId);
		Sale newSale = new Sale();
		// 调用查询最大单号
		String maxNo = "";
		if (oldSaleBack.getCustomerId() != customerId) {
			maxNo = findMaxNo(customerId);
			newSale.setSaleNum(maxNo);
		} else {
			maxNo = oldSaleBack.getSaleNum();
			newSale.setSaleNum(maxNo);
		}
		Integer sumSaleNumber = 0;
		BigDecimal sumSalePrice = new BigDecimal("0");
		for (int i = 0; i < goodsId.length; i++) {
			sumSaleNumber += number[i];
			sumSalePrice = sumSalePrice.add(new BigDecimal(sumSaleNumber).multiply(realPrice[i]));
		}
		// 删除主表
		saleMapper.deleteByPrimaryKey(saleBackId);
		// 将数据汇总，插入主表
		newSale.setCreateId(oldSaleBack.getCreateId());
		newSale.setCreateTime(oldSaleBack.getCreateTime());
		newSale.setCustomerId(customerId);
		newSale.setSumNumber(sumSaleNumber);
		newSale.setSumPrice(sumSalePrice);
		newSale.setUpdateTime(date);
		newSale.setUpdateId(operator.getId());
		newSale.setType(1);
		newSale.setState(0);
		newSale.setRemark(mainRemark);
		saleMapper.insert(newSale);

		// 删除明细表
		saleDetailMapper.deleteSaleDetail(oldSaleBack);
		// 插入明细表
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setSaleNum(maxNo);
		int saleNum = 0;
		for (int i = 0; i < goodsId.length; i++) {
			saleDetail.setCustomerId(customerId);
			saleDetail.setGoodsId(goodsId[i]);
			saleDetail.setNumber(number[i]);
			saleDetail.setType(1);
			saleDetail.setPrice(goodsPrice[i]);
			saleDetail.setRealPrice(realPrice[i]);
			if(remark.length != 0){
				saleDetail.setRemark(remark[i]);
			}
			saleNum = checkSaleNum(customerId, goodsId[i]);
			if (number[i] <= saleNum) {
				saleDetailMapper.insert(saleDetail);
				map.put("edit", true);
			} else {
				map.put("fail", "超过可退数量，请检查该客户的销售数!");
			}
		}
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("servlet", "saleBack/saleBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}
}