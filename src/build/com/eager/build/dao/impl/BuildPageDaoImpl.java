package com.eager.build.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eager.build.dao.BuildPageDao;
import com.eager.build.domain.Columns;
import com.eager.build.domain.Tables;
import com.eager.core.constract.GlobalValue;
import com.eager.core.dao.BaseDao;
import com.eager.core.dao.TysysperDao;
import com.eager.core.domain.Tyjcdlst;
import com.eager.core.domain.Tysysper;
import com.eager.core.util.ThreadVariable;

@Repository("buildBuildPageDao")
public class BuildPageDaoImpl extends BaseDao implements BuildPageDao {
	@Autowired
	private TysysperDao coreTysysperDao;

	public List<Tables> findAllTables() {
		return getSqlSession().selectList("build.buildtables.findAllTables" + GlobalValue.DATABASE_TYPE,
				GlobalValue.POSTGRESQL_SCHEMA);
	}

	public List<Columns> findAllColumns(String tablename) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tablename", tablename);
		map.put("schemaname", GlobalValue.POSTGRESQL_SCHEMA);
		return getSqlSession().selectList("build.buildtables.findAllColumns" + GlobalValue.DATABASE_TYPE, map);
	}

	public List<Tysysper> findAllpermissionsToList() {
		return coreTysysperDao.findTysyspersByTysysusr(ThreadVariable.getTysysusr());
	}

	public Tysysper addPermission(Tysysper tysysper) {
		return coreTysysperDao.addTysysper(tysysper);
	}

	public List<String> findSimpleTableColumns(String tablename) {
		return getSqlSession().selectList("build.buildtables.findSimpleTableColumns" + GlobalValue.DATABASE_TYPE,
				tablename);
	}

	/*
	 * public String getTablesDDL(String tablename) { return
	 * getSqlSession().selectOne("build.buildtables.getTablesDDL", tablename); }
	 */

	public List<Tyjcdlst> findAllDlsts() {
		return getSqlSession().selectList("build.buildtables.findAllDlsts" + GlobalValue.DATABASE_TYPE);
	}

}
