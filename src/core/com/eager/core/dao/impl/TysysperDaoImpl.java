package com.eager.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.eager.core.dao.BaseDao;
import com.eager.core.dao.TysysperDao;
import com.eager.core.domain.Tysysper;
import com.eager.core.domain.Tysysusr;
import com.eager.core.exception.DAOException;

@Repository("coreTysysperDao")
public class TysysperDaoImpl extends BaseDao implements TysysperDao {
	private static Logger logger = LoggerFactory.getLogger(TysysperDaoImpl.class);

	public Tysysusr login(Tysysusr tysysusr) {
		return getSqlSession().selectOne("core.tysysusr.login", tysysusr);
	}

	public List<Tysysper> findTysyspersByTysysusr(Tysysusr tysysusr) {
		if (tysysusr != null) {
			if (tysysusr.getIsAdmin() == 1) {
				return getSqlSession().selectList("core.tysysper.findAllPermissions");
			} else {
				if (tysysusr.getGroup() != null) {
					return getSqlSession().selectList("core.tysysper.findPermissionsByTysysusrId",
							tysysusr.getGroup().getId());
				} else {
					throw new DAOException("无法获取到用户组信息");
				}
			}
		} else {
			return new ArrayList<Tysysper>();
		}
	}

	public Tysysper getTysysperByid(Long tysysperid) {
		return getSqlSession().selectOne("core.tysysper.getPermissionsById", tysysperid);
	}

	@Override
	public Tysysper findTysysperByUrl(String url) {
		List<Tysysper> tysyspers = getSqlSession().selectList("core.tysysper.findTysysperByUrl", url);
		if (tysyspers == null || tysyspers.size() == 0) {
			return null;
		}
		return tysyspers.get(0);
	}

	@Override
	public Tysysper addTysysper(Tysysper tysysper) {
		Long id = (long) getSqlSession().insert("core.tysysper.addTysysper", tysysper);
		return getTysysperByid(id);
	}

}
