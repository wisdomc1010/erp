package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.InportDetailMapper;
import pres.wisdom.entity.InportDetail;
import pres.wisdom.service.InportDetailService;
@Service("inportDetailService")
public class InportDetailServiceImpl implements InportDetailService {
	@Resource
	private InportDetailMapper inportDetailMapper;
	public List<InportDetail> selectByPrimaryKey(Integer id) {
		return this.inportDetailMapper.selectByPrimaryKey(id);
	}

}
