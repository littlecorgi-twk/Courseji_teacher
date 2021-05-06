package com.littlecorgi.middle.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class AttendanceRequest {
    private String title;
    private String description;
    private long startTime;
    private long endTime;
    private double latitude;
    private double longitude;
    private int radius;
}
