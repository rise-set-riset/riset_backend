package com.github.riset_backend.login.commute.entity;

public enum CommuteStatus {
    START("START"),
    END("END");

    private final String type;

    CommuteStatus(String type) { this.type = type; }

    public String getType() { return type; }
}
