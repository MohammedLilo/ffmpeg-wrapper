package com.ffmpeg_wrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FFmpegEnhanced {

	private String workingDirectory;
	private String logFile;

	private String input;
	private List<String> outputs;
	private InputOptions inputOptions;
	private List<OutputOptions> outputOptions;
	private GlobalOptions globalOptions;

	private List<String> command = new LinkedList<>();

	private FFmpegEnhanced(FFmpegEnhancedBuilder builder) {
		this.workingDirectory = builder.workingDirectory;
		this.logFile = builder.logFile;
		this.input = builder.input;
		this.outputs = builder.outputs;
		this.inputOptions = builder.inputOptions;
		this.outputOptions = builder.outputOptions;
		this.globalOptions = builder.globalOptions;
	}

	public static FFmpegEnhancedBuilder builder() {
		return new FFmpegEnhancedBuilder();
	}

	public void buildCommandAndExecute() {
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
//	outputs=	[out1,out2]
//	options=	[option1]
		command.add("-i");
		command.add(input);
		for (int i = 0; i < outputs.size(); i++) {
			while (i < outputOptions.size()) {
				command.addAll(outputOptions.get(i).getCommand());
				break;
			}
			command.add(outputs.get(i));

		}
//		if (outputOptions != null) {
//			outputOptions.forEach(outputOptions -> {
//				command.addAll(outputOptions.getCommand());
//			});
//		}
//		outputs.forEach(output -> {
//			command.add(output);
//
//		});
		System.out.println(command);
		ProcessBuilder pb = new ProcessBuilder(command);
		if (this.workingDirectory != null) {
			pb.directory(new File(this.workingDirectory));
		}

		if (logFile != null) {
			pb.redirectError(new File(workingDirectory, this.logFile));
		} else {
			pb.redirectError(ProcessBuilder.Redirect.DISCARD); // Discard error stream
		}
		try {
			Process process = pb.start();
			int exitCode = process.waitFor();
			String msg = (logFile == null) ? "" : " you can check the log of this command at" + logFile;

			if (exitCode == 0) {
				System.out.println("FFmpeg process completed successfully" + msg);
				System.out.println(pb.command());
			} else {
				System.err.println("FFmpeg process failed with exit code: " + exitCode + msg);
				System.out.println(pb.command());
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	public static class FFmpegEnhancedBuilder {

		private String workingDirectory;
		private String logFile;

		private String input;
		private List<String> outputs = new ArrayList<>();
		private InputOptions inputOptions;
		private List<OutputOptions> outputOptions = new ArrayList<>();
		private GlobalOptions globalOptions;

		public FFmpegEnhancedBuilder workingDirectory(String workingDirectory) {
			if (workingDirectory == null || workingDirectory.isBlank())
				return this;
			this.workingDirectory = workingDirectory;
			return this;
		}

		public FFmpegEnhancedBuilder logFile(String logFile) {
			if (logFile == null || logFile.isBlank())
				return this;

			this.logFile = logFile;
			return this;
		}

		public FFmpegEnhancedBuilder input(String input) {
			this.input = input;
			return this;
		}

		public FFmpegEnhancedBuilder outputs(List<String> outputs) {
			this.outputs = outputs;
			return this;
		}

		public FFmpegEnhancedBuilder outputs(String... outputs) {
			this.outputs = List.of(outputs);
			return this;
		}

		public FFmpegEnhancedBuilder inputOptions(InputOptions inputOptions) {
			this.inputOptions = inputOptions;
			return this;
		}

		public FFmpegEnhancedBuilder outputOptions(List<OutputOptions> outputOptions) {
			this.outputOptions = outputOptions;
			return this;
		}

		public FFmpegEnhancedBuilder outputOptions(OutputOptions... outputOptions) {
			this.outputOptions = List.of(outputOptions);
			return this;
		}

		public FFmpegEnhancedBuilder globalOptions(GlobalOptions globalOptions) {
			this.globalOptions = globalOptions;
			return this;
		}

		public FFmpegEnhanced build() {
			return new FFmpegEnhanced(this);
		}
	}
}
