package com.eager.core.constract;

import java.util.ArrayList;
import java.util.List;

public class GlobalValue {

	public final static String SESSION_ID = "sid";
	public final static String USER_ID = "uid";
	public final static String DATA_KEY = "dkey";
	public final static String LOGIN_FAILURE_MSG = "login_failure_msg";
	public static final String POSTGRESQL_SCHEMA = "eager";
	public static final String ERROR_MESSAGE = "数据异常请联系管理员";
	public static final String OPTIONTYPE_ADD = "add";

	public static final String DATABASE_TYPE = DatabaseType.Postgresql.name();
	public final static List<String> IS_NOT_LOGIN_VALIDATE_PATH = new ArrayList<String>();
	static {
		String isNotLoginValidatePaths = GridProperties.getKey("isNotValidatePath");
		String[] isNotLoginValidatePathArray = isNotLoginValidatePaths.split("\\;");
		for (String path : isNotLoginValidatePathArray) {
			IS_NOT_LOGIN_VALIDATE_PATH.add(path);
		}
	}
}
