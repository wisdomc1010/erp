package pres.wisdom.service;

import java.util.List;

import pres.wisdom.entity.AccountPage;

public interface AccountService {
	public int findRows(AccountPage page);
	public List<AccountPage> selectByCondition(AccountPage page);
}
