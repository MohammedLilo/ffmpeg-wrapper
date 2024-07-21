package com.ffmpeg_wrapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StreamMapping {
	private int streamIndex;
	private String codecType;
	private String codecName;
	private long audioSampleRate;
	private long bitrate;
	private boolean isStreamCopy;
	private List<String> command = new ArrayList<>();

	private StreamMapping(StreamMappingBuilder builder) {
		this.streamIndex = builder.streamIndex;
		this.codecType = builder.codecType;
		this.codecName = builder.codecName;
		this.audioSampleRate = builder.audioSampleRate;
		this.bitrate = builder.bitrate;
		this.isStreamCopy = builder.isStreamCopy;
		this.command = builder.command;
	}

	public static StreamMappingBuilder builder(int streamIndex, String codecType) {
		return new StreamMappingBuilder(streamIndex, codecType);
	}

	public static final class StreamMappingBuilder {
		private int streamIndex;
		private String codecType;
		private String codecName;
		private long audioSampleRate;
		private long bitrate;
		private boolean isStreamCopy;
		private List<String> command = new ArrayList<>();

		public StreamMappingBuilder(int streamIndex, String codecType) {
			this.streamIndex = streamIndex;
			this.codecType = codecType;
		}

//		public StreamMappingBuilder streamIndex(int streamIndex) {
//			this.streamIndex = streamIndex;
//			return this;
//		}
//
//		public StreamMappingBuilder codecType(String codecType) {
//			this.codecType = codecType;
//			return this;
//		}

		public StreamMappingBuilder codecName(String codecName) {
			command.add(String.format("-c:%s:%d", codecType, streamIndex));
			command.add(codecName);
			this.codecName = codecName;
			return this;
		}

		public StreamMappingBuilder audioSampleRate(long audioSampleRate) {
			command.add(String.format("-ar:%s:%d", codecType, streamIndex));
			command.add(audioSampleRate + "");
			this.audioSampleRate = audioSampleRate;
			return this;
		}

		public StreamMappingBuilder bitrate(long bitrate) {
			command.add(String.format("-b:%s:%d", codecType, streamIndex));
			command.add(bitrate + "");
			this.bitrate = bitrate;
			return this;
		}

		public StreamMappingBuilder isStreamCopy(boolean isStreamCopy) {
			this.isStreamCopy = isStreamCopy;
			return this;
		}

		public StreamMapping build() {
			command.add("-map");
			command.add(String.format("%s:%d", codecType, streamIndex));

			if (isStreamCopy) {
				command.add(String.format("-c:%s:%d", codecType, streamIndex));
				command.add("copy");
			}
			return new StreamMapping(this);
		}
	}

}
