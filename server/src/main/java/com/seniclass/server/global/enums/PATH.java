package com.seniclass.server.global.enums;

public enum PATH {
    CDN_STREAMING_PATH("https://cdn.senicle.com/"),
    ;

    final String path;

    PATH(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
