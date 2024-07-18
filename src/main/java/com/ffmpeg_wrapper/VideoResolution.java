package com.ffmpeg_wrapper;

import lombok.Data;

@Data
public class VideoResolution {
	private int width;
	private int height;

	VideoResolution(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
