package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.AccountDetailPage;

public interface AccountDetailService {
	public int findRows(AccountDetailPage page);
	public List<AccountDetailPage> selectByCondition(AccountDetailPage page);
}
