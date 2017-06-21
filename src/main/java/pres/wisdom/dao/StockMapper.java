package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.Stock;
import pres.wisdom.entity.StockPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
    
    int findRows(StockPage page);
    
    List<StockPage> selectByCondition(StockPage page);
    
    StockPage selectByGoodsId(Integer goodsId);
    
    int updateByGoodsId(Stock record);
}