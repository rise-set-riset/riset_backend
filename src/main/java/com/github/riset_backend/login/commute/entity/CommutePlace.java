package com.github.riset_backend.login.commute.entity;

public enum CommutePlace {
    HEADQUARTERS("HEADQUARTERS"),
    HOME("HOME"),
    OUTSIDE("OUTSIDE");

    private final String type;

    CommutePlace(String type){ this.type = type; }

    public String getType() { return type; }
}
