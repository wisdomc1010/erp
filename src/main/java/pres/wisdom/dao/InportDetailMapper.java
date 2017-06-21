package pres.wisdom.dao;

import java.util.List;

import pres.wisdom.entity.InportDetail;
import pres.wisdom.entity.InportDetailPage;
import pres.wisdom.entity.InportPage;
import pres.wisdom.util.MyBatisDao;
@MyBatisDao
public interface InportDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InportDetail record);

    int insertSelective(InportDetail record);

    List<InportDetailPage> selectInportDetail(InportPage record);
    
    List<InportDetail> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InportDetail record);

    int updateByPrimaryKey(InportDetail record);
    
    int deleteInportDetail(InportPage record);
}