package com.littlecorgi.attendance.logic.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 所有的请假model
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class AllCheckOnResponse implements Serializable {
    private int status;
    private String msg;
    private List<CheckOnBean> data;
}
