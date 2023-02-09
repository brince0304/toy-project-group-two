package com.toyboardproject.domain;

import lombok.Getter;

@Getter
public enum AccountRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");


    AccountRole(String value) {
        this.value = value;
    }

    private final String value;

}