package pres.wisdom.controller.stock;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pres.wisdom.dao.StockMapper;
import pres.wisdom.entity.StockPage;

@Controller
@RequestMapping("/stock")
@SessionAttributes("page")
public class StockController {
	@Resource
	private StockMapper stockMapper;

	public void setStockMapper(StockMapper stockMapper) {
		this.stockMapper = stockMapper;
	}

	// 对应/stock/stockInfoList/{goodsNum}/{goodsName}/{supplier}/{page}请求
	// 对应查询库存列表
	@RequestMapping(value = "/stockInfoList/{goodsNum}/{goodsName}/{supplier}/{page}", method = RequestMethod.GET)
	public String stockExecute(
			@PathVariable("goodsNum") String goodsNum,
			@PathVariable("goodsName") String goodsName,
			@PathVariable("supplier") String supplier,
			@PathVariable("page") Integer p,
			Model model) {// 默认显示第一页,一页5条
		StockPage page = new StockPage();
		page.setGoodsNum(goodsNum);
		page.setGoodsName(goodsName);
		page.setSupplier(supplier);
		page.setPage(p);
		List<StockPage> list = stockMapper.selectByCondition(page);
		// 计算总页数
		int totalRows = stockMapper.findRows(page);
		int totalPage = 1;// 默认为1
		if (totalRows % page.getPageSize() == 0) {
			// 能整除时,按10/5=2计算
			totalPage = totalRows / page.getPageSize();
		} else {// 不能整除时,按7/5=1+1计算
			totalPage = totalRows / page.getPageSize() + 1;
		}// 将总页数放入page对象
		page.setTotalPage(totalPage);
		//将条件*号清空
		page.setGoodsNum(convert(page.getGoodsNum()));
		page.setGoodsName(convert(page.getGoodsName()));
		page.setSupplier(convert(page.getSupplier()));
		// 将结果放到Model,供页面访问
		model.addAttribute("page", page);
		model.addAttribute("stocks", list);
		model.addAttribute("totalRows", totalRows);
		return "/stock/stockInfoList";// 进入stockInfoList.jsp
	}
	
	public String convert(Object o){
		if("*".equals(o.toString())){
			return "";
		}else{
			return o.toString();
		}
	}

}