package com.lyf.okhttputils.utils;

import com.google.gson.Gson;
import java.lang.reflect.Type;


public class GsonTools {

	private static Gson gson = new Gson();

	/**
	 * 将json字符串转换成bean
	 *
	 * @param json
	 *            json字符串，注意字符串不能带外层参数
	 * @param clazz
	 *            bean对应的类类型
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	/**
	 * 将json字符串转换成bean，用于泛型
	 *
	 * @param json
	 *            json字符串，注意字符串不能带外层参数
	 * @param typeOfT
	 *            对应的type类型，可使用new TypeToken<T>(){}.getType()的方式获取
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * 将对象转换为json字符串
	 */
	public static String toJson(Object src) {
		return gson.toJson(src);
	}

}
