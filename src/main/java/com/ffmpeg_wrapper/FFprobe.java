package com.ffmpeg_wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		try {
			StringBuilder probeResult = new StringBuilder();
			StringBuilder probeError = new StringBuilder();
			String line;

			process = processBuilder.start();

			if (this.outputFilePath == null) {
				try (BufferedReader resultReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
					while ((line = resultReader.readLine()) != null) {
						probeResult.append(line).append("\n");
					}
				}
				try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
					while ((line = errorReader.readLine()) != null) {
						probeError.append(line).append("\n");
					}
				}
			}
			if (process.waitFor() == 0)
				this.probeResult = probeResult.toString();
			else
				this.probeError = probeError.toString();

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
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

		public FFprobeBuilder workingDirectory(String workingDirectory) throws NoSuchFileException {
			if (workingDirectory == null || workingDirectory.isBlank()) {
				throw new IllegalArgumentException(
						"Working directory cannot be null or blank, but you used " + workingDirectory);
			}
			if (!Files.exists(Paths.get(workingDirectory)) || !Files.isDirectory(Paths.get(workingDirectory))) {
				throw new NoSuchFileException("The specified working directory does not exist: " + workingDirectory);
			}
			this.workingDirectory = workingDirectory;
			return this;
		}

		public FFprobeBuilder inputFilePath(String inputFilePath) throws NoSuchFileException {
			validateFileName(inputFilePath, true);
			command.add("-i");
			command.add(inputFilePath);
			this.inputFilePath = inputFilePath;
			return this;
		}

		public FFprobeBuilder outputFilePath(String outputFilePath) throws NoSuchFileException {
			validateFileName(outputFilePath, false);
			command.add("-o");
			command.add(outputFilePath);
			this.outputFilePath = outputFilePath;
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

		private void validateFileName(String fileName, boolean isMustExist) throws NoSuchFileException {
			if (fileName == null || fileName.isBlank()) {
				throw new IllegalArgumentException(String.format("File name cannot be null or blank."));
			}
			Path path = (this.workingDirectory != null ? Paths.get(this.workingDirectory, fileName)
					: Paths.get(fileName));
			if (isMustExist && !Files.exists(path)) {
				throw new NoSuchFileException("The specified file does not exist: " + path);
			}

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
