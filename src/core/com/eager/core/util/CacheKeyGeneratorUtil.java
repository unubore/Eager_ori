package com.eager.core.util;

public class CacheKeyGeneratorUtil {
	public static String generateCacheKeyFromId(Class<?> clazz, Long id) {
		if (null == id) {
			id = -1L;
		}
		return clazz.getName() + "_" + id.toString();
	}

	public static String generateCacheKeyFromString(Class<?> clazz, String name) {
		return clazz.getName() + "_keystring:" + name;
	}

	public static String generateCacheKeyFromMethodName(Class<?> clazz, String methodname, String paramText) {
		return clazz.getName() + ":" + methodname + ",param:" + paramText;
	}

	public static String intArrayToString(int[] ints) {
		StringBuffer result = new StringBuffer();
		if (ints != null && ints.length > 0) {
			for (int value : ints) {
				result.append(",").append(value);
			}
		}
		return result.toString();
	}
}
