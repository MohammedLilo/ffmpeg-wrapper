package com.ffmpeg_wrapper;

import java.util.ArrayList;
import java.util.List;

import com.ffmpeg_wrapper.enums.VideoResolution;
import com.ffmpeg_wrapper.mapping.FFprobeOutput;
import com.ffmpeg_wrapper.mapping.Stream;

public class FFmpegUtil {
	/**
	 * Reduces the file size of a video or audio file to a target size (in bytes).
	 *
	 * @param inputFilePath  Input file path.
	 * @param outputFilePath Output file path.
	 * @param targetSize     Target file size in bytes.
	 */
	static void reduceFileSize(String inputFilePath, String outputFilePath, long targetSize) {
		FFprobeOutput output = FFprobeUtil.extractMetadata(inputFilePath);
		List<Stream> streams = output.getStreams();
		long totalBitRate = output.getFormat().getTotalBitRate();
		List<Double> oldBitrates = new ArrayList<>();
		double ratio = (targetSize / totalBitRate) * 8;
		for (int i = 0; i < streams.size(); i++) {
			oldBitrates.add((double) streams.get(i).getBitRate());
		}
		for (int i = 0; i < streams.size(); i++) {
			oldBitrates.set(i, oldBitrates.get(i) * ratio);
		}

	}

	static void changeBitRates(String inputFilePath, String outputFilePath, List<Double> newBitRates) {

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
		FFmpeg.builder().videoResolution(width + "x" + height).input(inputFilePath).isOverwriteOutput(true)
				.output(outputFilePath).build().buildCommandAndExecute();
	}

	static void changeResolution(String inputFilePath, String outputFilePath, VideoResolution newResolution) {
		FFmpeg.builder().videoResolution(newResolution.getWidth() + "x" + newResolution.getHeight())
				.input(inputFilePath).isOverwriteOutput(true).output(outputFilePath).build().buildCommandAndExecute();
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
		FFmpeg.builder().startTime(startTime).duration(duration).input(inputFilePath).isOverwriteOutput(true)
				.output(outputFilePath).build().buildCommandAndExecute();
	}

	/**
	 * Converts a video to a different format.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output video file path.
	 * @param format         Target format (e.g., mp4, avi).
	 */
	static void convertFormat(String inputFilePath, String outputFilePath, String format) {
		FFmpeg.builder().format(format).input(inputFilePath).isOverwriteOutput(true).output(outputFilePath).build()
				.buildCommandAndExecute();
	}

	/**
	 * Extracts audio from an input file and stores it as a <code>format</code> file at
	 * <code>outputFilePath</code>.
	 *
	 * @param inputFilePath  Input video file path.
	 * @param outputFilePath Output audio file path.
	 * @param format         output file format (e.g. mp3, aac, etc).
	 */
	static void extractAudio(String inputFilePath, String outputFilePath, String format) {
		FFmpeg.builder().input(inputFilePath).isOverwriteOutput(true).output(outputFilePath).isStreamCopy(true)
				.format(format).build().buildCommandAndExecute();
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
		FFmpeg.builder().input(inputFilePath).isOverwriteOutput(true).output(outputFilePath).isStreamCopy(true).build()
				.buildCommandAndExecute();

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
