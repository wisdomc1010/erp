package pres.wisdom.controller.saleBack;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.CustomerMapper;
import pres.wisdom.dao.GoodsMapper;
import pres.wisdom.dao.SaleBackMapper;
import pres.wisdom.dao.SaleDetailMapper;
import pres.wisdom.dao.SaleMapper;
import pres.wisdom.entity.Customer;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.SaleDetailPage;
import pres.wisdom.entity.SalePage;


@Controller
@RequestMapping("/saleBack")
@Transactional(readOnly = true)
public class SaleBackShowController {
	@Resource
	private CustomerMapper customerMapper;

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}
	
	@Resource
	private GoodsMapper goodsMapper;

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	@Resource
	private SaleMapper saleMapper;
	
	public void setSaleMapper(SaleMapper saleMapper) {
		this.saleMapper = saleMapper;
	}

	@Resource
	private SaleBackMapper saleBackMapper;

	public void setSaleBackMapper(SaleBackMapper saleBackMapper) {
		this.saleBackMapper = saleBackMapper;
	}
	
	@Resource
	private SaleDetailMapper saleDetailMapper;

	public void setSaleDetailMapper(SaleDetailMapper saleDetailMapper) {
		this.saleDetailMapper = saleDetailMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showSaleBackLeft() {
		return "/saleBack/saleBackLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showSaleBackTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}

	
	@RequestMapping(value = "/saleBackSlipManage", method = RequestMethod.GET)
	public String showSaleBackList() {
		return "/saleBack/saleBackSlipManage";
	}

	
	@RequestMapping(value = "/saleBackSlipAdd", method = RequestMethod.GET)
	public String showSaleBackAdd(Model model) {
		List<Customer> list= customerMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("customer",  list);
		model.addAttribute("goods", goodsList);
		return "/saleBack/saleBackSlipAdd";
	}	
	
	// 对应/supplier/supplierInfoEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/saleBackSlipEdit/{saleBackId}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showSaleBackEdit(
			@PathVariable("saleBackId") Integer saleBackId,
			Model model) {
		SalePage salePage = saleMapper.selectBySaleBackId(saleBackId);
		List<SaleDetailPage> detailList = saleDetailMapper.selectBySale(salePage);
		List<Customer> customerList = customerMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("goods", goodsList);
		model.addAttribute("customer", customerList);
		model.addAttribute("saleBackSlip", salePage);
		model.addAttribute("saleBackSlipDetail", detailList);
		return "saleBack/saleBackSlipEdit";
	}
	
	//查看退货单明细
	@RequestMapping(value = "/showSaleBackDetail/{saleId}", method = RequestMethod.GET)
	public String showSaleBackDetail(
			@PathVariable("saleId") Integer saleId,
			Model model) {
		SalePage salePage = saleBackMapper.selectBySaleId(saleId);
		List<SaleDetailPage> detailList = saleDetailMapper.selectBySale(salePage);
		model.addAttribute("saleBackSlip",  salePage);
		model.addAttribute("saleBackSlipDetail",  detailList);
		return "/saleBack/saleBackSlipDetail";
	}	
}