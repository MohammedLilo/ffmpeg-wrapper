package com.ffmpeg_wrapper;

public enum VerbosityLevel {
    QUIET("quiet"),
    PANIC("panic"),
    FATAL("fatal"),
    ERROR("error"),
    WARNING("warning"),
    INFO("info"),
    VERBOSE("verbose"),
    DEBUG("debug"),
    TRACE("trace");

    private final String level;

    VerbosityLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level;
    }
}
