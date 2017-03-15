package com.eager.build.service;

import java.util.List;

import com.eager.build.domain.Columns;
import com.eager.build.domain.Tables;
import com.eager.core.domain.Tyjcdlst;
import com.eager.core.domain.Tysysper;

public interface BuildPageService {
	boolean buildOnlyPermissionTree(Tables tables);
	boolean buildSimpleTableOpt(Tables tables);
	List<Tables> findAllTables();
	List<Columns> findAllColumns(String tablename);
	List<Tysysper> findAllpermissionsToList();
	List<Tyjcdlst> findAllDlsts();
	List<String> findSimpleTableColumns(String tablename);
	void buildSimpleTable(Tables tables,List<Columns> columnlist);
}
