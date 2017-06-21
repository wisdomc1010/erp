package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.InportMapper;
import pres.wisdom.entity.InportPage;
import pres.wisdom.service.InportService;
@Service("inportService")
public class InportServiceImpl implements InportService {
	@Resource
	private InportMapper inportMapper;
	public int findRows(InportPage page) {
		return this.inportMapper.findRows(page);
	}

	public List<InportPage> selectByCondition(InportPage page) {
		return this.inportMapper.selectByCondition(page);
	}

	public String findPinYin(Integer supplierId) {
		return this.inportMapper.findPinYin(supplierId);
	}

	public String findMaxNo(InportPage page) {
		return this.inportMapper.findMaxNo(page);
	}

}
