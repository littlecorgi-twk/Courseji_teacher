package com.littlecorgi.attendance.logic.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class AllAttendanceResponse implements Serializable {
    private Integer status;
    private String msg;
    private List<AttendanceBean> data;
}
