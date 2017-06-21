package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.OperatorMapper;
import pres.wisdom.entity.Operator;
import pres.wisdom.entity.OperatorPage;
import pres.wisdom.service.OperatorService;
import pres.wisdom.vo.Page;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {
	@Resource
	private OperatorMapper operatorMapper;
	public Operator getOperatorById(int id) {
		return this.operatorMapper.selectByPrimaryKey(id);
	}

	public List<Operator> findPage(Page page) {
		return this.operatorMapper.findPage(page);
	}
	public int findRows(OperatorPage page) {
		return this.operatorMapper.findRows(page);
	}

	public Operator selectByNamePwd(Operator record) {
		return this.operatorMapper.selectByNamePwd(record);
	}

	public Operator selectByName(String name) {
		return this.operatorMapper.selectByName(name);
	}

	public int updateUseStateByPrimaryKey(int id) {
		return this.operatorMapper.updateUseStateByPrimaryKey(id);
	}

	public int updateStateByPrimaryKey(int id) {
		return this.operatorMapper.updateStateByPrimaryKey(id);
	}

	public List<Operator> selectByCondition(OperatorPage page) {
		return this.operatorMapper.selectByCondition(page);
	}
}
