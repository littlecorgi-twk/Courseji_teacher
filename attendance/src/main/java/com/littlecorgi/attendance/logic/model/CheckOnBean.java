package com.littlecorgi.attendance.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签到model
 *
 * @author littlecorgi 2021/5/6
 */
@Data
@NoArgsConstructor
public class CheckOnBean implements Serializable {
    private int id;
    private long createdTime;
    private long lastModifiedTime;
    private int checkOnStates;
    private double longitude;
    private double latitude;
    private StudentBean student;
    private AttendanceBean attendance;
}
