package com.ffmpeg_wrapper.mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FFprobeOutput {
	@JsonProperty("streams")
	private List<Stream> streams;

	@JsonProperty("format")
	private Format format;

}
