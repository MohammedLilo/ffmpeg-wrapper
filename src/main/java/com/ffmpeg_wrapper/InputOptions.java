package com.ffmpeg_wrapper;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InputOptions {

	private String duration;
	private String startTime;
	private String endTime;
	private Map<String, String> command;

	private InputOptions(InputOptionsBuilder builder) {
		this.duration = builder.duration;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;
		this.command = builder.command;
	}

	public static InputOptionsBuilder builder() {
		return new InputOptionsBuilder();
	}

	public static class InputOptionsBuilder {
		private String duration;
		private String startTime;
		private String endTime;
		private Map<String, String> command = new HashMap<>();

		public InputOptionsBuilder duration(String duration) {
			command.put("-t", duration);
			this.duration = duration;
			return this;
		}

		public InputOptionsBuilder startTime(String startTime) {
			command.put("-ss", startTime);
			this.startTime = startTime;
			return this;
		}

		public InputOptionsBuilder endTime(String endTime) {
			command.put("-to", endTime);
			this.endTime = endTime;
			return this;
		}

		public InputOptions build() {
			return new InputOptions(this);
		}
	}
}
