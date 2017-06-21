package pres.wisdom.controller.stock;



import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.entity.Operator;


@Controller
@RequestMapping("/stock")
@Transactional(readOnly = true)
public class StockShowController {
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showStockLeft() {
		return "/stock/stockLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showStockTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}
	
	@RequestMapping(value = "/stockInfoManage", method = RequestMethod.GET)
	public String showStockList() {
		return "/stock/stockInfoManage";
	}
}