package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Operator;
import pres.wisdom.entity.OperatorPage;
import pres.wisdom.vo.Page;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface OperatorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operator record);

    int insertSelective(Operator record);

    Operator selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operator record);

    int updateByPrimaryKey(Operator record);
    
    Operator selectByNamePwd(Operator record);
    
    List<Operator> findPage(Page page);
    
    int findRows(OperatorPage page);
    
    Operator selectByName(String name);
    
    int updateStateByPrimaryKey(Integer id);
    
    int updateUseStateByPrimaryKey(Integer id);
    
    List<Operator> selectByCondition(OperatorPage page);
    
    int selectByState(Integer id);
}