package pres.wisdom.controller.inport;

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
import pres.wisdom.dao.InportDetailMapper;
import pres.wisdom.dao.InportMapper;
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
@RequestMapping("/inport")
@SessionAttributes("page")
public class InportController {
	@Resource
	private InportMapper inportMapper;

	public void setInportMapper(InportMapper inportMapper) {
		this.inportMapper = inportMapper;
	}
	
	@Resource
	private InportDetailMapper inportDetailMapper;

	public void setInportDetailMapper(InportDetailMapper inportDetailMapper) {
		this.inportDetailMapper = inportDetailMapper;
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

	// 对应/inport/inportSlipList/{type}/{supplier}/{createTime}/{state}/{page}请求
	// 对应查询到货单列表
	@RequestMapping(value = "/inportSlipList/{type}/{supplier}/{createTime}/{state}/{page}", method = RequestMethod.GET)
	public String inportExecute(
			@PathVariable("type") Integer type,
			@PathVariable("supplier") String supplier,
			@PathVariable("createTime") String createTime,
			@PathVariable("state") Integer state,
			@PathVariable("page") Integer p,
			Model model) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		InportPage page = new InportPage();
		page.setType(type);
		page.setSupplier(supplier);
		page.setState(state);
		page.setPage(p);
		if(!createTime.equals("*")){
			page.setCreateTime(sdf.parse(createTime));
		}
		List<InportPage> list = inportMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = inportMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		//将条件*号清空
		page.setSupplier(convert(page.getSupplier()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("inportSlip", list);
		model.addAttribute("totalRows", totalRows);
		return "/inport/inportSlipList";// 进入inportSlipList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}
	
	//根据供应商查询最大入库单单号
	public String findMaxNo(Integer supplierId){
		//查询客户首字母大写
		String pinYin = inportMapper.findPinYin(supplierId);
		//查询当前日期，20140101
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);
		//根据供应商首字母和日期，查询供应商当天是否有单据，
		String preNo = pinYin + today;
		InportPage page = new InportPage();
		page.setInportNum(preNo);
		page.setSupplierId(supplierId);
		page.setType(0);
		//没有则001，有则取最大单号
		String maxNo = inportMapper.findMaxNo(page);
		if(maxNo == null || maxNo.length() <= 0 ){
			System.out.println(preNo);
			maxNo = preNo + "001";
		}else{
			String lastNo = maxNo.substring(maxNo.length()-3,maxNo.length());
			int no = Integer.parseInt(lastNo) + 1;
			maxNo = preNo + new DecimalFormat("000").format(no);
		}
		return maxNo;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/inportDetailSave")
	@ResponseBody
	public boolean inportAdd(
			Integer[] goodsId,
			Integer[] number, 
			String[] remark, 
			String mainRemark,
			Integer supplierId,
			HttpSession session){ 
		//查询当前时间
		Date date = new Date();
		//调用查询最大单号
		String maxNo = findMaxNo(supplierId);
		
		Operator operator = (Operator) session.getAttribute("user");
		
		//将数据汇总，插入主表
		Inport inport = new Inport();
		Integer sumInportNumber = 0;
		for(int i = 0; i < goodsId.length; i++){
			sumInportNumber += number[i];
		}
		inport.setInportNum(maxNo);
		inport.setSupplierId(supplierId);
		inport.setSumNumber(sumInportNumber);
		inport.setCreateTime(date);
		inport.setCreateId(operator.getId());
		inport.setType(0);
		inport.setState(0);
		inport.setRemark(mainRemark);
		inportMapper.insert(inport);
		
		//插入明细表
		InportDetail inportDetail = new InportDetail();
		inportDetail.setInportNum(maxNo);
		for(int i = 0; i < goodsId.length; i++){
			inportDetail.setGoodsId(goodsId[i]);
			inportDetail.setNumber(number[i]);
			if(remark.length != 0){
				inportDetail.setRemark(remark[i]);
			}
			inportDetail.setSupplierId(supplierId);
			inportDetail.setType(0);
			inportDetailMapper.insert(inportDetail);
		}
		return true;
	}
	
	// 对应/inport/inportState/1请求
	// 用于查询单据状态
	@RequestMapping("/inportState/{inportId}")
	@ResponseBody
	public Integer checkInportState(@PathVariable("inportId") Integer inportId) {
		int sate = inportMapper.selectStateByPrimaryKey(inportId);
		return sate;
	}

	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> inportUpdateState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			inportMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "inport/inportSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
	
	//更新反审状态
	@RequestMapping(value="/updateBackState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> inportUpdateBackState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			inportMapper.updateBackStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("backState", true);
		map.put("servlet", "inport/inportSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
	
	//删除单据
	@RequestMapping("/inportDelete/{inportId}")
	@ResponseBody
	@Transactional
	public boolean inportDelete(
			@PathVariable("inportId") Integer inportId){
		// 获取主表信息
		InportPage inportPage = inportMapper.selectByInportId(inportId);
		int inport_flag = inportMapper.deleteByPrimaryKey(inportId);
		int inportDetail_flag = inportDetailMapper.deleteInportDetail(inportPage);
		if(inport_flag > 0 && inportDetail_flag > 0){
			return true;
		}else{
			return false;
		}
	}
	
	//入库操作
	@RequestMapping("/inportReview/{inportId}")
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> inportReview(
			@PathVariable("inportId") Integer inportId,
			HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取主表信息
		InportPage inportPage = inportMapper.selectByInportId(inportId);
		//获取明细表信息
		List<InportDetailPage> inportDetails = inportDetailMapper.selectInportDetail(inportPage);
		for(InportDetailPage detail : inportDetails){
			Integer goodsId = detail.getGoodsId();
			StockPage stockPage = stockMapper.selectByGoodsId(goodsId);
			Stock stock = new Stock();
			if(stockPage == null || stockPage.equals("")){
				stock.setGoodsId(goodsId);
				stock.setNum(detail.getNumber());
				stockMapper.insert(stock);
			}else{
				stock.setGoodsId(goodsId);
				stock.setNum(stockPage.getNum() + detail.getNumber());
				stockMapper.updateByGoodsId(stock);
			}
		} 
		
		//查询当前时间
		Date date = new Date();
		Operator operator = (Operator) session.getAttribute("user");
		Inport inport = new Inport();
		inport.setId(inportId);
		inport.setReviewId(operator.getId());
		inport.setReviewTime(date);
		inportMapper.updateReviewStateByPrimaryKey(inport);
		Page page = (Page) session.getAttribute("page");
		map.put("reviewState", true);
		map.put("servlet", "inport/inportSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
	
	//财务操作
	@RequestMapping("/inportAccount/{inportId}")
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> inportAccount(
			@PathVariable("inportId") Integer inportId,
			HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		//获取主表信息
		InportPage inportPage = inportMapper.selectByInportId(inportId);
		//获取明细表信息
		List<InportDetailPage> inportDetails = inportDetailMapper.selectInportDetail(inportPage);
		
		Account accountMain = accountMapper.selectByClientId(inportPage.getSupplierId());
		
		BigDecimal sumInportPrice = new BigDecimal("0");;
		
		AccountDetail accountDetail = null;
		
		//查询当前时间
		Date date = new Date();
		
		for(InportDetailPage detail : inportDetails){
			Goods goods = goodsMapper.selectByPrimaryKey(detail.getGoodsId());
			BigDecimal goodsCost = goods.getCost();
			BigDecimal goodsNumber = new BigDecimal(detail.getNumber());
			BigDecimal goodsPrice = goodsCost.multiply(goodsNumber);
			sumInportPrice = sumInportPrice.add(goodsPrice);
			accountDetail = new AccountDetail();
			accountDetail.setClientId(inportPage.getSupplierId());
			accountDetail.setType(1);
			accountDetail.setListId(inportId);
			accountDetail.setListNum(inportPage.getInportNum());
			accountDetail.setListType(2);
			accountDetail.setListPrice(goodsPrice);
			accountDetail.setListNumber(detail.getNumber());
			accountDetail.setCreateTime(inportPage.getCreateTime());
			accountDetail.setAccountTime(date);
			accountDetailMapper.insert(accountDetail);
		} 
		//更新余额表
		Account account = new Account();
		account.setClientId(inportPage.getSupplierId());
		account.setType(1);
		//余额表存在此供应商则更新；不存在则插入
		if(accountMain == null || accountMain.equals("")){
			account.setBalance(sumInportPrice);
			accountMapper.insert(account);
		}else{
			sumInportPrice = sumInportPrice.add(accountMain.getBalance());
			account.setBalance(sumInportPrice);
			account.setId(accountMain.getId());
			accountMapper.updateByPrimaryKey(account);
		}
		
		//更新到货单状态为财务审核
		Operator operator = (Operator) session.getAttribute("user");
		Inport inportAccount = new Inport();
		inportAccount.setId(inportId);
		inportAccount.setAccountId(operator.getId());
		inportAccount.setAccountTime(date);
		inportMapper.updateAccountStateByPrimaryKey(inportAccount);
		Page page = (Page) session.getAttribute("page");
		map.put("accountState", true);
		map.put("servlet", "inport/inportSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
	
	//修改单据信息操作
	@RequestMapping(value = "/inportSlipEdit/toEdit", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String,Object> inportEdit(
			Integer[] goodsId,
			Integer[] number, 
			String[] remark, 
			String mainRemark,
			Integer supplierId,
			Integer inportId,
			String inportNum,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询主表信息
		InportPage inportPage = inportMapper.selectByInportId(inportId);
		//查询当前时间
		Date date = new Date();
		Operator operator = (Operator) session.getAttribute("user");
		//查询原单据供应商ID
		Inport oldInport = inportMapper.selectByPrimaryKey(inportId);
		
		Inport newInport = new Inport();
		
		//调用查询最大单号
		String maxNo = "";
		if(oldInport.getSupplierId() != supplierId){
			maxNo = findMaxNo(supplierId);
			newInport.setInportNum(maxNo);
		}else{
			maxNo = oldInport.getInportNum();
			newInport.setInportNum(maxNo);
		}
		newInport.setCreateId(oldInport.getCreateId());
		newInport.setCreateTime(oldInport.getCreateTime());
		//删除主表
		inportMapper.deleteByPrimaryKey(inportId);
		//将数据汇总，插入主表
		Integer sumInportNumber = 0;
		for(int i = 0; i < goodsId.length; i++){
			sumInportNumber += number[i];
		}
		newInport.setSupplierId(supplierId);
		newInport.setSumNumber(sumInportNumber);
		newInport.setUpdateTime(date);
		newInport.setUpdateId(operator.getId());
		newInport.setType(0);
		newInport.setState(0);
		newInport.setRemark(mainRemark);
		inportMapper.insert(newInport);
		
		//删除明细表
		inportDetailMapper.deleteInportDetail(inportPage);
		//插入明细表
		InportDetail inportDetail = new InportDetail();
		inportDetail.setInportNum(maxNo);
		for(int i = 0; i < goodsId.length; i++){
			inportDetail.setGoodsId(goodsId[i]);
			inportDetail.setNumber(number[i]);
			if(remark.length != 0){
				inportDetail.setRemark(remark[i]);
			}
			inportDetail.setSupplierId(supplierId);
			inportDetail.setType(0);
			inportDetailMapper.insert(inportDetail);
		}
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("edit", true);
		map.put("servlet", "inport/inportSlipList/0/*/*/-1/" + page.getPage());
		return map;
	}
	
}