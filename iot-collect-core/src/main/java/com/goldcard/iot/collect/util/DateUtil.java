package com.goldcard.iot.collect.util;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date string2Date(String source, String pattern) {
		try {
			if (StringUtils.isBlank(source)) {
				return null;
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date = format.parse(source);
			return date;
		} catch (Exception e) {

		}
		return null;
	}

	public static String dateFormat(Date source, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(source);
		return str;
	}

}
