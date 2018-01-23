package com.lyf.okhttputils.utils;

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class GsonTools {

	private static Gson gson = new Gson();

	/**
	 * parse the json to an Instance of clazz
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	/**
	 * parse the json to an Instance of typeOft
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * parse the object to json string.
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

}
