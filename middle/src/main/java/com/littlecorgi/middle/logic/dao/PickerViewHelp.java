package com.littlecorgi.middle.logic.dao;


import android.content.Context;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

public class PickerViewHelp {
    /*
    这个要得到班级的集合，目前的做法是访问我的里的方法，或者我的里把这个集合存在本地，这里去访问
     */
    public static List<String> getClassList() {

        List<String> list = new ArrayList<>();
        list.add("1班");
        list.add("2班");
        list.add("3班");
        list.add("4班");
        list.add("5班");
        list.add("6班");
        return list;
    }
}
