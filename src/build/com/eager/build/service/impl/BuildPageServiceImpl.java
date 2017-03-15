package com.eager.build.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.eager.build.dao.BuildPageDao;
import com.eager.build.domain.Columns;
import com.eager.build.domain.Tables;
import com.eager.build.service.BuildPageService;
import com.eager.core.domain.Tyjcdlst;
import com.eager.core.domain.Tysysper;

@Service("buildBuildPageService")
@Transactional
public class BuildPageServiceImpl implements BuildPageService {
	@Autowired
	private BuildPageDao buildBuildPageDao;

	public boolean buildOnlyPermissionTree(Tables tables) {
		return false;
	}

	public boolean buildSimpleTableOpt(Tables tables) {
		return false;
	}

	public List<Tables> findAllTables() {
		return buildBuildPageDao.findAllTables();
	}

	public List<Columns> findAllColumns(String tablename) {
		return buildBuildPageDao.findAllColumns(tablename);
	}

	public List<Tysysper> findAllpermissionsToList() {
		List<Tysysper> result=buildBuildPageDao.findAllpermissionsToList();
		if(result.size()==0){
			result.add(new Tysysper());
		}
		return buildBuildPageDao.findAllpermissionsToList();
	}

	public List<Tyjcdlst> findAllDlsts() {
		return buildBuildPageDao.findAllDlsts();
	}

	public List<String> findSimpleTableColumns(String tablename) {
		return buildBuildPageDao.findSimpleTableColumns(tablename);
	}

	public void buildSimpleTable(Tables tables, List<Columns> columnlist) {
		
	}
}
