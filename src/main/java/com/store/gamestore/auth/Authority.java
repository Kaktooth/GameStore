package com.store.gamestore.auth;

public enum Authority {
    USER(0), ADMIN(1);

    private final Integer numVal;

    Authority(Integer numVal) {
        this.numVal = numVal;
    }

    public Integer getNumVal() {
        return numVal;
    }
}
