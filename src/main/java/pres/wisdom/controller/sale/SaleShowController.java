package pres.wisdom.controller.sale;

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
import pres.wisdom.dao.SaleDetailMapper;
import pres.wisdom.dao.SaleMapper;
import pres.wisdom.entity.Customer;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.SaleDetailPage;
import pres.wisdom.entity.SalePage;
import pres.wisdom.util.Token;


@Controller
@RequestMapping("/sale")
@Transactional(readOnly = true)
public class SaleShowController {
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
	private SaleDetailMapper saleDetailMapper;
	
	public void setSaleDetailMapper(SaleDetailMapper saleDetailMapper) {
		this.saleDetailMapper = saleDetailMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showSaleLeft() {
		return "/sale/saleLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showSaleTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}

	
	@RequestMapping(value = "/saleSlipManage", method = RequestMethod.GET)
	public String showSaleList() {
		return "/sale/saleSlipManage";
	}

	@Token(save=true)  
	@RequestMapping(value = "/saleSlipAdd", method = RequestMethod.GET)
	public String showSaleAdd(Model model) {
		List<Customer> list= customerMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("customer",  list);
		model.addAttribute("goods", goodsList);
		return "/sale/saleSlipAdd";
	}	
	
	// 对应/supplier/supplierInfoEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/saleSlipEdit/{saleId}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showSaleEdit(
			@PathVariable("saleId") Integer saleId,
			Model model) {
		SalePage salePage = saleMapper.selectBySaleId(saleId);
		List<SaleDetailPage> detailList = saleDetailMapper.selectBySale(salePage);
		List<Customer> customerList = customerMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("goods", goodsList);
		model.addAttribute("customer", customerList);
		model.addAttribute("saleSlip", salePage);
		model.addAttribute("saleSlipDetail", detailList);
		return "sale/saleSlipEdit";
	}
	
	//查看销售单明细
	@RequestMapping(value = "/showSaleDetail/{saleId}", method = RequestMethod.GET)
	public String showSaleDetail(
			@PathVariable("saleId") Integer saleId,
			Model model) {
		SalePage salePage = saleMapper.selectBySaleId(saleId);
		List<SaleDetailPage> detailList = saleDetailMapper.selectBySale(salePage);
		model.addAttribute("saleSlip",  salePage);
		model.addAttribute("saleSlipDetail",  detailList);
		return "/sale/saleSlipDetail";
	}	
}