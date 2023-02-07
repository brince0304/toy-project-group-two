package com.toyboardproject.domain;

public enum SearchType {

    TITLE("TITLE"),
    CONTENT("CONTENT"),

    USER_NICKNAME("USER_NICKNAME"),
    USER_ID("USER_ID");





    private final String value;

    SearchType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
