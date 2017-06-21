package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.StockMapper;
import pres.wisdom.entity.StockPage;
import pres.wisdom.service.StockService;
@Service("stockService")
public class StockServiceImpl implements StockService {
	@Resource
	private StockMapper stockMapper;
	public int findRows(StockPage page) {
		return this.stockMapper.findRows(page);
	}

	public List<StockPage> selectByCondition(StockPage page) {
		return this.stockMapper.selectByCondition(page);
	}

}
