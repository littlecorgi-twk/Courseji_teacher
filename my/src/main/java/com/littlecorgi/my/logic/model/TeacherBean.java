package com.littlecorgi.my.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师的数据类
 *
 * @author littlecorgi 2021/5/7
 */
@NoArgsConstructor
@Data
public class TeacherBean implements Serializable {
    private static final long serialVersionUID = 1234567890604L;
    private long id;
    private long createdTime;
    private long lastModifiedTime;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String avatar;
}
