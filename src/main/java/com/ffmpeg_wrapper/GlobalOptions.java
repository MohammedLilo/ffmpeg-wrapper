package com.ffmpeg_wrapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalOptions {

	private boolean isOverwriteOutput;
	private boolean isHideBanner;
	private List<String> command = new ArrayList<>();

	private GlobalOptions(GlobalOptionsBuilder builder) {
		this.isOverwriteOutput = builder.isOverwriteOutput;
		this.isHideBanner = builder.isHideBanner;
		this.command = builder.command;
	}

	public static GlobalOptionsBuilder builder() {
		return new GlobalOptionsBuilder();
	}

	public static class GlobalOptionsBuilder {
		private boolean isOverwriteOutput;
		private boolean isHideBanner;
		List<String> command = new ArrayList<>();

		public GlobalOptionsBuilder isOverwriteOutput(boolean isOverwriteOutput) {
			this.isOverwriteOutput = isOverwriteOutput;
			if (isOverwriteOutput)
				command.add("-y");

			return this;
		}

		public GlobalOptionsBuilder isHideBanner(boolean isHideBanner) {
			this.isHideBanner = isHideBanner;
			if (isHideBanner)
				command.add("-hide_banner");

			return this;
		}

		public GlobalOptions build() {
			return new GlobalOptions(this);
		}

	}
}
