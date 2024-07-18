package com.ffmpeg_wrapper;

public enum FFprobeOutputFormat {
	NOPRINTWRAPPERS_NOKEY("default=noprint_wrappers=1:nokey=1"),
	NOPRINT("default=noprint_wrappers=1"),
	JSON("json"),
	COMPACT("compact"),
	CSV("csv"),
	FLAT("flat"),
	INI("ini"),
	XML("xml");

	private final String format;

	FFprobeOutputFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	@Override
	public String toString() {
		return format;
	}
}
