package com.ffmpeg_wrapper.enums;

import lombok.Getter;

@Getter
public enum VideoResolution {
    P480(640,480),
    P720(1280,720),
    P1080(1920,1080),
    P_4K(3840,2160),
    P_8K(7680,4320);
	
	private final int width;
	private final int height;

	VideoResolution(int width,int height) {
		this.width = width;
		this.height = height;
	}

}
