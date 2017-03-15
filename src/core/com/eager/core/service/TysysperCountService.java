package com.eager.core.service;

import com.eager.core.domain.TysysperCount;

public interface TysysperCountService {
	TysysperCount findTysysperCountByTysysperIdAndTysysusrId(Long tysysperId,Long tysysusrId);
	void updateTysysperCount(TysysperCount tysysperCount);
	void addTysysperCount(TysysperCount tysysperCount);
}
