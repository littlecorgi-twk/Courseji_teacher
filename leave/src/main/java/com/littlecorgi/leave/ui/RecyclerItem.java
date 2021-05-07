package com.littlecorgi.leave.ui;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求Recycler的Item
 */
@Data
@NoArgsConstructor
public class RecyclerItem implements Serializable {
    private long leaveId; // 请假id
    private String type; // 请假类型
    private String pass; // 审核状态
    private long studentId; // 学生id
    private String student; // 学生姓名
    private String studentPhone; // 学生电话
    private String studentAvatar; // 学生头像
    private String startTime; // 开始时间
    private String endTime; // 开始时间
    private String time; // 申请时间
    private String reason; // 请假理由
}
