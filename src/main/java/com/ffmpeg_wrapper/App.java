package com.ffmpeg_wrapper;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws InterruptedException, IOException {
		FFmpeg.builder().input("solo_leveling_video.mp4").isOverwriteOutput(true).logFile("logs.log").isHideBanner(true)
				.output("outputOfSolo").workingDirectory("C:\\Users\\msi-pc\\Desktop").endTime("00:1:12").startTime("00:00:10").duration("15")
				.isStreamCopy(false).format("mp3").build().buildCommandAndExecute();

	}
}
