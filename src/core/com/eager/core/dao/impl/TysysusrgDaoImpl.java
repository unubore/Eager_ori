package com.eager.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.eager.core.dao.BaseDao;
import com.eager.core.dao.TysysusrgDao;
import com.eager.core.domain.PagingBean;
import com.eager.core.domain.Tysysusrg;
import com.eager.core.domain.vo.TysysusrgVo;

@Repository("coreTysysusrgDao")
public class TysysusrgDaoImpl extends BaseDao  implements TysysusrgDao {

	@Override
	public PagingBean<Tysysusrg> findAllTysysusrgs(TysysusrgVo tysysusrg) {
		
		return null;
	}

	@Override
	public Tysysusrg getTysysusrgById(Long id) {
		return getSqlSession().selectOne("core.tysysusrg.getTysysusrgById", id);
	}

	@Override
	public Tysysusrg addTysysusrg(Tysysusrg tysysusrg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tysysusrg updateTysysusrg(Tysysusrg tysysusrg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTysysusrgById(Long id) {
		// TODO Auto-generated method stub

	}

}
