package com.ffmpeg_wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ffmpeg_wrapper.enums.VideoResolution;
import com.ffmpeg_wrapper.mapping.FFprobeOutput;
import com.ffmpeg_wrapper.mapping.Stream;

public class FFmpegEnhancedUtil {

	/**
	 * Reduces the file size of a video or audio file to a target size (in bytes).
	 *
	 * @param inputFilePath  Input file path.
	 * @param outputFilePath Output file path.
	 * @param targetSize     Target file size in bytes (using binary prefix).
	 */
	public static void reduceFileSize(String inputFilePath, String outputFilePath, long targetSize) {
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

		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true).build();
		OutputOptions outputOptions = OutputOptions.builder().streamsMappings(streamMappings).build();
		FFmpegEnhanced fFmpegEnhanced = FFmpegEnhanced.builder().input(inputFilePath).outputs(List.of(outputFilePath))
				.globalOptions(globalOptions).outputOptions(List.of(outputOptions)).build();
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
					.add(StreamMapping.builder(streamIndex, codecType).codecName(codecName).bitrate(bitrate).build());
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
			streamMappings.add(StreamMapping.builder(streamIndex, codecType).codecName(codecName).build());
			map.put(codecType, ++streamIndex);
		}
		return streamMappings;
	}

	/**
	 * Changes the video resolution to the specified width and height. It will
	 * overwrite the output file if it already exists.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param width          Target width in pixels.
	 * @param height         Target height in pixels.
	 */
	static void changeResolution(String inputFilePath, String outputFilePath, int width, int height) {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		OutputOptions outputOptions = OutputOptions.builder().videoResolution(String.format("%dx%d", width, height))
				.streamsMappings(streamMappings).build();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true).build();
		FFmpegEnhanced.builder().input(inputFilePath).outputs(outputFilePath).outputOptions(outputOptions)
				.globalOptions(globalOptions).build().buildCommandAndExecute();

	}

	static void changeResolution(String inputFilePath, String outputFilePath, VideoResolution newResolution) {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		List<StreamMapping> streamMappings = mapStreams(streams);

		OutputOptions outputOptions = OutputOptions.builder()
				.videoResolution(String.format("%dx%d", newResolution.getWidth(), newResolution.getHeight()))
				.streamsMappings(streamMappings).build();
		GlobalOptions globalOptions = GlobalOptions.builder().isOverwriteOutput(true).build();
		FFmpegEnhanced.builder().input(inputFilePath).outputs(outputFilePath).outputOptions(outputOptions)
				.globalOptions(globalOptions).build().buildCommandAndExecute();

	}

	/**
	 * Extracts a specific time duration from a video.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param startTime      Start time of the segment to extract (in seconds).
	 * @param duration       Duration of the segment to extract (in seconds).
	 */
	static void extractVideoSegment(String inputFilePath, String outputFilePath, String startTime, String duration) {
	}

	/**
	 * Converts a video to a different format.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param format         Target format (e.g., mp4, avi).
	 */
	static void convertFormat(String inputFilePath, String outputFilePath, String format) {
	}

	/**
	 * Extracts audio from an input file and stores it as a <code>format</code> file
	 * at <code>outputFilePath</code>.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output audio file path.
	 * @param format         output file format (e.g. mp3, aac, etc).
	 */
	static void extractAudio(String inputFilePath, String outputFilePath, String format) {
	}

	/**
	 * Extracts audio from an input file and stores it at
	 * <code>outputFilePath</code>. Output file's format will be inferred from the
	 * output file name.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output audio file path.
	 */
	static void extractAudio(String inputFilePath, String outputFilePath) {
	}

	/**
	 * Concatenates multiple videos into a single video file.
	 *
	 * @param inputFiles     List of input video files to concatenate.
	 * @param outputFilePath Output video file path.
	 */
	static void concatenateVideos(List<String> inputFiles, String outputFilePath) {
	}

	/**
	 * Applies a video filter to resize, crop, or otherwise modify the video frames.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param filter         Video filter expression (e.g., scale=w=640:h=480).
	 */
	static void applyVideoFilter(String inputFilePath, String outputFilePath, String filter) {
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
	static void generateThumbnail(String inputFilePath, String outputFilePath, int time, int width, int height) {

	}

	/**
	 * Adds watermark to a video at specified position.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param watermarkImage Watermark image file path.
	 * @param xPosition      Horizontal position of the watermark (in pixels from
	 *                       the left).
	 * @param yPosition      Vertical position of the watermark (in pixels from the
	 *                       top).
	 */
	static void addWatermark(String inputFilePath, String outputFilePath, String watermarkImage, int xPosition,
			int yPosition) {
	}

}
