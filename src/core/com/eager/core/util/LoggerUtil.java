package com.eager.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 当前工具类暂支持问题处理，最常用一种使用方式,如果需要输出详细信息方便出错调试,请在msg参数加上"{}"占位符号
 * 
 * LoggerUtil.error("传递参数有误!{}", e);
 * 
 * @ClassName: TraceLogUtil
 * @Description: TODO
 * @author: N-242
 * @date: 2015年8月6日 下午1:32:55
 */
public class LoggerUtil {

	private static Logger getLogger() {
		return LoggerFactory.getLogger(new Throwable().getStackTrace()[1].getClassName());
	}

	private static void error(String msg, Exception e) {
		innerError(innerGet(), msg, e);
	}

	private static void error(Logger log, String msg, Exception e) {
		innerError(log, msg, e);
	}

	private static void info(String msg, Exception e) {
		innerInfo(innerGet(), msg, e);
	}

	private static void info(Logger log, String msg, Exception e) {
		innerInfo(log, msg, e);
	}

	private static void debug(Logger log, String msg, Exception e) {
		innerDebug(log, msg, e);
	}

	private static void debug(String msg, Exception e) {
		innerDebug(innerGet(), msg, e);
	}

	private static void warn(String msg, Exception e) {
		innerWarn(innerGet(), msg, e);
	}

	private static void warn(Logger log, String msg, Exception e) {
		innerWarn(log, msg, e);
	}

	private static void trace(String msg, Exception e) {
		innerTrace(innerGet(), msg, e);
	}

	private static void trace(Logger log, String msg, Exception e) {
		innerTrace(log, msg, e);
	}

	private static void innerError(Logger log, String msg, Exception e) {
		log.error(msg, processTrace(e));
	}

	private static void innerDebug(Logger log, String msg, Exception e) {
		log.debug(msg, processTrace(e));
	}

	private static void innerWarn(Logger log, String msg, Exception e) {
		log.warn(msg, processTrace(e));
	}

	private static void innerTrace(Logger log, String msg, Exception e) {
		log.trace(msg, processTrace(e));
	}

	private static void innerInfo(Logger log, String msg, Exception e) {
		log.info(msg, processTrace(e));
	}

	public static String processTrace(Exception e) {
		if (null != e && e.getStackTrace() != null) {
			StackTraceElement stackTraceElement = e.getStackTrace()[e.getStackTrace().length - 1];
			StringBuffer sb = new StringBuffer();
			sb.append("error messag detail：").append(e.toString()).append(",").append("file=")
					.append(stackTraceElement.getFileName()).append(",").append("line=")
					.append(stackTraceElement.getLineNumber()).append(",").append("method=")
					.append(stackTraceElement.getMethodName());
			return sb.toString();
		}
		return null;
	}

	private static Logger innerGet() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[3].getClassName());
	}
}
