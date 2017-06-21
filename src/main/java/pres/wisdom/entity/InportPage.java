package pres.wisdom.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import pres.wisdom.vo.Page;

public class InportPage extends Page {
	private Integer id;
	
	private String inportNum;
	
    private Integer supplierId;
    
    private String supplier;

    private Integer type;
    
    private Integer sumNumber;
    
    private Integer state;

    private Integer createId;

    private String createOperator;
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date createTime;

    private Integer updateId;

    private String updateOperator;
    
    private Date updateTime;
    
    private Integer reviewId;

    private String reviewOperator;
    
    private Date reviewTime;
    
    private Integer accountId;

    private String accountOperator;
    
    private Date accountTime;

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
		this.inportNum = inportNum;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public String getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public String getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewOperator() {
		return reviewOperator;
	}

	public void setReviewOperator(String reviewOperator) {
		this.reviewOperator = reviewOperator;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountOperator() {
		return accountOperator;
	}

	public void setAccountOperator(String accountOperator) {
		this.accountOperator = accountOperator;
	}

	public Date getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
