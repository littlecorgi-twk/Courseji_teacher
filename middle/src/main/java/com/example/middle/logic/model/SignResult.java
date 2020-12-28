package com.example.middle.logic.model;

import com.google.gson.annotations.SerializedName;

public class SignResult {
    @SerializedName("state")
    boolean state;

    public boolean isState() {
        return state;
    }
}
