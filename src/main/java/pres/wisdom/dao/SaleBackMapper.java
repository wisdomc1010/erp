package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Sale;
import pres.wisdom.entity.SalePage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface SaleBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sale record);

    int insertSelective(Sale record);

    Sale selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sale record);

    int updateByPrimaryKey(Sale record);
    
    int updateStateByPrimaryKey(Integer id);
    
    int updateBackStateByPrimaryKey(Integer id);
    
    int updateReviewStateByPrimaryKey(Sale record);
    
    int updateAccountStateByPrimaryKey(Sale record);
    
    int findRows(SalePage page);
    
    List<SalePage> selectByCondition(SalePage page);
    
    String findPinYin(Integer supplierId);
    
    String findMaxNo(SalePage page);
    
    SalePage selectBySaleId(Integer id);
    
    int selectStateByPrimaryKey(Integer id);
    
}