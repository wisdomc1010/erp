package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.SaleDetail;
import pres.wisdom.entity.SaleDetailPage;
import pres.wisdom.entity.SalePage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface SaleDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleDetail record);

    int insertSelective(SaleDetail record);

    SaleDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleDetail record);

    int updateByPrimaryKey(SaleDetail record);
    
    int deleteSaleDetail(SalePage record);
    
    List<SaleDetailPage> selectBySale(SalePage record);
    
    int selectBySaleNum(SaleDetail record);
}