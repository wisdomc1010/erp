package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Customer;
import pres.wisdom.entity.CustomerPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    Customer selectByIdCard(String idCard);
    
    int findRows(CustomerPage page);
    
    int updateStateByPrimaryKey(Integer id);
    
    int updateUseStateByPrimaryKey(Integer id);
    
    List<CustomerPage> selectByCondition(CustomerPage page);
    
    List<Customer> selectAll();
}