package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Supplier;
import pres.wisdom.entity.SupplierPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface SupplierMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    Supplier selectByName(String name);
    
    List<Supplier> selectAll();
    
    int findRows(SupplierPage page);
    
    int updateStateByPrimaryKey(Integer id);
    
    int updateUseStateByPrimaryKey(Integer id);
    
    List<SupplierPage> selectByCondition(SupplierPage page);
}