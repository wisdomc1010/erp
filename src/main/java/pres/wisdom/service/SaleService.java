package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.SalePage;

public interface SaleService {
	public int findRows(SalePage page);
	public List<SalePage> selectByCondition(SalePage page);
}
