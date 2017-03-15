package com.eager.core.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eager.core.dataSourse.DataSourceConst;
import com.eager.core.dataSourse.DataSourceHandle;
import com.eager.core.dataSourse.TradingDataSource;
import com.eager.core.exception.ServiceException;
import com.eager.core.util.GenericsUtils;
import com.eager.core.util.LoggerUtil;
import com.eager.core.util.ThreadVariable;

@Aspect
@Component
@Order(1)
public class TradingDataSourceAspect {

	final static Logger logger = LoggerFactory.getLogger(TradingDataSourceAspect.class);

	@Around(value = "@within(com.eager.core.dataSourse.TradingDataSource) || "
			+ "@annotation(com.eager.core.dataSourse.TradingDataSource)")
	public Object interceptor(ProceedingJoinPoint pjp) throws Exception {
		Object object = null;
		TradingDataSource tradingDataSource = null;
		try {
			// 在类上找
			tradingDataSource = pjp.getTarget().getClass().getAnnotation(TradingDataSource.class);
			if (tradingDataSource == null) {
				// 在方法上找
				tradingDataSource = getMethod(pjp).getAnnotation(TradingDataSource.class);
			}
			if (tradingDataSource != null) {
				// 如果检测到需要切换，则进行处理
				if (DataSourceConst.GRIDOTHER.equals(tradingDataSource.dataSourceName())) {
					if (null != ThreadVariable.getDataSource()) {
						DataSourceHandle.setDataSourceType(ThreadVariable.getDataSource().toUpperCase());
					} else {
						throw new Exception("不存在的数据源");
					}
				} else {
					DataSourceHandle.setDataSourceType(tradingDataSource.dataSourceName());
				}
			}
			object = pjp.proceed();
			return object;
		} catch (Exception e) {
			logger.error("切换数据源时出错{}", LoggerUtil.processTrace(e));
			throw new ServiceException(e);
		} catch (Throwable e) {
			logger.error("切换数据源时出错：", e);
			throw new ServiceException(e);
		} finally {
			if (tradingDataSource != null) {
				// 切换后清除
				DataSourceHandle.clearDataSourceType();
			}
		}
	}

	private Method getMethod(ProceedingJoinPoint point) throws Exception {
		// 拦截的实体类
		Object target = point.getTarget();
		// 拦截的方法名称
		String methodName = point.getSignature().getName();
		// 拦截的方法参数
		Object[] args = point.getArgs();
		// 拦截的放参数类型
		Class[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();

		Method m = null;
		try {
			// 通过反射获得拦截的method
			m = target.getClass().getMethod(methodName, parameterTypes);
			// 如果是桥则要获得实际拦截的method
			if (m.isBridge()) {
				for (int i = 0; i < args.length; i++) {
					// 获得泛型类型
					Class genClazz = GenericsUtils.getSuperClassGenricType(target.getClass());
					// 根据实际参数类型替换parameterType中的类型
					if (args[i].getClass().isAssignableFrom(genClazz)) {
						parameterTypes[i] = genClazz;
					}
				}
				// 获得parameterType参数类型的方法
				m = target.getClass().getMethod(methodName, parameterTypes);
			}
			return m;
		} catch (SecurityException e) {
			logger.error("切换数据源时获取方法出错：", e);
			throw new ServiceException(e);
		} catch (NoSuchMethodException e) {
			logger.error("切换数据源时获取方法出错：", e);
			throw new ServiceException(e);
		}
	}

}