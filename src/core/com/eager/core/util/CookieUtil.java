package com.eager.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eager.core.constract.GlobalValue;

public class CookieUtil {

	private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	public static String getSessionId() {
		return getValueFromCookies(GlobalValue.SESSION_ID);
	}

	private static String getValueFromCookies(String key) {
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if (cookies == null) {
			return "";
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return "";
	}

	public static String getSesssionIdFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (GlobalValue.SESSION_ID.equals(cookie.getName())) {
				String sid = cookie.getValue();
				return sid;
			}
		}
		return null;
	}

	public static void putSessionIdInCookies(HttpServletRequest request, HttpServletResponse response, String key,
			String sessionId) {
		Cookie cookie = new Cookie(key, sessionId);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void saveObjectToCookie(HttpServletRequest request, HttpServletResponse response, String key,
			Object amchartData) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(amchartData);

			String cookieValue = new String(baos.toByteArray(), "UTF-8");
			Cookie cookie = new Cookie(key, cookieValue);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(6000);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("异常信息", e);
		}
	}

	public static Object getObjectFromCookie(String cookieValue) {
		try {
			// String decoderCookieValue =
			// java.net.URLDecoder.decode(cookieValue, " UTF-8 " );
			Object result = new Object();
			ByteArrayInputStream bais = new ByteArrayInputStream(cookieValue.getBytes("UTF-8"));
			ObjectInputStream ios = new ObjectInputStream(bais);
			result = ios.readObject();
			return result;
		} catch (Exception e) {
			logger.error("异常信息", e);
			return null;
		}
	}

	public static Object getObject(byte[] bt) {
		Object oo = null;
		ObjectInputStream objIps;

		// 注意这里，ObjectInputStream 对以前使用 ObjectOutputStream
		// 写入的基本数据和对象进行反序列化。问题就在这里，你是直接将byte[]数组传递过来，而这个byte数组不是使用ObjectOutputStream类写入的。所以问题解决的办法就是：用输出流得到byte[]数组。
		try {
			objIps = new ObjectInputStream(new ByteArrayInputStream(bt));
			oo = (Object) objIps.readObject();
		} catch (Exception e) {
			logger.error("异常信息", e);
		}
		return oo;
	}

	public static byte[] getByte(Object object) {
		byte[] bt = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			if (object != null) {
				ObjectOutputStream objos = new ObjectOutputStream(baos);
				objos.writeObject(object);
				bt = baos.toByteArray();
			}
		} catch (Exception e) {
			bt = (byte[]) null;
			logger.error("异常信息", e);

		}
		return bt;
	}

	/*
	 * public static void main(String[] args) { String s = "helloworld!"; byte[]
	 * bt = null;
	 * 
	 * ArrayList<String> list = new ArrayList<String>();
	 * 
	 * ArrayList<String> byte_list = new ArrayList<String>();
	 * 
	 * byte_list.add(s);
	 * 
	 * bt = getByte(byte_list);// 通过调用getByte()方法得到bt[]数组。 list =
	 * (ArrayList<String>) getObject(bt);
	 * 
	 * System.out.println(list.size()); System.out.println(list.get(0));
	 * 
	 * }
	 */

}
