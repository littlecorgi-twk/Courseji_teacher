package com.littlecorgi.attendance.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考勤model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class AttendanceBean implements Serializable {
    private int id;
    private long createdTime;
    private long lastModifiedTime;
    private String title;
    private String description;
    private long startTime;
    private long endTime;
    private double latitude;
    private double longitude;
    private int checkInNum;
    private int radius;
    private ClassDetailBean classDetail;
}
