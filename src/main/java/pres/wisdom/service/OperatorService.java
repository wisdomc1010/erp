package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.Operator;
import pres.wisdom.entity.OperatorPage;
import pres.wisdom.vo.Page;

public interface OperatorService {
	public Operator getOperatorById(int id);
	public Operator selectByNamePwd(Operator record);
	public List<Operator> findPage(Page page);
	public int findRows(OperatorPage page);
	public Operator selectByName(String name);
	public int updateStateByPrimaryKey(int id);
	public int updateUseStateByPrimaryKey(int id);
	public List<Operator> selectByCondition(OperatorPage page);
}
