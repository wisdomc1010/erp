package pres.wisdom.controller.account;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pres.wisdom.dao.AccountMapper;
import pres.wisdom.entity.AccountPage;

@Controller
@RequestMapping("/account")
@SessionAttributes("page")
public class AccountController {
	@Resource
	private AccountMapper accountMapper;

	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	// 对应/account/accountInfoList/{clientName}/{type}/{page}请求
	// 对应查询余额列表
	@RequestMapping(value = "/accountInfoList/{clientName}/{type}/{page}", method = RequestMethod.GET)
	public String accountExecute(
			@PathVariable("clientName") String clientName,
			@PathVariable("type") Integer type,
			@PathVariable("page") Integer p,
			Model model) {
		AccountPage page = new AccountPage();
		page.setClientName(clientName);
		page.setType(type);
		page.setPage(p);
		List<AccountPage> list = accountMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = accountMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		//将条件*号清空
		page.setClientName(convert(page.getClientName()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("accounts", list);
		model.addAttribute("totalRows", totalRows);
		return "/account/accountInfoList";// 进入accountInfoList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}

}