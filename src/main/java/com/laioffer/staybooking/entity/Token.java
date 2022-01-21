package com.laioffer.staybooking.entity;

// 为了向前端返回token 以json格式 需要创建一个Token class
public class Token {
    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}