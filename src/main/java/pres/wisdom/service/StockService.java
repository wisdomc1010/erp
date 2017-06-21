package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.StockPage;

public interface StockService {
	public int findRows(StockPage page);
	public List<StockPage> selectByCondition(StockPage page);
}
