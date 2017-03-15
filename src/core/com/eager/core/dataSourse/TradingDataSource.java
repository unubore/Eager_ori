package com.eager.core.dataSourse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TradingDataSource {
	String dataSourceName() default DataSourceConst.GRIDDEFAULT;
}
