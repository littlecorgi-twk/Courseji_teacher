package com.littlecorgi.my.logic.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlecorgi 2021/5/7
 */
@NoArgsConstructor
@Data
public class AllClassResponse {
    private int status;
    private String msg;
    private List<ClassBean> data;
}
