package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.Supplier;
import pres.wisdom.entity.SupplierPage;

public interface SuppilerService {
	public List<Supplier> selectAll();
	public Supplier selectByName(String name);
	public List<SupplierPage> selectByCondition(SupplierPage page);
	public int findRows(SupplierPage page);
	public int updateStateByPrimaryKey(int id);
	public int updateUseStateByPrimaryKey(int id);
}
