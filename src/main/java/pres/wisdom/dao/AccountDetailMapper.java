package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.AccountDetail;
import pres.wisdom.entity.AccountDetailPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface AccountDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountDetail record);

    int insertSelective(AccountDetail record);

    AccountDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountDetail record);

    int updateByPrimaryKey(AccountDetail record);
    
    int findRows(AccountDetailPage page);
    
    List<AccountDetailPage> selectByCondition(AccountDetailPage page);
}