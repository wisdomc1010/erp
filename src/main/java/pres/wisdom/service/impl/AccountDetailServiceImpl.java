package pres.wisdom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pres.wisdom.dao.AccountDetailMapper;
import pres.wisdom.entity.AccountDetailPage;
import pres.wisdom.service.AccountDetailService;
@Service("accountDetailService")
public class AccountDetailServiceImpl implements AccountDetailService {
	@Resource
	private AccountDetailMapper accountDetailMapper;
	public int findRows(AccountDetailPage page) {
		return this.accountDetailMapper.findRows(page);
	}

	public List<AccountDetailPage> selectByCondition(AccountDetailPage page) {
		return this.accountDetailMapper.selectByCondition(page);
	}

}
