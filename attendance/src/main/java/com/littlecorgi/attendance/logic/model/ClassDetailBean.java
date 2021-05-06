package com.littlecorgi.attendance.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class ClassDetailBean implements Serializable {
    private int id;
    private long createdTime;
    private long lastModifiedTime;
    private int studentNum;
    private String name;
    private TeacherBean teacher;
}