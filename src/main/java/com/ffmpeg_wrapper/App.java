package com.ffmpeg_wrapper;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws InterruptedException, IOException {
//		FFmpeg.builder().input("solo_leveling_video.mp4").isOverwriteOutput(true).logFile("logs.log").isHideBanner(true)
//				.output("outputOfSolo.mp4").workingDirectory("C:\\Users\\msi-pc\\Desktop").endTime("00:1:12").startTime("00:00:10").duration("15")
//				.isStreamCopy(false)/*.format("mp4")*/.build().buildCommandAndExecute();
//		FFprobe f=FFprobe.builder().isHideBanner(false).inputFilePath("big_bunny_video.mp4").workingDirectory("C:\\Users\\msi-pc\\Desktop")
//				.outputFilePath("app_output.txt").entryToShow(StreamMetadata.ALL)
//				.entryToShow(StreamMetadata.CODEC_NAME).entryToShow(StreamMetadata.DURATION).ffprobeOutputFormat(FFprobeOutputFormat.INI).entryToShow(StreamMetadata.CHANNELS).build();
//		f.probe();
//		System.out.println(f.getCommand());
//		FFmpeg.builder().videoResolution(720 + "x" + 480).input("C:\\Users\\msi-pc\\Desktop\\shortsolo.mp4").isOverwriteOutput(true).output("C:\\Users\\msi-pc\\Desktop\\newresolution").format("mp4").build().buildCommandAndExecute();
//FFmpegUtil.extractVideoSegment("C:\\Users\\msi-pc\\Desktop\\solo_leveling_video.mp4", "C:\\Users\\msi-pc\\Desktop\\new.mp4", "00:00:10", "00:00:4.432");
//		FFmpegUtil.changeResolution("C:\\Users\\msi-pc\\Desktop\\shortsolo.mp4", "C:\\Users\\msi-pc\\Desktop\\newresolution.mp4", 2020, 720);

//		FFprobe f = FFprobe.builder().inputFilePath("shortsolo.mp4").workingDirectory("C:\\Users\\msi-pc\\Desktop")
//				.outputFilePath(null).entryToShow(StreamMetadata.ALL).entryToShow(StreamMetadata.CODEC_NAME)
//				.ffprobeOutputFormat(FFprobeOutputFormat.JSON).entryToShow(StreamMetadata.CHANNELS).build();
//		f.probe();
//		FFmpegUtil.changeResolution("C:\\Users\\msi-pc\\Desktop\\shortsolo.mp4", "C:\\Users\\msi-pc\\Desktop\\shortsolochange.mp4",VideoResolution.P480);
		System.out.println(FFprobeUtil.extractMetadata("C:\\Users\\msi-pc\\Desktop\\solo_leveling_video.mp4"));

	}
}
