package com.ffmpeg_wrapper;

public enum StreamMetadata {
	INDEX("index"),
    CODEC_NAME("codec_name"),
    CODEC_TYPE("codec_type"),
    BIT_RATE("bit_rate"),
    WIDTH("width"),
    HEIGHT("height"),
    SAMPLE_RATE("sample_rate"),
    CHANNELS("channels"),
    DURATION("duration"),
    PIX_FMT("pix_fmt"),
    PROFILE("profile"),
    LEVEL("level"),
    AVG_FRAME_RATE("avg_frame_rate"),
    FRAME_COUNT("nb_frames"),
    ALL(" ");
	
    private final String metadata;

    StreamMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public String toString() {
        if (this == ALL) {
			StringBuilder allMetadata = new StringBuilder();
			for (StreamMetadata sm : values()) {
				allMetadata.append(sm.metadata+",");
			}
			allMetadata.setLength(allMetadata.length()-1);
			return allMetadata.toString();
		}
		return metadata;
	}
}
