package pres.wisdom.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pres.wisdom.dao.OperatorMapper;
import pres.wisdom.entity.Operator;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Resource
	private OperatorMapper operatorMapper;

	public void setOperatorMapper(OperatorMapper operatorMapper) {
		this.operatorMapper = operatorMapper;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> checkLogin(
		@RequestHeader("name") String name,
		@RequestHeader("pwd") String pwd,
		HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("login", false);//将标识登录失败
		//检查账号和密码
		Operator operator = new Operator();
		operator.setName(name);
		operator.setPwd(pwd);
		Operator info = operatorMapper.selectByNamePwd(operator);
		if(info != null && info.getUseState().equals(1)){//有记录
			session.setAttribute("user", info);
			map.put("login", true);
		}else if(info != null && info.getUseState().equals(0)){
			map.put("error", "账号被锁定，请联系管理员!");
		}
		else{//无记录,错误
			map.put("error", "员工账号或密码不正确，请重新输入!");
		}
		return map;
	}
}
