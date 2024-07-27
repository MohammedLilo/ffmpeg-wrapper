package com.ffmpeg_wrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ffmpeg_wrapper.exceptions.FFmpegExecutionException;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FFmpeg {

	private String workingDirectory;
	private String logFile;

	private String input;
	private List<String> outputs;
	private InputOptions inputOptions;
	private List<OutputOptions> outputOptions;
	private GlobalOptions globalOptions;

	private List<String> command = new LinkedList<>();

	private FFmpeg(FFmpegBuilder builder) {
		this.workingDirectory = builder.workingDirectory;
		this.logFile = builder.logFile;
		this.input = builder.input;
		this.outputs = builder.outputs;
		this.inputOptions = builder.inputOptions;
		this.outputOptions = builder.outputOptions;
		this.globalOptions = builder.globalOptions;
	}

	public static FFmpegBuilder builder() {
		return new FFmpegBuilder();
	}

	public void buildCommandAndExecute() throws InterruptedException, IOException, FFmpegExecutionException {
		command.add("ffmpeg");
		if (globalOptions != null) {
			globalOptions.getCommand().forEach(option -> {
				command.add(option);
			});
		}
		if (inputOptions != null) {
			inputOptions.getCommand().forEach((k, v) -> {
				command.add(k);
				command.add(v);
			});
		}
		command.add("-i");
		command.add(input);
		for (int i = 0; i < outputs.size(); i++) {
			while (i < outputOptions.size()) {
				command.addAll(outputOptions.get(i).getCommand());
				break;
			}
			command.add(outputs.get(i));

		}
		ProcessBuilder pb = new ProcessBuilder(command);
		if (workingDirectory != null) {
			pb.directory(new File(workingDirectory));
		}
		String msg;
		if (logFile != null) {
			pb.redirectError(new File(workingDirectory, logFile));
			msg = " you can check the log of this command at" + new File(workingDirectory, logFile).getAbsolutePath();
		} else {
			msg = "";
		}
		Process process = pb.start();
		StringBuilder errorMsg = new StringBuilder();
//must read the process input stream or discarding it before waiting for the process to finish, as it will halt indefinitely otherwise.
		if (logFile == null) {
			try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
				String line;
				while ((line = errorReader.readLine()) != null) {
					errorMsg.append(line).append("\n");
				}
			}
		}
		int exitCode = process.waitFor();

		if (exitCode == 0) {
			System.out.println("FFmpeg process completed successfully" + msg);
			System.out.println(pb.command().toString().replaceAll("[,\\[\\]]", ""));
		} else {
			throw new FFmpegExecutionException(String.format(
					"FFmpeg process failed with exit code: %d \nYour command: %s\nBelow is the complete ffmpeg log for this command\n%s",
					exitCode, pb.command().toString().replaceAll("[,\\[\\]]", ""), errorMsg.toString()));
		}
	}

	public static class FFmpegBuilder {

		private String workingDirectory;
		private String logFile;

		private String input;
		private List<String> outputs = new ArrayList<>();
		private InputOptions inputOptions;
		private List<OutputOptions> outputOptions = new ArrayList<>();
		private GlobalOptions globalOptions;

		public FFmpegBuilder workingDirectory(String workingDirectory) throws NoSuchFileException {
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

		public FFmpegBuilder logFile(String logFile) throws NoSuchFileException {
			validateFileName(logFile, false);
			this.logFile = logFile;
			return this;
		}

		public FFmpegBuilder input(String input) throws NoSuchFileException {
			validateFileName(input, true);
			this.input = input;
			return this;
		}

		public FFmpegBuilder outputs(List<String> outputs) throws NoSuchFileException {
			for (int i = 0; i < outputs.size(); i++) {
				validateFileName(outputs.get(i), false);
			}
			this.outputs = outputs;
			return this;
		}

		public FFmpegBuilder outputs(String... outputs) throws NoSuchFileException {
			for (int i = 0; i < outputs.length; i++) {
				validateFileName(outputs[i], false);
			}
			this.outputs = List.of(outputs);
			return this;
		}

		public FFmpegBuilder inputOptions(InputOptions inputOptions) {
			this.inputOptions = inputOptions;
			return this;
		}

		public FFmpegBuilder outputOptions(List<OutputOptions> outputOptions) {
			this.outputOptions = outputOptions;
			return this;
		}

		public FFmpegBuilder outputOptions(OutputOptions... outputOptions) {
			this.outputOptions = List.of(outputOptions);
			return this;
		}

		public FFmpegBuilder globalOptions(GlobalOptions globalOptions) {
			this.globalOptions = globalOptions;
			return this;
		}

		private void validateFileName(String fileName, boolean isMustExist) throws NoSuchFileException {
			if (fileName == null || fileName.isBlank()) {
				throw new IllegalArgumentException(String.format("File name cannot be null or blank."));
			}
			Path path = (this.workingDirectory != null ? Paths.get(this.workingDirectory, fileName)
					: Paths.get(fileName));
			if (isMustExist && (!Files.exists(path) || !Files.isRegularFile(path))) {
				throw new NoSuchFileException("The specified file does not exist: " + path);
			}

		}

		public FFmpeg build() {
			return new FFmpeg(this);
		}
	}
}
