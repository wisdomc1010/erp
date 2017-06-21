package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Account;
import pres.wisdom.entity.AccountPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
    
    int findRows(AccountPage page);
    
    List<AccountPage> selectByCondition(AccountPage page);
    
    Account selectByClientId(Integer id);
}