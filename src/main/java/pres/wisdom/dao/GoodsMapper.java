package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Goods;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    
    Goods selectByNum(String num);
    
    List<GoodsPage> selectByCondition(GoodsPage page);
    
    int findRows(GoodsPage page);
    
    int updateStateByPrimaryKey(Integer id);
    
    int updateUseStateByPrimaryKey(Integer id);
    
    List<GoodsPage> selectAll();
    
}