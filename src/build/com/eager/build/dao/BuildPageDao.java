package com.eager.build.dao;

import java.util.List;

import com.eager.build.domain.Columns;
import com.eager.build.domain.Tables;
import com.eager.core.domain.Tyjcdlst;
import com.eager.core.domain.Tysysper;

public interface BuildPageDao {
	List<Tables> findAllTables();

	List<Columns> findAllColumns(String tablename);

	List<Tysysper> findAllpermissionsToList();

	Tysysper addPermission(Tysysper tysysper);

	List<String> findSimpleTableColumns(String tablename);

	/* String getTablesDDL(String tablename); */

	List<Tyjcdlst> findAllDlsts();
}
