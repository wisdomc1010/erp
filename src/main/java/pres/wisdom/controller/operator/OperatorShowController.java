package pres.wisdom.controller.operator;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.OperatorMapper;
import pres.wisdom.entity.Operator;


@Controller
@RequestMapping("/operator")
@Transactional(readOnly = true)
public class OperatorShowController {
	@Resource
	private OperatorMapper operatorMapper;
	
	public void setOperatorMapper(OperatorMapper operatorMapper) {
		this.operatorMapper = operatorMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showOperatorLeft() {
		return "/operator/operatorLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showOperatorTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}
	
	@RequestMapping(value = "/operatorInfoManage", method = RequestMethod.GET)
	public String showOperatorList() {
		return "/operator/operatorInfoManage";
	}
	
	@RequestMapping(value = "/operatorInfoAdd", method = RequestMethod.GET)
	public String showOperatorAdd() {
		return "/operator/operatorInfoAdd";
	}	
	
	// 对应/operator/operatorInfoEdit请求
	// 用于进入修改界面
	@RequestMapping(value = "/operatorInfoEdit/toEdit/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showOperatorEdit(@PathVariable("id") Integer id, Model model) {
		Operator operator = operatorMapper.selectByPrimaryKey(id);
		model.addAttribute("operator", operator);
		return "operator/operatorInfoEdit";
	}
	
}