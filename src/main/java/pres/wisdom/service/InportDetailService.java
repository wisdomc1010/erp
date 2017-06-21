package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.InportDetail;

public interface InportDetailService {
	public List<InportDetail> selectByPrimaryKey(Integer id);
}
