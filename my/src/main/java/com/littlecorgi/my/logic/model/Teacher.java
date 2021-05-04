package com.littlecorgi.my.logic.model;

import java.io.Serializable;

/**
 * @author littlecorgi 2021/5/4
 */
@lombok.NoArgsConstructor
@lombok.Data
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1234567890601L;

    private Integer status;
    private String msg;
    private String errorMsg;
    private DataBean data;

    /**
     * 具体的Student类
     */
    @lombok.NoArgsConstructor
    @lombok.Data
    public static class DataBean implements Serializable {
        private static final long serialVersionUID = 1234567890602L;
        private long id;
        private String name;
        private String email;
        private String password;
        private String phone;
        private String avatar;
    }
}
