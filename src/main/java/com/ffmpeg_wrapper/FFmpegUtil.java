package com.ffmpeg_wrapper;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ffmpeg_wrapper.enums.VideoResolution;
import com.ffmpeg_wrapper.exceptions.FFmpegExecutionException;
import com.ffmpeg_wrapper.mapping.FFprobeOutput;
import com.ffmpeg_wrapper.mapping.Stream;

public class FFmpegUtil {

	/**
	 * Reduces the file size of a video or audio file to a target size (in bytes).
	 *
	 * @param inputFilePath  Input file path.
	 * @param outputFilePath Output file path.
	 * @param targetSize     Target file size in bytes (using binary prefix).
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 */
	public static void reduceFileSize(String inputFilePath, String outputFilePath, long targetSize) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		long size = output.getFormat().getSize();
		List<Long> newBitrates = new ArrayList<>();
		double ratio = (double) targetSize / size;
		long newBitrate = 0;
		
		for (int i = 0; i < streams.size(); i++) {
			newBitrate = (long) (Math.ceil(streams.get(i).getBitRate() * ratio));
			newBitrates.add(newBitrate);
		}
		List<StreamMapping> streamMappings = mapStreamsAndBitrates(streams, newBitrates);
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();
		OutputOptions outputOptions = OutputOptions.builder().streamsMappings(streamMappings)
															.build();
		FFmpeg fFmpegEnhanced = FFmpeg.builder().input(inputFilePath)
																.outputs(List.of(outputFilePath))	
																.globalOptions(globalOptions)
																.outputOptions(List.of(outputOptions))
																.build();
			
			fFmpegEnhanced.buildCommandAndExecute();
	}

	private static List<StreamMapping> mapStreamsAndBitrates(List<Stream> streams, List<Long> newBitrates) {
		String codecType = "";
		Map<String, Integer> map = new HashMap<>();
		List<StreamMapping> streamMappings = new ArrayList<>();
		String codecName;
		int streamIndex;
		long bitrate;
		Stream stream;
		for (int i = 0; i < streams.size(); i++) {
			stream = streams.get(i);
			codecType = stream.getCodecType().substring(0, 1);
			codecName = stream.getCodecName();
			streamIndex = map.getOrDefault(codecType, 0);
			bitrate = newBitrates.get(i);

			streamMappings
					.add(StreamMapping.builder(streamIndex, codecType).codecName(codecName)
																		.bitrate(bitrate)
																		.build());
			map.put(codecType, ++streamIndex);
		}

		return streamMappings;
	}

	private static List<StreamMapping> mapStreams(List<Stream> streams) {

		String codecType;
		String codecName;
		int streamIndex;
		Map<String, Integer> map = new HashMap<>();
		List<StreamMapping> streamMappings = new ArrayList<>();
		Stream stream;

		for (int i = 0; i < streams.size(); i++) {
			stream = streams.get(i);
			codecType = stream.getCodecType().substring(0, 1);
			codecName = stream.getCodecName();
			streamIndex = map.getOrDefault(codecType, 0);
			streamMappings.add(StreamMapping.builder(streamIndex, codecType).codecName(codecName)
																			.build());
			map.put(codecType, ++streamIndex);
		}
		return streamMappings;
	}

	private static List<StreamMapping> mapStreamsOfType(List<Stream> streams, StreamType streamType) {

		String codecType;
		String codecName;
		int streamIndex;
		Map<String, Integer> map = new HashMap<>();
		List<StreamMapping> streamMappings = new ArrayList<>();
		Stream stream;

		for (int i = 0; i < streams.size(); i++) {
			stream = streams.get(i);
			codecType = stream.getCodecType().substring(0, 1);

			if (!codecType.equals(streamType.getType()))
				continue;

			codecName = stream.getCodecName();
			streamIndex = map.getOrDefault(codecType, 0);
			streamMappings.add(StreamMapping.builder(streamIndex, codecType).codecName(codecName)
																			.build());
			map.put(codecType, ++streamIndex);
		}
		return streamMappings;
	}

