package com.littlecorgi.my.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级的数据类
 *
 * @author littlecorgi 2021/5/7
 */
@NoArgsConstructor
@Data
public class ClassBean implements Serializable {
    private static final long serialVersionUID = 1234567890601L;
    private long id;
    private long createdTime;
    private long lastModifiedTime;
    private String name;
    private Integer studentNum;
    private TeacherBean teacher;
}
