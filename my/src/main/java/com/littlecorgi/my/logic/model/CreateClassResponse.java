package com.littlecorgi.my.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建班级的相应
 *
 * @author littlecorgi 2021/5/7
 */
@NoArgsConstructor
@Data
public class CreateClassResponse {
    private int status;
    private String msg;
    private long data;
}
