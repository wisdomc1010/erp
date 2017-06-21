package pres.wisdom.controller.accountDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pres.wisdom.dao.AccountDetailMapper;
import pres.wisdom.entity.AccountDetailPage;

@Controller
@RequestMapping("/accountDetail")
@SessionAttributes("page")
public class AccountDetailController {
	@Resource
	private AccountDetailMapper accountDetailMapper;

	public void setAccountDetailMapper(AccountDetailMapper accountDetailMapper) {
		this.accountDetailMapper = accountDetailMapper;
	}

	// 对应/accountDetail/accountDetailInfoList/{clientName}/{type}/{listType}/{createTime}/{accountTime}/{page}请求
	// 对应查询往来账列表
	@RequestMapping(value = "/accountDetailInfoList/{clientName}/{type}/{listType}/{createTime}/{accountTime}/{page}", method = RequestMethod.GET)
	public String accountDetailExecute(
			@PathVariable("clientName") String clientName,
			@PathVariable("type") Integer type,
			@PathVariable("listType") Integer listType,
			@PathVariable("createTime") String createTime,
			@PathVariable("accountTime") String accountTime,
			@PathVariable("page") Integer p,
			Model model) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AccountDetailPage page = new AccountDetailPage();
		page.setClientName(clientName);
		page.setType(type);
		page.setListType(listType);
		if(!createTime.equals("*")){
			page.setCreateTime(sdf.parse(createTime));
		}
		if(!accountTime.equals("*")){
			page.setAccountTime(sdf.parse(accountTime));
		}
		page.setPage(p);
		List<AccountDetailPage> list = accountDetailMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = accountDetailMapper.findRows(page);
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
		model.addAttribute("accountDetails", list);
		model.addAttribute("totalRows", totalRows);
		return "/accountDetail/accountDetailInfoList";// 进入accountDetailInfoList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}

}