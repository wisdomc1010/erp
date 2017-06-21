package pres.wisdom.controller.account;



import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.entity.Operator;


@Controller
@RequestMapping("/account")
@Transactional(readOnly = true)
public class AccountShowController {
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showAccountLeft() {
		return "/account/accountInfoLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showAccountTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}
	
	@RequestMapping(value = "/accountInfoManage", method = RequestMethod.GET)
	public String showAccountList() {
		return "/account/accountInfoManage";
	}
}