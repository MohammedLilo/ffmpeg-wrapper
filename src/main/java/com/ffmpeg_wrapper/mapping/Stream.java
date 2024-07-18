package com.ffmpeg_wrapper.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Stream {
	@JsonProperty("index")
	private int index;

	@JsonProperty("codec_name")
	private String codecName;

	@JsonProperty("profile")
	private String profile;

	@JsonProperty("codec_type")
	private String codecType;

	@JsonProperty("width")
	private int width;

	@JsonProperty("height")
	private int height;

	@JsonProperty("pix_fmt")
	private String pixFmt;

	@JsonProperty("level")
	private int level;

	@JsonProperty("avg_frame_rate")
	private String avgFrameRate;

	@JsonProperty("duration")
	private double duration;

	@JsonProperty("bit_rate")
	private long bitRate;

	@JsonProperty("sample_rate")
	private long sampleRate;

	@JsonProperty("nb_frames")
	private long frameCount;
}