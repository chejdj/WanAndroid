package com.chejdj.wanandroid.db.account.entity;

public class Account {
    private String username;

    public Account(String username) {
        this.username = username;
    }

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
