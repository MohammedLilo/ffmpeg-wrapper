package com.ffmpeg_wrapper.exceptions;

public class FFmpegExecutionException extends Exception {

	private static final long serialVersionUID = 1L;

	public FFmpegExecutionException() {
		super();
	}

	public FFmpegExecutionException(String message) {
		super(message);
	}
}