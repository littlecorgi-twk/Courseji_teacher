package com.littlecorgi.middle.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签到响应类
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class AttendanceResponse {
    private long status;
    private String msg;
    private DataBean data;
}
