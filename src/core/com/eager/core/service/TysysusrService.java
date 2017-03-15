package com.eager.core.service;

import com.eager.core.domain.PagingBean;
import com.eager.core.domain.Tysysusr;
import com.eager.core.domain.vo.TysysusrVo;

public interface TysysusrService {
	Tysysusr login(Tysysusr tysysusr);

	PagingBean<Tysysusr> findAllTysysusrs(TysysusrVo tysysusr);

	Tysysusr getTysysusrById(Long id);

	Tysysusr getTysysusrByUsername(String username);

	Tysysusr addTysysusr(Tysysusr tysysusr);

	Tysysusr updateTysysusr(Tysysusr tysysusr);

	void deleteTysysusrById(Long id);

	void updateFailureTimesById(Long id, int times);

	void lockTysysusrByid(Long id);

	void updateTysysusrForLogin(Tysysusr tysysusr);
}
