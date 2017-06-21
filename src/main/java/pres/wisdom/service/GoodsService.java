package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.Goods;
import pres.wisdom.entity.GoodsPage;

public interface GoodsService {
	public Goods selectByNum(String num);
	public List<GoodsPage> selectByCondition(GoodsPage page);
	public int findRows(GoodsPage page);
	public int updateStateByPrimaryKey(int id);
	public int updateUseStateByPrimaryKey(int id);
	public List<GoodsPage> selectAll();
}
