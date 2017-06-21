package pres.wisdom.controller.supplier;

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

import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.Supplier;
import pres.wisdom.entity.SupplierPage;
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/supplier")
@SessionAttributes("page")
public class SupplierController {
	@Resource
	private SupplierMapper supplierMapper;

	public void setSupplierMapper(SupplierMapper supplierMapper) {
		this.supplierMapper = supplierMapper;
	}

	// 对应/supplier/supplierInfoList/{name}/{contacts}/{phone}/{state}/{useState}/{page}请求
	// 对应查询供应商信息列表
	@RequestMapping(value = "/supplierInfoList/{name}/{contacts}/{phone}/{state}/{useState}/{page}", method = RequestMethod.GET)
	public String supplierExecute(
			@PathVariable("name") String name,
			@PathVariable("contacts") String contacts,
			@PathVariable("phone") String phone,
			@PathVariable("state") Integer state,
			@PathVariable("useState") Integer useState,
			@PathVariable("page") Integer p,
			Model model) {
		SupplierPage page = new SupplierPage();
		page.setName(name);
		page.setPhone(phone);
		page.setContacts(contacts);
		page.setState(state);
		page.setUseState(useState);
		page.setPage(p);
		List<SupplierPage> list = supplierMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = supplierMapper.findRows(page);
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
		page.setContacts(convert(page.getContacts()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("suppliers", list);
		model.addAttribute("totalRows", totalRows);
		return "/supplier/supplierInfoList";// 进入supplierInfoList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}

	// 对应/customer/customerCheck/xxxxxx请求
	// 用于查询供应商名称是否存在
	@RequestMapping("/supplierCheck/{name}")
	@ResponseBody
	public Integer supplierCheckName(@PathVariable("name") String name) {
		System.out.println(name);
		Supplier supplier = supplierMapper.selectByName(name);
		if (supplier == null) {
			return -1;// 没记录表明供应商名称可用
		} else {
			return supplier.getId();// 有记录表明供应商名称不可用
		}
	}

	// 对应/supplier/supplierAdd请求
	// 添加供应商信息
	@RequestMapping(value = "/supplierAdd", method = RequestMethod.POST)
	@Transactional
	public String supplierAdd(Supplier supplier,HttpSession session) throws ParseException {
		Operator operator = (Operator) session.getAttribute("user");
		supplier.setState(0);
		supplier.setUseState(1);
		supplier.setCreateId(operator.getId());
		supplier.setCreateTime(new Date());
		supplierMapper.insert(supplier);
		return "redirect:/supplier/supplierInfoList/*/*/*/-1/-1/1";
	}
	// 对应/supplier/supplierCheckState/1请求
	// 用于查询供应商状态是否审核
	@RequestMapping("/supplierCheckState/{id}")
	@ResponseBody
	public Integer supplierCheckState(@PathVariable("id") Integer id) {
		int sate = supplierMapper.selectByPrimaryKey(id).getState();
		return sate;
	}

	//修改供应商信息操作
	@RequestMapping(value = "/supplierInfoEdit/{id}", method = RequestMethod.POST)
	@Transactional
	public String supplierEdit(Supplier supplier, HttpSession session) {
		Operator operator = (Operator) session.getAttribute("user");
		supplier.setUpdateId(operator.getId());
		supplier.setUpdateTime(new Date());
		// 获取更新记录,调用dao更新
		supplierMapper.updateByPrimaryKeySelective(supplier);
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		return "redirect:/supplier/supplierInfoList/*/*/*/-1/-1/" + page.getPage();
	}
	
	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> supplierUpdateState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			supplierMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "supplier/supplierInfoList/*/*/*/-1/-1/" + page.getPage());
		return map;
	}
	
	//更新在用状态
	@RequestMapping(value="/updateUseState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> supplierUpdateUseState(String strArr,HttpSession session){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			supplierMapper.updateUseStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		map.put("state", true);
		map.put("servlet", "supplier/supplierInfoList/*/*/*/-1/-1/" + page.getPage());
		return map;
	}
}