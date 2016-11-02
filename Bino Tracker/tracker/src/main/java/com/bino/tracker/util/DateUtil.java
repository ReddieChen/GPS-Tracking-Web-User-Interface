package com.bino.tracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		return sdf.format(date);
	}

	public static Date toDate(String dateString) {
		if (dateString == null || dateString.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
