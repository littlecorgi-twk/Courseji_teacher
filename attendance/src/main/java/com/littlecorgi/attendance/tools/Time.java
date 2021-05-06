package com.littlecorgi.attendance.tools;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 时间数据类
 */
@Data
@NoArgsConstructor
public class Time {
    private String time; // 创建时间
    private String proportion; // 统计
    private String class1; // 班级
    private long attendanceId; // 考勤id
}
