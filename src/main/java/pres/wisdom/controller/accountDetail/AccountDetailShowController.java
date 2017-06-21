package pres.wisdom.controller.accountDetail;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pres.wisdom.dao.InportDetailMapper;
import pres.wisdom.dao.InportMapper;
import pres.wisdom.entity.InportDetailPage;
import pres.wisdom.entity.InportPage;
import pres.wisdom.entity.Operator;


@Controller
@RequestMapping("/accountDetail")
@Transactional(readOnly = true)
public class AccountDetailShowController {
	@Resource
	InportMapper inportMapper;
	
	public void setInportMapper(InportMapper inportMapper) {
		this.inportMapper = inportMapper;
	}
	
	@Resource
	InportDetailMapper inportDetailMapper;

	public void setInportDetailMapper(InportDetailMapper inportDetailMapper) {
		this.inportDetailMapper = inportDetailMapper;
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String showAccountDetailLeft() {
		return "/accountDetail/accountDetailLeft";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String showAccountDetailTop(Model model,HttpSession session) {
		session.getAttribute("user");
		Operator operator = (Operator) session.getAttribute("user");
		model.addAttribute("operator",operator);
		return "top";
	}
	
	@RequestMapping(value = "/accountDetailInfoManage", method = RequestMethod.GET)
	public String showAccountDetailList() {
		return "/accountDetail/accountDetailInfoManage";
	}

//	//查看到货单明细
//	@RequestMapping(value = "/showInportDetail/{inportId}/{inportNum}", method = RequestMethod.GET)
//	public String showInportDetail(
//			@PathVariable("inportId") Integer inportId,
//			@PathVariable("inportNum") String inportNum,
//			Model model) {
//		InportPage inportPage = inportMapper.selectByInportId(inportId);
//		List<InportDetailPage> detailList = inportDetailMapper.selectInportDetail(inportPage);
//		model.addAttribute("inportSlip",  inportPage);
//		model.addAttribute("inportSlipDetail",  detailList);
//		return "/inport/inportSlipDetail";
//	}
}