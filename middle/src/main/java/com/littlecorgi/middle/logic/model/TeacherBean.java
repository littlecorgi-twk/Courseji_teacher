package com.littlecorgi.middle.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class TeacherBean {
    private long id;
    private long createdTime;
    private long lastModifiedTime;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String avatar;
}