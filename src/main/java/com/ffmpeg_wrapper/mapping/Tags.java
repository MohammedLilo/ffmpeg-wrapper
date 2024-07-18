package com.ffmpeg_wrapper.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tags {
	@JsonProperty("title")
	private String title;

	@JsonProperty("artist")
	private String artist;

	@JsonProperty("album_artist")
	private String albumArtist;

	@JsonProperty("album")
	private String album;

	@JsonProperty("genre")
	private String genre;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("composer")
	private String composer;

	@JsonProperty("creation_time")
	private String creationTime;

	@JsonProperty("language")
	private String language;

	@JsonProperty("copyright")
	private String copyright;

	@JsonProperty("performer")
	private String performer;

	@JsonProperty("publisher")
	private String publisher;

	@JsonProperty("track")
	private String track;

	@JsonProperty("season_number")
	private String seasonNumber;

	@JsonProperty("episode_id")
	private String episodeId;
}
