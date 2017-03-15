package com.eager.core.util;

import java.util.List;

public class StringUtil {

	public static String firstCharLowCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String firstCharUpCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String addPrefix(int num, String prefix, int length) {
		return String.format("%04d", num);
	}

	public static boolean isStringAvaliable(String string) {
		return string != null && !"".equals(string.trim());
	}

	public static boolean isListSizeGtZero(List list) {
		return list != null && list.size() > 0;
	}

	public static Boolean notSame(String dest, String scre) {
		if (null == dest || null == scre)
			return false;
		else
			return !dest.equals(scre);
	}

	/**
	 * 去掉字符串前后空格
	 * 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		int i = s.length();
		int j = 0;// 字符串第一个字符
		int k = 0;// 中间变量
		char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
		while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
			++j;// 确定字符串前面的空格数
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
			--i;// 确定字符串后面的空格数
		return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
	}

	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");

		return str.replaceAll("[(/>)<]", "");
	}
}
