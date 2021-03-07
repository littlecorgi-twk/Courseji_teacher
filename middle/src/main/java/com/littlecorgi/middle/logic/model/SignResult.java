package com.littlecorgi.middle.logic.model;

import com.google.gson.annotations.SerializedName;

/**
 * 登录结果
 */
public class SignResult {

    @SerializedName("state")
    boolean state;

    public boolean isState() {
        return state;
    }
}
