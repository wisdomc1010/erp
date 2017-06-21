package pres.wisdom.controller.inportBack;

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
import pres.wisdom.dao.InportBackDetailMapper;
import pres.wisdom.dao.InportBackMapper;
import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.entity.InportDetailPage;
import pres.wisdom.entity.InportPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Supplier;

@Controller
@RequestMapping("/inportBack")
@Transactional(readOnly = true)
public class InportBackShowController {
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
	private InportBackMapper inportBackMapper;
	
	public void setInportBackMapper(InportBackMapper inportBackMapper) {
		this.inportBackMapper = inportBackMapper;
	}

	@Resource
	private InportBackDetailMapper inportBackDetailMapper;
	
	public void setInportBackDetailMapper(
			InportBackDetailMapper inportBackDetailMapper) {
		this.inportBackDetailMapper = inportBackDetailMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showInportBackLeft() {
		return "/inportBack/inportBackLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showInportBackTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}

	@RequestMapping(value = "/inportBackSlipManage", method = RequestMethod.GET)
	public String showInportBackList() {
		return "/inportBack/inportBackSlipManage";
	}

	@RequestMapping(value = "/inportBackSlipAdd", method = RequestMethod.GET)
	public String showInportBackAdd(Model model) {
		List<Supplier> supplierList = supplierMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("supplier", supplierList);
		model.addAttribute("goods", goodsList);
		return "/inportBack/inportBackSlipAdd";
	}

	@RequestMapping(value = "/inportBackSlipEdit", method = RequestMethod.GET)
	public String showInportBackEdit() {
		return "/inportBack/inportBackSlipEdit";
	}

	// 查看退厂单明细
	@RequestMapping(value = "/showInportBackDetail/{inportBackId}", method = RequestMethod.GET)
	public String showInportBackDetail(
			@PathVariable("inportBackId") Integer inportId,
			Model model) {
		InportPage inportPage = inportBackMapper.selectByInportBackId(inportId);
		List<InportDetailPage> detailList = inportBackDetailMapper.selectInportBackDetail(inportPage);
		model.addAttribute("inportBackSlip", inportPage);
		model.addAttribute("inportBackSlipDetail", detailList);
		return "/inportBack/inportBackSlipDetail";
	}
	
	// 对应/inportBack/inportBackSlipEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/inportBackSlipEdit/{inportId}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showInportBackEdit(
			@PathVariable("inportId") Integer inportId,
			Model model) {
		InportPage inportPage = inportBackMapper.selectByInportBackId(inportId);
		List<InportDetailPage> detailList = inportBackDetailMapper.selectInportBackDetail(inportPage);
		List<Supplier> supplierList = supplierMapper.selectAll();
		List<GoodsPage> goodsList = goodsMapper.selectAll();
		model.addAttribute("goods", goodsList);
		model.addAttribute("supplier", supplierList);
		model.addAttribute("inportSlip", inportPage);
		model.addAttribute("inportSlipDetail", detailList);
		return "inportBack/inportBackSlipEdit";
	}
}