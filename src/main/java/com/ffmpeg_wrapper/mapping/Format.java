package com.ffmpeg_wrapper.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Format {
	@JsonProperty("filename")
	private String filename;

	@JsonProperty("nb_streams")
	private int numberOfStreams;

	@JsonProperty("nb_programs")
	private int numberOfPrograms;

	@JsonProperty("format_name")
	private String formatName;

	@JsonProperty("format_long_name")
	private String formatLongName;

	@JsonProperty("start_time")
	private double startTime;

	@JsonProperty("duration")
	private double duration;

	@JsonProperty("size")
	private long size;

	@JsonProperty("bit_rate")
	private long totalBitRate;

	@JsonProperty("probe_score")
	private int probeScore;

	@JsonProperty("tags")
	private Tags tags;
}
