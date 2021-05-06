package com.littlecorgi.leave.logic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审批请假的响应
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class ApproveLeaveResponse implements Serializable {
    private static final long serialVersionUID = 1234567890408L;
    private int status;
    private String msg;
    private LeaveBean data;
}
