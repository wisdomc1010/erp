package pres.wisdom.controller.goods;


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
import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.Goods;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Supplier;


@Controller
@RequestMapping("/goods")
@Transactional(readOnly = true)
public class GoodsShowController {
	@Resource
	private SupplierMapper supplierMapper;
	
	public void setSupplierMapper(SupplierMapper supplierMapper) {
		this.supplierMapper = supplierMapper;
	}
	
	@Resource
	private GoodsMapper	goodsMapper;

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showGoodsLeft() {
		return "/goods/goodsLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showGoodsTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}

	
	@RequestMapping(value = "/goodsInfoManage", method = RequestMethod.GET)
	public String showGoodsList() {
		return "/goods/goodsInfoManage";
	}
	
	@RequestMapping(value = "/goodsInfoAdd", method = RequestMethod.GET)
	public String showGoodsAdd(Model model) {
		List<Supplier> list = supplierMapper.selectAll();
		model.addAttribute("supplier", list);
		return "/goods/goodsInfoAdd";
	}

	// 对应/operator/operatorInfoEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/goodsInfoEdit/toEdit/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showGoodsEdit(@PathVariable("id") Integer id, Model model) {
		Goods goods = goodsMapper.selectByPrimaryKey(id);
		model.addAttribute("goods", goods);
		List<Supplier> list = supplierMapper.selectAll();
		model.addAttribute("supplier", list);
		return "goods/goodsInfoEdit";
	}
}