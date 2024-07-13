package com.ffmpeg_wrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FFmpeg {
	private String workingDirectory;
	private String input;
	private String output;
	private boolean isOverwriteOutput;
	private boolean isHideBanner;
	private boolean isStreamCopy;
	private String logFile;
	private String audioSampleRate;
	private String audioBitrate;
	private String audioCodec;
	private String duration;
	private String volume;
	private String format;
	private String videoBitrate;
	private String videoResolution;
	private String videoFrameRate;
	private String videoCodec;
	private String videoAspectRatio;
	private String startTime;
	private String endTime;

	private List<String> globalOptions;
	private Map<String, String> inputOptions;
	private Map<String, String> outputOptions;

	private FFmpeg(FFmpegBuilder builder) {
		this.workingDirectory = builder.workingDirectory;
		this.logFile = builder.logFile;

		this.input = builder.input;
		this.output = builder.output;

		this.isOverwriteOutput = builder.isOverwriteOutput;
		this.isHideBanner = builder.isHideBanner;
		this.isStreamCopy = builder.isStreamCopy;
		this.audioSampleRate = builder.audioSampleRate;
		this.audioBitrate = builder.audioBitrate;
		this.audioCodec = builder.audioCodec;
		this.format = builder.format;
		this.videoBitrate = builder.videoBitrate;
		this.videoResolution = builder.videoResolution;
		this.videoFrameRate = builder.videoFrameRate;
		this.videoCodec = builder.videoCodec;
		this.videoAspectRatio = builder.videoAspectRatio;

		this.duration = builder.duration;
		this.volume = builder.volume;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;

		this.inputOptions = builder.inputOptions;
		this.outputOptions = builder.outputOptions;
		this.globalOptions = builder.globalOptions;
	}

	public static FFmpegBuilder builder() {
		return new FFmpegBuilder();
	}

	public void buildCommandAndExecute() {
		List<String> command = new ArrayList<>();
		command.add("ffmpeg");
		command.addAll(globalOptions);
		command.add("-i");
		command.add(this.input);

		inputOptions.forEach((key, val) -> {
			command.add(key);
			command.add(val);
		});

		outputOptions.forEach((key, val) -> {
			command.add(key);
			command.add(val);
		});
		command.add(this.output);
		ProcessBuilder pb = new ProcessBuilder(command);
		if (this.workingDirectory != null) {
			pb.directory(new File(this.workingDirectory));
		}

		if (logFile != null) {
			File logFile = new File(workingDirectory, this.logFile);
			pb.redirectError(logFile);
		}
		try {
			Process process = pb.start();
			int exitCode = process.waitFor();

			if (exitCode == 0) {
				System.out.println("FFmpeg process completed successfully. \nYou can check the log of this command at "
						+ logFile.toString());
				System.out.println(pb.command());
			} else {
				System.err.println("FFmpeg process failed with exit code: " + exitCode
						+ "\nYou can check the log of this command at " + logFile.toString());
				System.out.println(pb.command());
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

//	public void buildCommandAndRun() throws InterruptedException, IOException {
//
//		String overwriteOutput = "";
//		if (this.isOverwriteOutput) {
//			overwriteOutput = "-y";
//		}
//		List<String> commands = new CopyOnWriteArrayList<>(
//				List.of("ffmpeg", overwriteOutput, "-hide_banner"/* , "-i", input */));
//		if (input == null) {
//			new RuntimeException("input cannot be null");
//		}
//		if (startTime != null) {
//			commands.add("-ss");
//			commands.add(startTime);
//		}
//		if (endTime != null) {
//			commands.add("-to");
//			commands.add(endTime);
//		}
//		if (audioSampleRate != null) {
//			commands.add("-ar");
//			commands.add(audioSampleRate);
//		}
//		if (audioBitrate != null) {
//			commands.add("-b:a");
//			commands.add(audioBitrate);
//		}
//		if (audioCodec != null) {
//			commands.add("-c:a");
//			commands.add(audioCodec);
//		}
//		if (duration != null) {
//			commands.add("-t");
//			commands.add(duration);
//		}
//		if (format != null) {
//			commands.add("-f");
//			commands.add(format);
//		}
//		if (volume != null) {
//			commands.add("-af");
//			commands.add("volume=" + volume);
//		}
//		if (videoBitrate != null) {
//			commands.add("-b:v");
//			commands.add(videoBitrate);
//		}
//		if (videoResolution != null) {
//			commands.add("-s");
//			commands.add(videoResolution);
//		}
//		if (videoFrameRate != null) {
//			commands.add("-r");
//			commands.add(videoFrameRate);
//		}
//		if (videoCodec != null) {
//			commands.add("-c:v");
//			commands.add(videoCodec);
//		}
//		if (videoAspectRatio != null) {
//			commands.add("-aspect");
//			commands.add(videoAspectRatio);
//		}
//
//		commands.add(output);
//		System.out.println(commands);
//		ProcessBuilder pb = new ProcessBuilder(commands);
//		if (workingDirectory != null) {
//			pb.directory(new File(workingDirectory));
//		}
//
//		if (logFile != null) {
//			File logFile = new File(workingDirectory, this.logFile);
//			pb.redirectError(logFile);
//		}
//		Process process = pb.start();
//		int exitCode = process.waitFor();
//
//		if (exitCode == 0) {
//			System.out.println("FFmpeg process completed successfully. \nYou can check the log of this command at "
//					+ logFile.toString());
//		} else {
//			System.err.println("FFmpeg process failed with exit code: " + exitCode
//					+ "\nYou can check the log of this command at " + logFile.toString());
//		}
//	}

	public static class FFmpegBuilder {
		private String workingDirectory;
		private String input;
		private String output;

		private boolean isOverwriteOutput;
		private boolean isHideBanner;
		private boolean isStreamCopy;

		private String logFile;
		private String audioSampleRate;
		private String audioBitrate;
		private String audioCodec;
		private String duration;
		private String format;
		private String volume;
		private String videoBitrate;
		private String videoResolution;
		private String videoFrameRate;
		private String videoCodec;
		private String videoAspectRatio;
		private String startTime;
		private String endTime;

		private List<String> globalOptions = new ArrayList<>();
		private Map<String, String> inputOptions = new HashMap<>();
		private Map<String, String> outputOptions = new HashMap<>();

		public FFmpegBuilder workingDirectory(String workingDirectory) {
			this.workingDirectory = workingDirectory;
			return this;
		}

		public FFmpegBuilder isOverwriteOutput(boolean isOverwriteOutput) {
			if (isOverwriteOutput) {
				globalOptions.add("-y");
			}
			this.isOverwriteOutput = isOverwriteOutput;
			return this;
		}

		public FFmpegBuilder isStreamCopy(boolean isStreamCopy) {
			if (isStreamCopy) {
				outputOptions.put("-c", "copy");
			}
			this.isStreamCopy = isStreamCopy;
			return this;
		}

		public FFmpegBuilder isHideBanner(boolean isHideBanner) {
			if (isHideBanner) {
				globalOptions.add("-hide_banner");
			}
			this.isHideBanner = isHideBanner;
			return this;
		}

		public FFmpegBuilder input(String input) {
			this.input = input;
			return this;
		}

		public FFmpegBuilder output(String output) {
			this.output = output;
			return this;
		}

		public FFmpegBuilder logFile(String logFile) {
			this.logFile = logFile;
			return this;
		}

		public FFmpegBuilder audioSampleRate(String audioSampleRate) {
			outputOptions.put("-ar", audioSampleRate);
			this.audioSampleRate = audioSampleRate;
			return this;
		}

		public FFmpegBuilder audioBitRate(String audioBitRate) {
			outputOptions.put("-b:a", audioBitRate);
			this.audioBitrate = audioBitRate;
			return this;
		}

		public FFmpegBuilder audioCodec(String audioCodec) {
			outputOptions.put("-c:a", audioCodec);
			this.audioCodec = audioCodec;
			return this;
		}

		public FFmpegBuilder duration(String duration) {
			inputOptions.put("-t", duration);
			this.duration = duration;
			return this;
		}

		public FFmpegBuilder format(String format) {
			outputOptions.put("-f", format);
			this.format = format;
			return this;
		}

		public FFmpegBuilder volume(String volume) {
			outputOptions.put("-af", "volume=" + volume);
			this.volume = volume;
			return this;
		}

		public FFmpegBuilder videoBitrate(String videoBitrate) {
			outputOptions.put("-b:v", videoBitrate);
			this.videoBitrate = videoBitrate;
			return this;
		}

		public FFmpegBuilder videoResolution(String videoResolution) {
			outputOptions.put("-s", videoResolution);
			this.videoResolution = videoResolution;
			return this;
		}

		public FFmpegBuilder videoFrameRate(String videoFrameRate) {
			outputOptions.put("-r", videoFrameRate);
			this.videoFrameRate = videoFrameRate;
			return this;
		}

		public FFmpegBuilder videoCodec(String videoCodec) {
			outputOptions.put("-c:v", videoCodec);
			this.videoCodec = videoCodec;
			return this;
		}

		public FFmpegBuilder videoAspectRatio(String videoAspectRatio) {
			outputOptions.put("-aspect", videoAspectRatio);
			this.videoAspectRatio = videoAspectRatio;
			return this;
		}

		public FFmpegBuilder startTime(String startTime) {
			inputOptions.put("-ss", startTime);
			this.startTime = startTime;
			return this;
		}

		public FFmpegBuilder endTime(String endTime) {
			inputOptions.put("-to", endTime);
			this.endTime = endTime;
			return this;
		}

		public FFmpeg build() {
			return new FFmpeg(this);
		}

	}

}
