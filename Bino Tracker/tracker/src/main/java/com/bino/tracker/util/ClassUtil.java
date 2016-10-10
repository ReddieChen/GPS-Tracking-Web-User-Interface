package com.bino.tracker.util;

import java.lang.reflect.Field;

public class ClassUtil {

	public static String toString(Object obj) {
		if (obj == null) {
			return "Object is null";
		}
		String value = obj.getClass().getSimpleName() + " : ";
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				value += f.getName() + " = " + f.get(obj) + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}