//	private static List<StreamMapping> mapStreamsAndCopy(List<Stream> streams) {
//
//		String codecType;
//		String codecName;
//		int streamIndex;
//		Map<String, Integer> map = new HashMap<>();
//		List<StreamMapping> streamMappings = new ArrayList<>();
//		Stream stream;
//
//		for (int i = 0; i < streams.size(); i++) {
//			stream = streams.get(i);
//			codecType = stream.getCodecType().substring(0, 1);
//			codecName = stream.getCodecName();
//			streamIndex = map.getOrDefault(codecType, 0);
//			streamMappings
//					.add(StreamMapping.builder(streamIndex, codecType).isStreamCopy(true)
//																		.codecName(codecName)
//																		.build());
//			map.put(codecType, ++streamIndex);
//		}
//		return streamMappings;
//	}

	/**
	 * Changes the video resolution to the specified width and height. It will
	 * overwrite the output file if it already exists.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param width          Target width in pixels.
	 * @param height         Target height in pixels.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 */
	public static void changeResolution(String inputFilePath, String outputFilePath, int width, int height) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		OutputOptions outputOptions = OutputOptions.builder().videoResolution(String.format("%dx%d", width, height))
															.streamsMappings(streamMappings)
															.build();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();
		FFmpeg.builder().input(inputFilePath)
								.outputs(outputFilePath)
								.outputOptions(outputOptions)
								.globalOptions(globalOptions)
								.build()
								.buildCommandAndExecute();

	}

	public static void changeResolution(String inputFilePath, String outputFilePath, VideoResolution newResolution) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		OutputOptions outputOptions = OutputOptions.builder()
				.videoResolution(String.format("%dx%d", newResolution.getWidth(), newResolution.getHeight()))
				.streamsMappings(streamMappings).build();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();
		FFmpeg.builder().input(inputFilePath)
								.outputs(outputFilePath)
								.outputOptions(outputOptions)
								.globalOptions(globalOptions)
								.build()
								.buildCommandAndExecute();

	}

	/**
	 * Converts a video or an audio to a different format.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param format         Target format (e.g., mp4, avi).
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 */
	public static void convertFormat(String inputFilePath, String outputFilePath, String format) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		OutputOptions outputOptions = OutputOptions.builder().streamsMappings(streamMappings)
															.format(format)
															.build();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();

		FFmpeg.builder().input(inputFilePath)
								.outputs(outputFilePath)
								.outputOptions(outputOptions)
								.globalOptions(globalOptions)
								.build()
								.buildCommandAndExecute();
	}

	/**
	 * Converts a video or an audio to a different format. the output file's format
	 * will be inferred from the output file name.
	 * 
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param format         Target format (e.g., mp4, avi).
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 */
	public static void convertFormat(String inputFilePath, String outputFilePath) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		OutputOptions outputOptions = OutputOptions.builder().streamsMappings(streamMappings)
															.build();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();

		FFmpeg.builder().input(inputFilePath)
								.outputs(outputFilePath)
								.outputOptions(outputOptions)
								.globalOptions(globalOptions)
								.build()
								.buildCommandAndExecute();
	}

	/**
	 * Cuts a specific part of the playable media (video or audio file) from the
	 * input file and saves it to the output file.
	 * 
	 * @param inputFilePath  The path to the input file.
	 * @param outputFilePath The path to save the output file.
	 * @param startTime      The start time from which to begin cutting the file.
	 *                       The format should be "hh:mm:ss" or "ss" for seconds.
	 * @param duration       The duration of the file segment to cut. The format
	 *                       should be "hh:mm:ss" or "ss" for seconds.
	 * 
	 *                       Example usage: cutPlayableMedia("input.mp4",
	 *                       "output.mp4", "00:00:00", "00:00:10"); This will cut
	 *                       the first 10 seconds from the input.mp4 and save it as
	 *                       output.mp4.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 */
	public static void cutPlayableMedia(String inputFilePath, String outputFilePath, String startTime,
			String duration) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();

		OutputOptions outputOptions = OutputOptions.builder().startTime(startTime)
															.duration(duration)
															.streamsMappings(streamMappings)
															.build();

		FFmpeg.builder().globalOptions(globalOptions)
								.input(inputFilePath)
								.outputOptions(outputOptions)
								.outputs(outputFilePath)
								.build()
								.buildCommandAndExecute();
	}

	/**
	 * Extracts all streams of a specific type (i.e. audio or video or subtitle,
	 * etc.)from an input file and saves it to the output file. Output file's format
	 * will be inferred from the output file name.
	 *
	 * @param inputFilePath  Input file path.
	 * @param outputFilePath Output file path.
	 * @param streamType     Type of the streams to extract.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 * 
	 */
	public static void extractStreamsOfType(String inputFilePath, String outputFilePath, StreamType streamType) throws InterruptedException, IOException, FFmpegExecutionException {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreamsOfType(streams, streamType);

		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();

		OutputOptions outputOptions = OutputOptions.builder().streamsMappings(streamMappings)
															.build();

		FFmpeg.builder().globalOptions(globalOptions)
								.input(inputFilePath)
								.outputOptions(outputOptions)
								.outputs(outputFilePath)
								.build()
								.buildCommandAndExecute();
	}

	/**
	 * Splits a video to multiple video files of the same duration.
	 *
	 * @param inputFiles     List of input video files to concatenate.
	 * @param outputFilePath Output video file path.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws FFmpegExecutionException 
	 * 
	 */
	public static void splitVideo(String inputFilePath, List<String> outputFilesPaths, List<LocalTime> duration) throws InterruptedException, IOException, FFmpegExecutionException {

		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);
		List<OutputOptions> outputOptionsList = new ArrayList<>();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true)
															.build();
		LocalTime startTime = LocalTime.of(0, 0, 0);
		LocalTime durationTime = LocalTime.of(0, 0, 0);
		for (int i = 0; i < outputFilesPaths.size(); i++) {
			startTime = startTime.plusSeconds(durationTime.toSecondOfDay());
			durationTime = duration.get(i);

			outputOptionsList.add(OutputOptions.builder().duration(durationTime.toString())
															.startTime(startTime.toString())
															.streamsMappings(streamMappings)
															.build()
								);
		}

		FFmpeg.builder().globalOptions(globalOptions)
								.input(inputFilePath)
								.outputOptions(outputOptionsList)
								.outputs(outputFilesPaths)
								.build()
								.buildCommandAndExecute();

	}

	/**
	 * Generates a thumbnail image from a video at the specified time.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output thumbnail image file path.
	 * @param time           Time in seconds where the thumbnail should be taken
	 *                       from.
	 * @param width          Width of the thumbnail image.
	 * @param height         Height of the thumbnail image.
	 */
	public static void generateThumbnail(String inputFilePath, String outputFilePath, int time, int width, int height) {

	}

}
