package pres.wisdom.controller.inportBack;

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
import pres.wisdom.dao.InportBackDetailMapper;
import pres.wisdom.dao.InportBackMapper;
import pres.wisdom.dao.StockMapper;
import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.Account;
import pres.wisdom.entity.AccountDetail;
import pres.wisdom.entity.Goods;
import pres.wisdom.entity.Inport;
import pres.wisdom.entity.InportDetail;
import pres.wisdom.entity.InportDetailPage;
import pres.wisdom.entity.InportPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Stock;
import pres.wisdom.entity.StockPage;
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/inportBack")
@SessionAttributes("page")
public class InportBackController {
	@Resource
	private InportBackMapper inportBackMapper;

	public void setInportMapper(InportBackMapper inportBackMapper) {
		this.inportBackMapper = inportBackMapper;
	}

	@Resource
	private InportBackDetailMapper inportBackDetailMapper;

	public void setInportBackDetailMapper(InportBackDetailMapper inportBackDetailMapper) {
		this.inportBackDetailMapper = inportBackDetailMapper;
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
	private SupplierMapper supplierMapper;

	public void setSupplierMapper(SupplierMapper supplierMapper) {
		this.supplierMapper = supplierMapper;
	}

	@Resource
	private GoodsMapper goodsMapper;

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	// 对应/inportBack/inportBackSlipList/{type}/{supplier}/{createTime}/{state}/{page}请求
	// 对应查询退厂单列表
	@RequestMapping(value = "/inportBackSlipList/{type}/{supplier}/{createTime}/{state}/{page}", method = RequestMethod.GET)
	public String inportBackExecute(@PathVariable("type") Integer type,
			@PathVariable("supplier") String supplier,
			@PathVariable("createTime") String createTime,
			@PathVariable("state") Integer state,
			@PathVariable("page") Integer p, Model model) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		InportPage page = new InportPage();
		page.setType(type);
		page.setSupplier(supplier);
		page.setState(state);
		page.setPage(p);
		if (!createTime.equals("*")) {
			page.setCreateTime(sdf.parse(createTime));
		}
		List<InportPage> list = inportBackMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = inportBackMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		// 将条件*号清空
		page.setSupplier(convert(page.getSupplier()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("inportBackSlip", list);
		model.addAttribute("totalRows", totalRows);
		return "/inportBack/inportBackSlipList";// 进入inportBackSlipList.jsp
	}

	public String convert(Object o) {
		if ("*".equals(o.toString())) {
			return "";
		} else {
			return o.toString();
		}
	}

	// 根据供应商查询最大入库单单号
	public String findMaxNo(Integer supplierId) {
		// 查询客户首字母大写
		String pinYin = inportBackMapper.findPinYin(supplierId);
		// 查询当前日期，20140101
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);
		// 根据供应商首字母和日期，查询供应商当天是否有单据，
		String preNo = pinYin + today;
		InportPage page = new InportPage();
		page.setInportNum(preNo);
		page.setSupplierId(supplierId);
		page.setType(1);
		// 没有则001，有则取最大单号
		String maxNo = inportBackMapper.findMaxNo(page);
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
	// 退厂检查库存是否充足
	public boolean checkStock(Integer goodsId, Integer goodsNumber) {
		StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
		if (stockPage.getNum() >= goodsNumber) {
			return true;
		} else {
			return false;
		}
	}

	//保存
	@RequestMapping(method = RequestMethod.POST, value = "/inportBackDetailSave")
	@ResponseBody
	public Map<String, Object> inportBackAdd(
			Integer[] goodsId,
			Integer[] number, 
			String[] remark, 
			String mainRemark,
			Integer supplierId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询当前时间
		Date date = new Date();
		// 调用查询最大单号
		String maxNo = findMaxNo(supplierId);

		Operator operator = (Operator) session.getAttribute("user");

		// 将数据汇总，插入主表
		Inport inport = new Inport();
		Integer sumInportNumber = 0;
		for (int i = 0; i < goodsId.length; i++) {
			sumInportNumber += number[i];
		}
		inport.setInportNum(maxNo);
		inport.setSupplierId(supplierId);
		inport.setSumNumber(sumInportNumber);
		inport.setCreateTime(date);
		inport.setCreateId(operator.getId());
		inport.setType(1);
		inport.setState(0);
		inport.setRemark(mainRemark);
		inportBackMapper.insert(inport);

		// 插入明细表
		InportDetail inportBackDetail = new InportDetail();
		inportBackDetail.setInportNum(maxNo);
		boolean flag = false;
		for (int i = 0; i < goodsId.length; i++) {
			inportBackDetail.setGoodsId(goodsId[i]);
			inportBackDetail.setNumber(number[i]);
			if(remark.length != 0){
				inportBackDetail.setRemark(remark[i]);
			}
			inportBackDetail.setSupplierId(supplierId);
			inportBackDetail.setType(1);
			flag = checkStock(goodsId[i], number[i]);
			if (flag) {
				inportBackDetailMapper.insert(inportBackDetail);
				map.put("success", true);
			} else {
				map.put("fail", "库存不足，请检查库存/退厂商品数量!");
			}
		}
		return map;
	}

	// 对应/inportBack/inportBackState/1请求
	// 用于查询单据状态
	@RequestMapping("/inportBackState/{inportId}")
	@ResponseBody
	public Integer checkInportBackState(@PathVariable("inportId") Integer inportId) {
		int sate = inportBackMapper.selectStateByPrimaryKey(inportId);
		return sate;
	}

	// 更新审核状态
	@RequestMapping(value = "/updateState", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> inportBackUpdateState(String strArr,HttpSession session) {
		String[] strs = strArr.split(",");
		for (int i = 0; i < strs.length; i++) {
			inportBackMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "inportBack/inportBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}

	// 更新反审状态
	@RequestMapping(value = "/updateBackState", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> inportUBackUpdateBackState(String strArr,HttpSession session) {
		String[] strs = strArr.split(",");
		for (int i = 0; i < strs.length; i++) {
			inportBackMapper.updateBackStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page page = (Page) session.getAttribute("page");
		map.put("backState", true);
		map.put("servlet", "inportBack/inportBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}

	// 删除单据
	@RequestMapping("/inportBackDelete/{inportId}")
	@ResponseBody
	@Transactional
	public boolean inportBackDelete(
			@PathVariable("inportId") Integer inportId) {
		// 获取主表信息
		InportPage inportPage = inportBackMapper.selectByInportBackId(inportId);
		int inportBack_flag = inportBackMapper.deleteByPrimaryKey(inportId);
		int inportBackDetail_flag = inportBackDetailMapper.deleteInportBackDetail(inportPage);
		if(inportBack_flag > 0 && inportBackDetail_flag > 0){
			return true;
		}else{
			return false;
		}
	}

	
	// 出库操作
	@RequestMapping("/inportBackReview/{inportBackId}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> inportBackReview(
			@PathVariable("inportBackId") Integer inportBackId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取主表信息
		InportPage inportPage = inportBackMapper.selectByInportBackId(inportBackId);
		// 获取明细表信息
		List<InportDetailPage> inportBackDetails = inportBackDetailMapper.selectInportBackDetail(inportPage);
		boolean check_flag = false;
		boolean stock_flag = false;
		for (InportDetailPage detail : inportBackDetails) {
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
				map.put("fail", "库存不足，请检查库存/退厂商品数量!");
				stock_flag = false;
			}
		}

		if(stock_flag){
			// 查询当前时间
			Date date = new Date();
			Operator operator = (Operator) session.getAttribute("user");
			Inport inport = new Inport();
			inport.setId(inportBackId);
			inport.setReviewId(operator.getId());
			inport.setReviewTime(date);
			inportBackMapper.updateReviewStateByPrimaryKey(inport);
			Page page = (Page) session.getAttribute("page");
			map.put("servlet", "inportBack/inportBackSlipList/1/*/*/-1/" + page.getPage());
		}
		return map;
	}

	// 财务操作
	@RequestMapping("/inportBackAccount/{inportBackId}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public boolean inportBackAccount(
			@PathVariable("inportBackId") Integer inportBackId,
			HttpSession session) {
		// 获取主表信息
		InportPage inportPage = inportBackMapper.selectByInportBackId(inportBackId);
		// 获取明细表信息
		List<InportDetailPage> inportBackDetails = inportBackDetailMapper.selectInportBackDetail(inportPage);

		Account accountMain = accountMapper.selectByClientId(inportPage
				.getSupplierId());

		BigDecimal sumInportPrice = new BigDecimal("0");

		AccountDetail accountDetail = null;

		// 查询当前时间
		Date date = new Date();

		for (InportDetailPage detail : inportBackDetails) {
			Goods goods = goodsMapper.selectByPrimaryKey(detail.getGoodsId());
			BigDecimal goodsCost = goods.getCost();
			BigDecimal goodsNumber = new BigDecimal(detail.getNumber());
			BigDecimal goodsPrice = goodsCost.multiply(goodsNumber);
			sumInportPrice = sumInportPrice.add(goodsPrice);
			accountDetail = new AccountDetail();
			accountDetail.setClientId(inportPage.getSupplierId());
			accountDetail.setType(1);
			accountDetail.setListId(inportBackId);
			accountDetail.setListNum(inportPage.getInportNum());
			accountDetail.setListType(3);
			accountDetail.setListPrice(goodsPrice);
			accountDetail.setListNumber(detail.getNumber());
			accountDetail.setCreateTime(inportPage.getCreateTime());
			accountDetail.setAccountTime(date);
			accountDetailMapper.insert(accountDetail);
		}
		// 更新余额表
		Account account = new Account();
		account.setClientId(inportPage.getSupplierId());
		account.setType(1);
		sumInportPrice = accountMain.getBalance().subtract(sumInportPrice);
		account.setBalance(sumInportPrice);
		account.setId(accountMain.getId());
		accountMapper.updateByPrimaryKey(account);

		// 更新退厂单状态为财务审核
		Operator operator = (Operator) session.getAttribute("user");
		Inport inport = new Inport();
		inport.setId(inportBackId);
		inport.setAccountId(operator.getId());
		inport.setAccountTime(date);
		inportBackMapper.updateAccountStateByPrimaryKey(inport);
		return true;
	}

	// 修改单据信息操作
	@RequestMapping(value = "/inportBackSlipEdit/toEdit", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String,Object> inportBackEdit(
			Integer[] goodsId, 
			Integer[] number,
			String[] remark, 
			Integer supplierId, 
			Integer inportId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询当前时间
		Date date = new Date();
		Operator operator = (Operator) session.getAttribute("user");
		// 查询原单据供应商ID
		InportPage oldInport = inportBackMapper.selectByInportBackId(inportId);
		
		Inport newInport = new Inport();

		// 调用查询最大单号
		String maxNo = "";
		if (oldInport.getSupplierId() != supplierId) {
			maxNo = findMaxNo(supplierId);
			newInport.setInportNum(maxNo);
		} else {
			maxNo = oldInport.getInportNum();
			newInport.setInportNum(maxNo);
		}
		newInport.setCreateId(oldInport.getCreateId());
		newInport.setCreateTime(oldInport.getCreateTime());
		// 删除主表
		inportBackMapper.deleteByPrimaryKey(inportId);
		// 将数据汇总，插入主表
		Integer sumInportNumber = 0;
		for (int i = 0; i < goodsId.length; i++) {
			sumInportNumber += number[i];
		}
		newInport.setSupplierId(supplierId);
		newInport.setSumNumber(sumInportNumber);
		newInport.setUpdateTime(date);
		newInport.setUpdateId(operator.getId());
		newInport.setType(1);
		newInport.setState(0);
		inportBackMapper.insert(newInport);

		// 删除明细表
		inportBackDetailMapper.deleteInportBackDetail(oldInport);
		// 插入明细表
		InportDetail inportDetail = new InportDetail();
		inportDetail.setInportNum(maxNo);
		for (int i = 0; i < goodsId.length; i++) {
			inportDetail.setGoodsId(goodsId[i]);
			inportDetail.setNumber(number[i]);
			inportDetail.setSupplierId(supplierId);
			inportDetail.setType(1);
			if(remark.length != 0){
				inportDetail.setRemark(remark[i]);
			}
			inportBackDetailMapper.insert(inportDetail);
		}
		// 获取session中存储的page信息
				Page page = (Page) session.getAttribute("page");
		map.put("edit", true);
		map.put("servlet", "inportBack/inportBackSlipList/1/*/*/-1/" + page.getPage());
		return map;
	}

	// 对应/inportBack/inportBackStock/{goodsId}请求
	// 对应查询库存列表
	@RequestMapping(value = "/inportBackStock/{goodsId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> stockExecute(
			@PathVariable("goodsId") Integer goodsId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
		if(stockPage == null || stockPage.equals("")){
			map.put("goodsNum", 0);
		}else{
			map.put("goodsNum", stockPage.getNum());
		}
		return map;
	}
}