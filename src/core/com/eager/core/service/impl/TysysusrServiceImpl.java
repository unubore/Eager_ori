package com.eager.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eager.core.dao.TysysusrDao;
import com.eager.core.dataSourse.TradingDataSource;
import com.eager.core.domain.PagingBean;
import com.eager.core.domain.Tysysusr;
import com.eager.core.domain.vo.TysysusrVo;
import com.eager.core.exception.ServiceException;
import com.eager.core.service.TysysusrService;
import com.eager.core.util.MD5Util;

@Service("coreTysysusrService")
@Transactional
public class TysysusrServiceImpl implements TysysusrService {

	@Autowired
	private TysysusrDao coreTysysusrDao;

	public Tysysusr login(Tysysusr tysysusr) {
		return coreTysysusrDao.login(tysysusr);
	}

	@TradingDataSource
	public PagingBean<Tysysusr> findAllTysysusrs(TysysusrVo tysysusr) {
		return coreTysysusrDao.findAllTysysusrs(tysysusr);
	}

	public Tysysusr getTysysusrById(Long id) {
		return coreTysysusrDao.getTysysusrById(id);
	}

	public Tysysusr addTysysusr(Tysysusr tysysusr) {
		if (!tysysusr.canSave()) {
			throw new ServiceException("缺少必要数值请补全");
		}
		tysysusr.setPassword(MD5Util.string2MD5("11111111"));
		return coreTysysusrDao.addTysysusr(tysysusr);
	}

	public Tysysusr updateTysysusr(Tysysusr tysysusr) {
		if (!tysysusr.canSave()) {
			throw new ServiceException("缺少必要数值请补全");
		}
		return coreTysysusrDao.updateTysysusr(tysysusr);
	}

	public void deleteTysysusrById(Long id) {
		coreTysysusrDao.deleteTysysusrById(id);
	}

	@Override
	public Tysysusr getTysysusrByUsername(String username) {
		return coreTysysusrDao.getTysysusrByUsername(username);
	}

	@Override
	public void updateFailureTimesById(Long id, int times) {
		coreTysysusrDao.updateFailureTimesById(id, times);

	}

	@Override
	public void lockTysysusrByid(Long id) {
		coreTysysusrDao.lockTysysusrByid(id);
	}

	@Override
	public void updateTysysusrForLogin(Tysysusr tysysusr) {
		coreTysysusrDao.updateTysysusrForLogin(tysysusr);

	}

}
