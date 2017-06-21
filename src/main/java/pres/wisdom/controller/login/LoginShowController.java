package pres.wisdom.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.OperatorMapper;
import pres.wisdom.entity.Operator;

@Controller
@RequestMapping("/login")
public class LoginShowController {
	@Resource
	private OperatorMapper operatorMapper;

	public void setOperatorMapper(OperatorMapper operatorMapper) {
		this.operatorMapper = operatorMapper;
	}
	
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

	@RequestMapping(value = "/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "main";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showLeft() {
		return "left";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}
	
    @RequestMapping(value="/logout")  
    public String toLogout(HttpSession session){  
        //清除Session  
        session.invalidate();  
        return "login";  
    } 
}
