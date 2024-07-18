package com.ffmpeg_wrapper;

import java.util.regex.Pattern;

public class TextUtils {
	public static String snakeToCamelCase(String old) {
		if (old == null || old.isEmpty()) {
			return "";
		}
		String str = new String(old);

	        str = Pattern.compile("_([a-z])")
	                    .matcher(str)
	                    .replaceAll(m -> m.group(1).toUpperCase());
		return str;
	}
}
