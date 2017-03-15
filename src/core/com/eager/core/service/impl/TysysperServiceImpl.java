package com.eager.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eager.core.constract.CacheNamePrefix;
import com.eager.core.dao.TysysperDao;
import com.eager.core.domain.Tysysper;
import com.eager.core.domain.Tysysusr;
import com.eager.core.exception.DAOException;
import com.eager.core.exception.ServiceException;
import com.eager.core.jedis.JedisManager;
import com.eager.core.service.TysysperService;

@Service("coreTysysperService")
@Transactional
public class TysysperServiceImpl implements TysysperService {

	@Autowired
	private TysysperDao coreTysysperDao;
	@Autowired
	private JedisManager jedisManager;

	@Override
	public List<Tysysper> findTysyspersByTysysusr(Tysysusr tysysusr) {
		try {
			return coreTysysperDao.findTysyspersByTysysusr(tysysusr);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	@Override
	public Tysysper getTysysperByid(Long tysysperid) {
		Tysysper tysysper;
		try {
			 tysysper = (Tysysper) jedisManager.get(CacheNamePrefix.TYSYSPER_PREFIX + tysysperid);
			if (tysysper == null) {
				tysysper= coreTysysperDao.getTysysperByid(tysysperid);
				jedisManager.set(CacheNamePrefix.TYSYSPER_PREFIX + tysysper.getId(),tysysper);
			}
		} catch (Exception e) {
			tysysper= coreTysysperDao.getTysysperByid(tysysperid);
		}
		return tysysper;
	}

	@Override
	public Tysysper findTysysperByUrl(String url) {
		
		return coreTysysperDao.findTysysperByUrl(url);
	}

}
