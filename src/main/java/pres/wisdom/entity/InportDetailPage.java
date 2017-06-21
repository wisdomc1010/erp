package pres.wisdom.entity;

import pres.wisdom.vo.Page;

public class InportDetailPage extends Page {
    private Integer id;

    private String inportNum;

    private Integer Type;
    
    private Integer goodsId;
    
    private String goodsNum;
    
    private String goodsName;
    
    private Integer number;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInportNum() {
        return inportNum;
    }

    public void setInportNum(String inportNum) {
        this.inportNum = inportNum == null ? null : inportNum.trim();
    }
    
    public Integer getType() {
		return Type;
	}

	public void setType(Integer type) {
		Type = type;
	}

	public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
    
    
}