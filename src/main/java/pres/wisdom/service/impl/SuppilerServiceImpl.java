package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.SupplierMapper;
import pres.wisdom.entity.Supplier;
import pres.wisdom.entity.SupplierPage;
import pres.wisdom.service.SuppilerService;

@Service("suppilerService")
public class SuppilerServiceImpl implements SuppilerService {
	@Resource
	private SupplierMapper supplierMapper;
	public List<Supplier> selectAll() {
		return this.supplierMapper.selectAll();
	}
	public Supplier selectByName(String name) {
		return this.supplierMapper.selectByName(name);
	}
	public List<SupplierPage> selectByCondition(SupplierPage page) {
		return this.supplierMapper.selectByCondition(page);
	}
	public int findRows(SupplierPage page) {
		return this.supplierMapper.findRows(page);
	}
	public int updateStateByPrimaryKey(int id) {
		return this.supplierMapper.updateStateByPrimaryKey(id);
	}
	public int updateUseStateByPrimaryKey(int id) {
		return this.supplierMapper.updateUseStateByPrimaryKey(id);
	}

}
