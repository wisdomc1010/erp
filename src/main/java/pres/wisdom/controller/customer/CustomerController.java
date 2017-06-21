package pres.wisdom.controller.customer;

import java.text.ParseException;
import java.util.Date;
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

import pres.wisdom.dao.CustomerMapper;
import pres.wisdom.entity.Customer;
import pres.wisdom.entity.CustomerPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/customer")
@SessionAttributes("page")
public class CustomerController {
	@Resource
	private CustomerMapper customerMapper;

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}


	// 对应/customer/customerInfoList/{name}/{idcard}/{phone}/{state}/{useState}/{page}请求
	// 对应查询客户信息列表
	@RequestMapping(value = "/customerInfoList/{name}/{idcard}/{phone}/{state}/{useState}/{page}", method = RequestMethod.GET)
	public String customerExecute(
			@PathVariable("name") String name,
			@PathVariable("idcard") String idcard,
			@PathVariable("phone") String phone,
			@PathVariable("state") Integer state,
			@PathVariable("useState") Integer useState,
			@PathVariable("page") Integer p,
			Model model) {
		CustomerPage page = new CustomerPage();
		page.setName(name);
		page.setPhone(phone);
		page.setIdcard(idcard);
		page.setState(state);
		page.setUseState(useState);
		page.setPage(p);
		List<CustomerPage> list = customerMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = customerMapper.findRows(page);
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
		page.setPhone(convert(page.getPhone()));
		page.setIdcard(convert(page.getIdcard()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("customers", list);
		model.addAttribute("totalRows", totalRows);
		return "/customer/customerInfoList";// 进入customerInfoList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}

	// 对应/customer/customerCheck/xxxxxx请求
	// 用于查询客户身份证号是否存在
	@RequestMapping("/customerCheck/{idcard}")
	@ResponseBody
	public Integer customerCheckIdcard(@PathVariable("idcard") String idcard) {
		System.out.println(idcard);
		Customer customer = customerMapper.selectByIdCard(idcard);
		if (customer == null) {
			return -1;// 没记录表明身份证号可用
		} else {
			return customer.getId();// 有记录表明身份证号不可用
		}
	}

	// 对应/customer/customerAdd请求
	// 添加客户信息
	@RequestMapping(value = "/customerAdd", method = RequestMethod.POST)
	@Transactional
	public String customerAdd(Customer customer,HttpSession session) throws ParseException {
		Operator operator = (Operator) session.getAttribute("user");
		customer.setState(0);
		customer.setUseState(1);
		customer.setCreateId(operator.getId());
		customer.setCreateTime(new Date());
		customerMapper.insert(customer);
		return "redirect:/customer/customerInfoList/*/*/*/-1/-1/1";
	}

	// 对应/customer/customerCheckState/1请求
	// 用于查询客户状态是否审核
	@RequestMapping("/customerCheckState/{id}")
	@ResponseBody
	public Integer customerCheckState(@PathVariable("id") Integer id) {
		int sate = customerMapper.selectByPrimaryKey(id).getState();
		return sate;
	}

	//修改客户信息操作
	@RequestMapping(value = "/customerInfoEdit/{id}", method = RequestMethod.POST)
	@Transactional
	public String customerEdit(Customer customer, HttpSession session) {
		Operator operator = (Operator) session.getAttribute("user");
		customer.setUpdateId(operator.getId());
		customer.setUpdateTime(new Date());
		// 获取更新记录,调用dao更新
		customerMapper.updateByPrimaryKeySelective(customer);
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		return "redirect:/customer/customerInfoList/*/*/*/-1/-1/" + page.getPage();
	}
	
	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> customerUpdateState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			customerMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "customer/customerInfoList/*/*/*/-1/-1/" + page.getPage());
		return map;
	}
	
	//更新在用状态
	@RequestMapping(value="/updateUseState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> customerUpdateUseState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			customerMapper.updateUseStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "customer/customerInfoList/*/*/*/-1/-1/" + page.getPage());
		return map;
	}
}