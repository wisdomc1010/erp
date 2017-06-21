package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.GoodsMapper;
import pres.wisdom.entity.Goods;
import pres.wisdom.entity.GoodsPage;
import pres.wisdom.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsMapper goodsMapper;
	public Goods selectByNum(String num) {
		return this.goodsMapper.selectByNum(num);
	}

	public int findRows(GoodsPage page) {
		return this.goodsMapper.findRows(page);
	}
	public int updateStateByPrimaryKey(int id) {
		return this.goodsMapper.updateStateByPrimaryKey(id);
	}
	public int updateUseStateByPrimaryKey(int id) {
		return this.goodsMapper.updateUseStateByPrimaryKey(id);
	}

	public List<GoodsPage> selectByCondition(GoodsPage page) {
		return this.goodsMapper.selectByCondition(page);
	}

	public List<GoodsPage> selectAll() {
		return this.goodsMapper.selectAll();
	}

}
