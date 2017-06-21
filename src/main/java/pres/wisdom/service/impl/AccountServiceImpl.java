package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.AccountMapper;
import pres.wisdom.entity.AccountPage;
import pres.wisdom.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Resource
	private AccountMapper accountMapper;
	
	public List<AccountPage> selectByCondition(AccountPage page) {
		return this.accountMapper.selectByCondition(page);
	}

	public int findRows(AccountPage page) {
		return this.accountMapper.findRows(page);
	}

}
