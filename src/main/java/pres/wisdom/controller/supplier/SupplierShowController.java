package pres.wisdom.controller.supplier;



import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Supplier;


@Controller
@RequestMapping("/supplier")
@Transactional(readOnly = true)
public class SupplierShowController {
	@Resource
	private SupplierMapper supplierMapper;

	public void setSupplierMapper(SupplierMapper supplierMapper) {
		this.supplierMapper = supplierMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showSupplierLeft() {
		return "/supplier/supplierLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showSupplierTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}

	
	@RequestMapping(value = "/supplierInfoManage", method = RequestMethod.GET)
	public String showSupplierList() {
		return "/supplier/supplierInfoManage";
	}
	
	@RequestMapping(value = "/supplierInfoAdd", method = RequestMethod.GET)
	public String showSupplierAdd() {
		return "/supplier/supplierInfoAdd";
	}	
	
	// 对应/supplier/supplierInfoEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/supplierInfoEdit/toEdit/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showSupplierEdit(@PathVariable("id") Integer id, Model model) {
		Supplier supplier = supplierMapper.selectByPrimaryKey(id);
		model.addAttribute("supplier", supplier);
		return "supplier/supplierInfoEdit";
	}
}