package pres.wisdom.controller.inport;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.GoodsMapper;
import pres.wisdom.dao.InportDetailMapper;
import pres.wisdom.dao.InportMapper;
import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.entity.InportDetailPage;
import pres.wisdom.entity.InportPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Supplier;


@Controller
@RequestMapping("/inport")
@Transactional(readOnly = true)
public class InportShowController {
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

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showInportLeft() {
		return "/inport/inportLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showInportTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}
	
	@RequestMapping(value = "/inportSlipManage", method = RequestMethod.GET)
	public String showInportList() {
		return "/inport/inportSlipManage";
	}

	
	@RequestMapping(value = "/inportSlipAdd", method = RequestMethod.GET)
	public String showInportAdd(Model model) {
		List<Supplier> supplierList= supplierMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("supplier",  supplierList);
		model.addAttribute("goods",  goodsList);
		return "/inport/inportSlipAdd";
	}	
	
	//查看到货单明细
	@RequestMapping(value = "/showInportDetail/{inportId}", method = RequestMethod.GET)
	public String showInportDetail(
			@PathVariable("inportId") Integer inportId,
			Model model) {
		InportPage inportPage = inportMapper.selectByInportId(inportId);
		List<InportDetailPage> detailList = inportDetailMapper.selectInportDetail(inportPage);
		model.addAttribute("inportSlip",  inportPage);
		model.addAttribute("inportSlipDetail",  detailList);
		return "/inport/inportSlipDetail";
	}	
	
	// 对应/inport/inportSlipEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/inportSlipEdit/{inportId}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showInportEdit(
			@PathVariable("inportId") Integer inportId,
			Model model) {
		InportPage inportPage = inportMapper.selectByInportId(inportId);
		List<InportDetailPage> detailList = inportDetailMapper.selectInportDetail(inportPage);
		List<Supplier> supplierList = supplierMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("goods",  goodsList);
		model.addAttribute("supplier", supplierList);
		model.addAttribute("inportSlip",  inportPage);
		model.addAttribute("inportSlipDetail",  detailList);
		return "inport/inportSlipEdit";
	}
}