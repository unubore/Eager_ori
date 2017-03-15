package com.eager.core.dao;

import com.eager.core.domain.PagingBean;
import com.eager.core.domain.Tysysusrg;
import com.eager.core.domain.vo.TysysusrgVo;

public interface TysysusrgDao {
	PagingBean<Tysysusrg> findAllTysysusrgs(TysysusrgVo tysysusrg);
	Tysysusrg getTysysusrgById(Long id);
	Tysysusrg addTysysusrg(Tysysusrg tysysusrg);
	Tysysusrg updateTysysusrg(Tysysusrg tysysusrg);
	void deleteTysysusrgById(Long id);
}
