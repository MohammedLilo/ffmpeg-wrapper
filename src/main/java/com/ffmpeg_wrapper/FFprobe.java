package com.ffmpeg_wrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FFprobe {
	private String workingDirectory;

	private String inputFilePath;
	private String outputFilePath;
//	private boolean isHideBanner;
	private boolean isShowFormat;
	private VerbosityLevel verbosityLevel;
	private FFprobeOutputFormat ffprobeOutputFormat;
	private List<StreamMetadata> entriesToShow;
	private StreamType selectedStream;
	private List<String> command;
	private ProcessBuilder processBuilder;

	private String probeResult = "";
	private String probeError = "";

	public FFprobe(FFprobeBuilder builder) {
		this.workingDirectory = builder.workingDirectory;
		this.inputFilePath = builder.inputFilePath;
		this.outputFilePath = builder.outputFilePath;
//		this.isHideBanner = builder.isHideBanner;
		this.verbosityLevel = builder.verbosityLevel;
		this.ffprobeOutputFormat = builder.ffprobeOutputFormat;
		this.entriesToShow = builder.entriesToShow;
		this.selectedStream = builder.selectedStream;
		this.processBuilder = builder.processBuilder;
		this.command = builder.command;
		this.isShowFormat = builder.isShowFormat;
	}

	public static FFprobeBuilder builder() {
		return new FFprobeBuilder();
	}

	public void probe() {
		Process process;
		BufferedReader resultReader = null;
		BufferedReader errorReader = null;
		try {
			StringBuilder probeResult = new StringBuilder();
			StringBuilder probeError = new StringBuilder();
			String line;

			process = processBuilder.start();

			if (this.outputFilePath == null || this.outputFilePath.isBlank()) {
				resultReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((line = resultReader.readLine()) != null) {
					probeResult.append(line).append("\n");
				}
				errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				while ((line = errorReader.readLine()) != null) {
					probeError.append(line).append("\n");
				}
			}
			if (process.waitFor() == 0)
				this.probeResult = probeResult.toString();
			else
				this.probeError = probeError.toString();

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			if (resultReader != null)
				try {
					resultReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (errorReader != null)
				try {
					errorReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
	}

	public static class FFprobeBuilder {
		private String workingDirectory;
		private String inputFilePath;
		private String outputFilePath;
//		private boolean isHideBanner;
		private boolean isShowFormat;
		private VerbosityLevel verbosityLevel = VerbosityLevel.ERROR;
		private FFprobeOutputFormat ffprobeOutputFormat = FFprobeOutputFormat.NOPRINT;
		private List<StreamMetadata> entriesToShow = new LinkedList<>();
		private StreamType selectedStream = StreamType.ALL;
		private List<String> command = new LinkedList<>();
		private ProcessBuilder processBuilder;

		private FFprobeBuilder() {
			processBuilder = new ProcessBuilder(command);
			command.add("ffprobe");
		}

		public FFprobeBuilder workingDirectory(String workingDirectory) {
			if (workingDirectory != null) {
				this.processBuilder.directory(new File(workingDirectory));
			}
			this.workingDirectory = workingDirectory;
			return this;
		}

		public FFprobeBuilder inputFilePath(String inputFilePath) {
			command.add("-i");
			command.add(inputFilePath);
			this.inputFilePath = inputFilePath;
			return this;
		}

		public FFprobeBuilder outputFilePath(String outputFilePath) {
			this.outputFilePath = outputFilePath;

			if (outputFilePath == null || outputFilePath.isBlank())
				return this;

			command.add("-o");
			command.add(outputFilePath);
			return this;
		}

//		public FFprobeBuilder isHideBanner(boolean isHideBanner) {
//			if (isHideBanner) {
//				command.add("-hide_banner");
//			}
//			this.isHideBanner = isHideBanner;
//			return this;
//		}
		public FFprobeBuilder isShowFormat(boolean isShowFormat) {
			if (isShowFormat) {
				command.add("-show_format");
			}
			this.isShowFormat = isShowFormat;
			return this;
		}

		public FFprobeBuilder verbosityLevel(VerbosityLevel verbosityLevel) {
			command.add("-v");
			command.add(verbosityLevel.toString());
			this.verbosityLevel = verbosityLevel;
			return this;
		}

		public FFprobeBuilder ffprobeOutputFormat(FFprobeOutputFormat outputFormat) {
			command.add("-of");
			command.add(outputFormat.toString());
			this.ffprobeOutputFormat = outputFormat;
			return this;
		}

		public FFprobeBuilder entryToShow(StreamMetadata entryToShow) {
			entriesToShow.add(entryToShow);
			return this;
		}

		public FFprobeBuilder entriesToShow(List<StreamMetadata> entriesToShow) {
			this.entriesToShow = entriesToShow;
			return this;
		}

		public FFprobeBuilder selectedStream(StreamType selectedStream) {
			this.selectedStream = selectedStream;
			return this;
		}

		public FFprobe build() {

			if (!entriesToShow.isEmpty()) {
				command.add("-select_streams");
				command.add(this.selectedStream.toString());
				command.add("-show_entries");
				StringBuilder sb = new StringBuilder("stream=");
				entriesToShow.forEach(entry -> {
					sb.append(entry.toString() + ",");
				});
				sb.setLength(sb.length() - 1);

				command.add(sb.toString());
			}
			command.add("-of");
			command.add(ffprobeOutputFormat.toString());

			return new FFprobe(this);
		}
	}
}
