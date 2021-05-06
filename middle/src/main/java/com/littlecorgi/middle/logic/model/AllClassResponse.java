package com.littlecorgi.middle.logic.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取所有班级的相应
 *
 * @author littlecorgi 2021/5/6
 */
@NoArgsConstructor
@Data
public class AllClassResponse {
    private int status;
    private String msg;
    private List<ClassDetailBean> data;
}
