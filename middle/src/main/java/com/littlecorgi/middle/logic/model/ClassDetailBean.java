package com.littlecorgi.middle.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class ClassDetailBean {
    private long id;
    private long createdTime;
    private long lastModifiedTime;
    private String name;
    private int studentNum;
    private TeacherBean teacher;

}
