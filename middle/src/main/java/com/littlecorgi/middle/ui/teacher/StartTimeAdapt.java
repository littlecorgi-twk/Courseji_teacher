package com.littlecorgi.middle.ui.teacher;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.littlecorgi.middle.R;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StartTimeAdapt extends BaseQuickAdapter<String, BaseViewHolder> {

    public StartTimeAdapt(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.setTime_itemText, s);
    }
}
