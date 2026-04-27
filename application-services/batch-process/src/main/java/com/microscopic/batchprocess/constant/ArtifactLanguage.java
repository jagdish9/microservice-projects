package com.microscopic.batchprocess.constant;

import lombok.Getter;

@Getter
public enum ArtifactLanguage {
    JAVA(1, "java"),
    PYTHON(2, "python"),
    NODEJS(3, "nodejs");

    private final int code;
    private final String language;

    ArtifactLanguage(int code, String language) {
        this.code = code;
        this.language = language;
    }
}
