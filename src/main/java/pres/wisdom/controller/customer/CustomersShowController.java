package pres.wisdom.controller.customer;



import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.CustomerMapper;
import pres.wisdom.entity.Customer;
import pres.wisdom.entity.Operator;


@Controller
@RequestMapping("/customer")
@Transactional(readOnly = true)
public class CustomersShowController {
	@Resource
	private CustomerMapper customerMapper;

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showCustomerLeft() {
		return "/customer/customerLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showCustomerTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}

	
	@RequestMapping(value = "/customerInfoManage", method = RequestMethod.GET)
	public String showCustomerList() {
		return "/customer/customerInfoManage";
	}
	
	@RequestMapping(value = "/customerInfoAdd", method = RequestMethod.GET)
	public String showCustomerAdd() {
		return "/customer/customerInfoAdd";
	}
	
	// 对应/customer/customerInfoEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/customerInfoEdit/toEdit/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showCustomerEdit(@PathVariable("id") Integer id, Model model) {
		Customer customer = customerMapper.selectByPrimaryKey(id);
		model.addAttribute("customer", customer);
		return "customer/customerInfoEdit";
	}
}