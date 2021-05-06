package com.littlecorgi.leave.logic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class ApproveLeaveRequest {
    private int state;
    private String description;
}
