package com.toyboardproject.domain;

public enum BoardType {
    NOTICE("NOTICE"),
    FREE("FREE"),

    FAQ_PAYMENT("FAQ_PAYMENT"),
    FAQ_USE("FAQ_USE"),
    FAQ_OTHER("FAQ_OTHER");




    private final String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
