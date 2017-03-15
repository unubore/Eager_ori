package com.eager.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.eager.core.dao.BaseDao;
import com.eager.core.dao.TysysusrDao;
import com.eager.core.dao.TysysusrgDao;
import com.eager.core.domain.PagingBean;
import com.eager.core.domain.Tysysusr;
import com.eager.core.domain.vo.TysysusrVo;

@Repository("coreTysysusrDao")
public class TysysusrDaoImpl extends BaseDao implements TysysusrDao {
	private static Logger logger = LoggerFactory.getLogger(TysysusrDaoImpl.class);
	private TysysusrgDao tysysusrgDao;

	@Override
	public Tysysusr login(Tysysusr tysysusr) {
		return getSqlSession().selectOne("core.tysysusr.login", tysysusr);
	}

	@Override
	public PagingBean<Tysysusr> findAllTysysusrs(TysysusrVo tysysusrVo) {
		List<Tysysusr> list = getSqlSession().selectList("core.tysysusr.findAllTysysusrs", tysysusrVo);
		if (list.size() > 0) {
			for (Tysysusr tysysusr : list) {
				if (tysysusr.getGroup() != null && tysysusr.getGroup().getId() != null) {
					tysysusr.setGroup(tysysusrgDao.getTysysusrgById(tysysusr.getGroup().getId()));
				}
			}
		}
		int size = tysysusrVo.getPageSize() - list.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				Tysysusr temp = new Tysysusr();
				temp.setId((long) -i - 1);
				list.add(temp);
			}
		}
		PagingBean<Tysysusr> result = new PagingBean<Tysysusr>(tysysusrVo, list);
		return result;
	}

	@Override
	public Tysysusr getTysysusrById(Long id) {
		return getSqlSession().selectOne("core.tysysusr.getTysysusrById", id);
	}

	@Override
	public Tysysusr addTysysusr(Tysysusr tysysusr) {
		getSqlSession().insert("core.tysysusr.addTysysusr", tysysusr);
		return this.getTysysusrById(tysysusr.getId());
	}

	@Override
	public Tysysusr updateTysysusr(Tysysusr tysysusr) {
		getSqlSession().update("core.tysysusr.updateTysysusr", tysysusr);
		return this.getTysysusrById(tysysusr.getId());
	}

	@Override
	public void deleteTysysusrById(Long id) {
		getSqlSession().delete("core.tysysusr.deleteTysysusrById", id);
	}

	@Override
	public Tysysusr getTysysusrByUsername(String username) {
		return getSqlSession().selectOne("core.tysysusr.getTysysusrByUsername", username);
	}

	@Override
	public void updateFailureTimesById(Long id, int times) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("times", times);
		getSqlSession().update("core.tysysusr.updateFailureTimesById", map);

	}

	@Override
	public void lockTysysusrByid(Long id) {
		getSqlSession().update("core.tysysusr.lockTysysusrByid", id);
	}

	@Override
	public void updateTysysusrForLogin(Tysysusr tysysusr) {
		getSqlSession().update("core.tysysusr.updateTysysusrForLogin", tysysusr);
	}

}
