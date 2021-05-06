package com.littlecorgi.middle.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签到model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class DataBean {
    private long id;
    private long createdTime;
    private long lastModifiedTime;
    private String title;
    private String description;
    private long startTime;
    private long endTime;
    private double latitude;
    private double longitude;
    private int radius;
    private int checkInNum;
    private ClassDetailBean classDetail;

}
