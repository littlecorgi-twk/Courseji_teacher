package com.littlecorgi.leave.ui;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求Recycler的Item
 */
@Data
@NoArgsConstructor
public class RecyclerItem {

    private String type; // 请假类型
    private String pass; // 审核状态
    private String student; // 学生姓名
    private String time; // 申请时间
    private String reason; // 请假理由
}
