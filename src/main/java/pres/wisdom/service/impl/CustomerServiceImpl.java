package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.CustomerMapper;
import pres.wisdom.entity.Customer;
import pres.wisdom.entity.CustomerPage;
import pres.wisdom.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Resource
	private CustomerMapper customerMapper;

	public Customer selectByIdCard(String idCard) {
		return this.customerMapper.selectByIdCard(idCard);
	}

	public List<CustomerPage> selectByCondition(CustomerPage page) {
		return this.customerMapper.selectByCondition(page);
	}

	public int findRows(CustomerPage page) {
		return this.customerMapper.findRows(page);
	}

	public int updateStateByPrimaryKey(int id) {
		return this.customerMapper.updateStateByPrimaryKey(id);
	}

	public int updateUseStateByPrimaryKey(int id) {
		return this.customerMapper.updateUseStateByPrimaryKey(id);
	}
}
