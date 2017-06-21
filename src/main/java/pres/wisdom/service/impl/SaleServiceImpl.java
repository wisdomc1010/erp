package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.SaleMapper;
import pres.wisdom.entity.SalePage;
import pres.wisdom.service.SaleService;
@Service("saleService")
public class SaleServiceImpl implements SaleService {
	@Resource
	private SaleMapper saleMapper;
	public int findRows(SalePage page) {
		return this.saleMapper.findRows(page);
	}

	public List<SalePage> selectByCondition(SalePage page) {
		return this.saleMapper.selectByCondition(page);
	}

}
