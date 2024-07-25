package com.ffmpeg_wrapper;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class App {
	public static void main(String[] args) throws InterruptedException, IOException {
//		FFmpeg.builder().input("solo_leveling_video.mp4").isOverwriteOutput(true).logFile("logs.log").isHideBanner(true)
//				.output("outputOfSoloooooooooo.mp4").workingDirectory("C:\\Users\\msi-pc\\Desktop").endTime("00:1:12").startTime("00:00:10").duration("15")
//				.videoResolution("480x244").isStreamCopy(false)/*.format("mp4")*/.build().buildCommandAndExecute();
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
//		FFmpegUtil.changeResolution("C:\\Users\\msi-pc\\Desktop\\shortsolo.mp4",
//				"C:\\Users\\msi-pc\\Desktop\\shortsolochange.mp4", VideoResolution.P480);

//		 System.out.println(
//				FFprobeUtil.extractStreamsByType("C:\\Users\\msi-pc\\Desktop\\shortbunny.mp4", StreamType.VIDEO));
//		System.out.println(
//				FFprobeUtil.extractMetadata("C:\\Users\\msi-pc\\Desktop\\shortbunny.mp4"));
//		FFmpegUtil.reduceFileSize("", "", 0);
//
//		FFmpegEnhanced.builder().input("C:\\Users\\msi-pc\\Desktop\\solo_leveling_video.mp4")
//				.outputs(List.of("C:\\Users\\msi-pc\\Desktop\\out.mp4"))
//				.inputOptions(InputOptions.builder().duration("120").build())
//				.globalOptions(GlobalOptions.builder().isOverwriteOutput(true).build())
//				.outputOptions(List.of(OutputOptions.builder().videoResolution("1280x720").volume("13").build())).logFile("C:\\Users\\msi-pc\\Desktop\\log.txt").build()
//				.buildCommandAndExecute();
//		FFmpegEnhancedUtil.reduceFileSize("C:\\Users\\msi-pc\\Desktop\\shortbunny2.mp4",
//				"C:\\Users\\msi-pc\\Desktop\\outcompressed3.mp4", 1 * 1_048_576);

//		FFmpegEnhancedUtil.changeResolution("C:\\Users\\msi-pc\\Desktop\\shortbunny2.mp4",
//				"C:\\Users\\msi-pc\\Desktop\\new res.mp4", VideoResolution.P480);

//		FFmpegEnhanced f = FFmpegEnhanced.builder().input("C:\\Users\\msi-pc\\Desktop\\shortsolo.mp4")
//				.outputs(List.of("C:\\Users\\msi-pc\\Desktop\\o1.mp4", "C:\\Users\\msi-pc\\Desktop\\o2.mp4"))
//				.outputOptions(List.of(OutputOptions.builder().duration("1").startTime("00:00:00").build()))
//				.globalOptions(GlobalOptions.builder().isOverwriteOutput(true).build()).build();
//		f.buildCommandAndExecute();

//		FFmpegEnhancedUtil.splitVideo("C:\\Users\\msi-pc\\Desktop\\big_bunny_video.mp4",
//				List.of("C:\\Users\\msi-pc\\Desktop\\seg1.mp4", "C:\\Users\\msi-pc\\Desktop\\seg2.mp4",
//						"C:\\Users\\msi-pc\\Desktop\\seg3.mp4"),
//				List.of(LocalTime.of(0, 0, 10), LocalTime.of(0, 0, 30), LocalTime.of(0, 0, 10)));
	}
}
