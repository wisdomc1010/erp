package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.InportPage;

public interface InportService {
	public int findRows(InportPage page);
	public List<InportPage> selectByCondition(InportPage page);
	public String findPinYin(Integer supplierId);
	public String findMaxNo(InportPage page);
}
