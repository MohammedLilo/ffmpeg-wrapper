package com.ffmpeg_wrapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OutputOptions {
//TODO	delete all redundant fields
//	private String audioSampleRate;
//	private String audioBitrate;
//	private String audioCodec;
	private String duration;
	private String startTime;
	private String endTime;
	private long bufSize;
	private String format;
	private String volume;
	private String videoBitrate;
	private String videoResolution;
	private String videoFrameRate;
	private String videoCodec;
	private String videoAspectRatio;
	private List<StreamMapping> streamsMappings = new ArrayList<>();

	private List<String> command = new ArrayList<>();

	private OutputOptions(OutputOptionsBuilder builder) {
//		this.audioSampleRate = builder.audioSampleRate;
//		this.audioBitrate = builder.audioBitrate;
//		this.audioCodec = builder.audioCodec;
		this.bufSize = builder.bufSize;
		this.format = builder.format;
		this.volume = builder.volume;
		this.videoBitrate = builder.videoBitrate;
		this.videoResolution = builder.videoResolution;
		this.videoFrameRate = builder.videoFrameRate;
		this.videoCodec = builder.videoCodec;
		this.videoAspectRatio = builder.videoAspectRatio;
		this.streamsMappings = builder.streamsMappings;
		this.duration = builder.duration;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;
		this.command = builder.command;
	}

	public static OutputOptionsBuilder builder() {
		return new OutputOptionsBuilder();
	}

	public static class OutputOptionsBuilder {
//		private String audioSampleRate;
//		private String audioBitrate;
//		private String audioCodec;	
		private String duration;
		private String startTime;
		private String endTime;
		private long bufSize;
		private String format;
		private String volume;
		private String videoBitrate;
		private String videoResolution;
		private String videoFrameRate;
		private String videoCodec;
		private String videoAspectRatio;
		private List<StreamMapping> streamsMappings = new ArrayList<>();
		private List<String> command = new ArrayList<>();

//		public OutputOptionsBuilder audioSampleRate(String audioSampleRate) {
//			command.add("-ar");
//			command.add(audioSampleRate);
//			this.audioSampleRate = audioSampleRate;
//			return this;
//		}

//		public OutputOptionsBuilder audioBitrate(String audioBitrate) {
//			command.add("-b:a");
//			command.add(audioBitrate);
//			this.audioBitrate = audioBitrate;
//			return this;
//		}

//		public OutputOptionsBuilder audioCodec(String audioCodec) {
//			command.add("-c:a");
//			command.add(audioCodec);
//			this.audioCodec = audioCodec;
//			return this;
//		}
		public OutputOptionsBuilder duration(String duration) {
			command.add("-t");
			command.add(duration);
			this.duration = duration;
			return this;
		}

		public OutputOptionsBuilder startTime(String startTime) {
			command.add("-ss");
			command.add(startTime);
			this.startTime = startTime;
			return this;
		}

		public OutputOptionsBuilder endTime(String endTime) {
			command.add("-to");
			command.add(endTime);
			this.endTime = endTime;
			return this;
		}

		public OutputOptionsBuilder bufSize(long bufSize) {
			command.add("-bufsize");
			command.add(String.valueOf(bufSize));
			this.bufSize = bufSize;
			return this;
		}

		public OutputOptionsBuilder format(String format) {
			command.add("-f");
			command.add(format);
			this.format = format;
			return this;
		}

		public OutputOptionsBuilder volume(String volume) {
			command.add("-af");
			command.add("volume=" + volume);
			this.volume = volume;
			return this;
		}

		public OutputOptionsBuilder videoBitrate(String videoBitrate) {
			command.add("-b:v");
			command.add(videoBitrate);
			this.videoBitrate = videoBitrate;
			return this;
		}

		public OutputOptionsBuilder videoResolution(String videoResolution) {
			command.add("-s");
			command.add(videoResolution);
			this.videoResolution = videoResolution;
			return this;
		}

		public OutputOptionsBuilder videoFrameRate(String videoFrameRate) {
			command.add("-r");
			command.add(videoFrameRate);
			this.videoFrameRate = videoFrameRate;
			return this;
		}

		public OutputOptionsBuilder videoCodec(String videoCodec) {
			command.add("-c:v");
			command.add(videoCodec);

			this.videoCodec = videoCodec;
			return this;
		}

		public OutputOptionsBuilder videoAspectRatio(String videoAspectRatio) {
			command.add("-aspect");
			command.add(videoAspectRatio);

			this.videoAspectRatio = videoAspectRatio;
			return this;
		}

		public OutputOptionsBuilder streamsMappings(StreamMapping... streamsMappings) {
			if (streamsMappings == null)
				throw new RuntimeException("you can't set streams mappings to null");
			this.streamsMappings = List.of(streamsMappings);
			this.streamsMappings.forEach(mapping -> {
				this.command.addAll(mapping.getCommand());
			});
			return this;
		}

		public OutputOptionsBuilder streamsMappings(List<StreamMapping> streamsMappings) {
			if (streamsMappings == null)
				throw new RuntimeException("you can't set streams mappings to null");
			this.streamsMappings = streamsMappings;
			this.streamsMappings.forEach(mapping -> {
				this.command.addAll(mapping.getCommand());
			});

			return this;
		}

		public OutputOptions build() {
			return new OutputOptions(this);
		}
	}

}
