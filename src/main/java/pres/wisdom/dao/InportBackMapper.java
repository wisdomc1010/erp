package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Inport;
import pres.wisdom.entity.InportPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface InportBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inport record);

    int insertSelective(Inport record);

    Inport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inport record);

    int updateByPrimaryKey(Inport record);
    
    int findRows(InportPage page);
    
    int updateStateByPrimaryKey(Integer id);
    
    int updateBackStateByPrimaryKey(Integer id);
    
    int updateReviewStateByPrimaryKey(Inport record);
    
    int updateAccountStateByPrimaryKey(Inport record);
    
    List<InportPage> selectByCondition(InportPage page);
    
    String findPinYin(Integer supplierId);
    
    String findMaxNo(InportPage page);
    
    int selectStateByPrimaryKey(Integer id);
    
    InportPage selectByInportBackId(Integer id);
}