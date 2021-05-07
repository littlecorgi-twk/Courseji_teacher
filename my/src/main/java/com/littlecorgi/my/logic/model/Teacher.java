package com.littlecorgi.my.logic.model;

import java.io.Serializable;

/**
 * @author littlecorgi 2021/5/4
 */
@lombok.NoArgsConstructor
@lombok.Data
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1234567890603L;
    private int status;
    private String msg;
    private String errorMsg;
    private TeacherBean data;
}
