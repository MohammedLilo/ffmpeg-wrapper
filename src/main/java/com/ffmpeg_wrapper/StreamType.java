package com.ffmpeg_wrapper;

public enum StreamType {
	ALL(""),
	VIDEO("v"),
    AUDIO("a"),
    SUBTITLE("s"),
    DATA("d"),
    ATTACHMENT("t");

    private final String type;

    StreamType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
