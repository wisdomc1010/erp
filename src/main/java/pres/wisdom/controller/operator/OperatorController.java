package pres.wisdom.controller.operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;





import pres.wisdom.dao.OperatorMapper;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.OperatorPage;
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/operator")
@SessionAttributes("page")
public class OperatorController {
	@Resource
	private OperatorMapper operatorMapper;

	public void setOperatorMapper(OperatorMapper operatorMapper) {
		this.operatorMapper = operatorMapper;
	}

	// 对应/operator/operatorInfoList/{name}/{realname}/{state}/{useState}/{page}请求
	// 对应查询员工信息列表
	@RequestMapping(value = "/operatorInfoList/{name}/{realname}/{state}/{useState}/{page}", method = RequestMethod.GET)
	public String operatorExecute(
			@PathVariable("name") String name,
			@PathVariable("realname") String realname,
			@PathVariable("state") Integer state,
			@PathVariable("useState") Integer useState,
			@PathVariable("page") Integer p,
			Model model) {
		OperatorPage page = new OperatorPage();
		page.setName(name);
		page.setRealname(realname);
		page.setState(state);
		page.setUseState(useState);
		page.setPage(p);
		List<Operator> list = operatorMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = operatorMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		//将条件*号清空
		page.setName(convert(page.getName()));
		page.setRealname(convert(page.getRealname()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("operators", list);
		model.addAttribute("totalRows", totalRows);
		return "/operator/operatorInfoList";// 进入operatorInfoList.jsp
	}
	
	public String convert(String s){
		if("*".equals(s)){
			return "";
		}else{
			return s;
		}
	}

	// 对应/operator/operatorCheck/admin请求
	// 用于查询员工工号是否存在
	@RequestMapping("/operatorCheck/{name}")
	@ResponseBody
	public boolean operatorCheckName(@PathVariable("name") String name) {
		Operator opeartor = operatorMapper.selectByName(name);
		if (opeartor == null) {
			return true;// 没记录表明员工账号可用
		} else {
			return false;// 有记录表明员工账号不可用
		}
	}
	
	// 对应/operator/operatorCheck/1请求
	// 用于查询员工状态是否审核
	@RequestMapping("/operatorCheckState/{id}")
	@ResponseBody
	public boolean operatorCheckState(@PathVariable("id") Integer id) {
		int sate = operatorMapper.selectByPrimaryKey(id).getState();
		if(sate==1){
			return true;
		}else{
			return false;
		}
	}
		
	// 对应/operator/operatorAdd请求
	// 添加员工信息
	@RequestMapping(value = "/operatorAdd", method = RequestMethod.POST)
	@Transactional
	public String operatorAdd(Operator operator) {
		operator.setState(0);
		operator.setUseState(1);
		operatorMapper.insert(operator);
		return "redirect:/operator/operatorInfoList/*/*/-1/-1/1";
	}

	//修改员工信息操作
	@RequestMapping(value = "/operatorInfoEdit/{id}", method = RequestMethod.POST)
	@Transactional
	public String operatorEdit(Operator operator, HttpSession session) {
		// 获取更新记录,调用dao更新
		operatorMapper.updateByPrimaryKeySelective(operator);
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		return "redirect:/operator/operatorInfoList/*/*/-1/-1/" + page.getPage();
	}
	
	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> updateOperatorState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			operatorMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "operator/operatorInfoList/*/*/-1/-1/" + page.getPage());
		return map;
	}
	
	//更新在用状态
	@RequestMapping(value="/updateUseState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> updateOperatorUseState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			operatorMapper.updateUseStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "operator/operatorInfoList/*/*/-1/-1/" + page.getPage());
		return map;
	}
}