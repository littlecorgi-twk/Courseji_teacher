package com.littlecorgi.attendance.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class StudentBean implements Serializable {
    private int id;
    private long createdTime;
    private long lastModifiedTime;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String avatar;
    private String picture;
}
