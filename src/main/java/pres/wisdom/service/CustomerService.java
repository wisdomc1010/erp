package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.Customer;
import pres.wisdom.entity.CustomerPage;

public interface CustomerService {
	public Customer selectByIdCard(String idCard);
	public List<CustomerPage> selectByCondition(CustomerPage page);
	public int findRows(CustomerPage page);
	public int updateStateByPrimaryKey(int id);
	public int updateUseStateByPrimaryKey(int id);
}
