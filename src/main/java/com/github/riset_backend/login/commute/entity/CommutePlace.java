package com.github.riset_backend.login.commute.entity;

public enum CommutePlace {
    HEADQUARTERS("HEADQUARTERS"),
    WORK_AT_HOME("WORK_AT_HOME"),
    OUT_ON_BUSINESS("OUT_ON_BUSINESS");

    private final String type;

    CommutePlace(String type){ this.type = type; }

    public String getType() { return type; }
}
