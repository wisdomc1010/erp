package pres.wisdom.controller.goods;

import java.util.Date;
import java.util.List;

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

import pres.wisdom.dao.GoodsMapper;
import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.Goods;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.entity.Operator;
import pres.wisdom.vo.Page;

@Controller
@RequestMapping("/goods")
@SessionAttributes("page")
public class GoodsController {
	@Resource
	private GoodsMapper goodsMapper;

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}
	@Resource
	private SupplierMapper supplierMapper;
	
	public void setSupplierMapper(SupplierMapper supplierMapper) {
		this.supplierMapper = supplierMapper;
	}

	// 对应/goods/goodsInfoList/{num}/{name}/{supplier}/{state}/{useState}/{page}请求
	// 对应查询员工信息列表
	@RequestMapping(value = "/goodsInfoList/{num}/{name}/{supplier}/{state}/{useState}/{page}", method = RequestMethod.GET)
	public String goodsExecute(
			@PathVariable("num") String num,
			@PathVariable("name") String name,
			@PathVariable("supplier") String supplier,
			@PathVariable("state") Integer state,
			@PathVariable("useState") Integer useState,
			@PathVariable("page") Integer p,
			Model model) {
		GoodsPage page = new GoodsPage();
		page.setNum(num);
		page.setName(name);
		page.setSupplier(supplier);
		page.setState(state);
		page.setUseState(useState);
		page.setPage(p);
		List<GoodsPage> list = goodsMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = goodsMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		//将条件*号清空
		page.setNum(convert(page.getNum()));
		page.setName(convert(page.getName()));
		page.setSupplier(convert(page.getSupplier()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("goods", list);
		model.addAttribute("totalRows", totalRows);
		return "/goods/goodsInfoList";// 进入goodsInfoList.jsp
	}
	
	public String convert(String s){
		if("*".equals(s)){
			return "";
		}else{
			return s;
		}
	}

	// 对应/goods/goodsCheck/0001请求
	// 用于查询商品编码是否存在
	@RequestMapping("/goodsCheck/{num}")
	@ResponseBody
	public boolean goodsCheckNum(@PathVariable("num") String num) {
		Goods goods = goodsMapper.selectByNum(num);
		if (goods == null) {
			return true;// 没记录表明商品编号可用
		} else {
			return false;// 有记录表明商品编号不可用
		}
	}

	// 对应/goods/goodsAdd请求
	// 添加商品信息
	@RequestMapping(value = "/goodsAdd", method = RequestMethod.POST)
	@Transactional
	public String goodsAdd(Goods goods,HttpSession session) {
		Operator operator = (Operator) session.getAttribute("user");
		goods.setState(0);
		goods.setUseState(1);
		goods.setCreateId(operator.getId());
		goods.setCreateTime(new Date());
		goodsMapper.insert(goods);
		return "redirect:/goods/goodsInfoList/*/*/*/-1/-1/1";
	}

	// 对应/goods/goodsCheckState/1请求
	// 用于查询商品状态是否审核
	@RequestMapping("/goodsCheckState/{id}")
	@ResponseBody
	public Integer checkGoodsState(@PathVariable("id") Integer id) {
		int sate = goodsMapper.selectByPrimaryKey(id).getState();
		return sate;
	}

	//修改商品信息操作
	@RequestMapping(value = "/goodsInfoEdit/{id}", method = RequestMethod.POST)
	@Transactional
	public String goodsEdit(Goods goods, HttpSession session) {
		Operator operator = (Operator) session.getAttribute("user");
		goods.setUpdateId(operator.getId());
		goods.setUpdateTime(new Date());
		// 获取更新记录,调用dao更新
		goodsMapper.updateByPrimaryKeySelective(goods);
		// 获取session中存储的page信息
		Page page = (Page) session.getAttribute("page");
		return "redirect:/goods/goodsInfoList/*/*/*/-1/-1/" + page.getPage();
	}
	
	//更新审核状态
	@RequestMapping(value="/updateState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public boolean goodsUpdateState(String strArr){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			goodsMapper.updateStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		return true;
	}
	
	//更新在用状态
	@RequestMapping(value="/updateUseState",method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public boolean goodsUpdateUseState(String strArr){
		String[] strs = strArr.split(",");
		for(int i = 0; i < strs.length; i++){
			goodsMapper.updateUseStateByPrimaryKey(Integer.parseInt(strs[i]));
		}
		return true;
	}
}