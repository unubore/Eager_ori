package com.eager.core.service;

import java.util.List;

import com.eager.core.domain.Tysysper;
import com.eager.core.domain.Tysysusr;

public interface TysysperService {
	List<Tysysper> findTysyspersByTysysusr(Tysysusr tysysusr);
	Tysysper getTysysperByid(Long tysysperid);
	Tysysper findTysysperByUrl(String url);
}
