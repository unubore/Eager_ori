package com.eager.core.dao;

import java.util.List;

import com.eager.core.domain.Tysysper;
import com.eager.core.domain.Tysysusr;

public interface TysysperDao {
	List<Tysysper> findTysyspersByTysysusr(Tysysusr tysysusr);

	Tysysper getTysysperByid(Long tysysperid);

	Tysysper addTysysper(Tysysper tysysper);

	Tysysper findTysysperByUrl(String url);
}
