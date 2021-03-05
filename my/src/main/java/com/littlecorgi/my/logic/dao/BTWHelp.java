package com.littlecorgi.my.logic.dao;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import com.littlecorgi.my.R;

public class BTWHelp {
  /*
  底部弹窗的实现
   */
  // 第一种方法：使用Dialog弹出
  public static Dialog dialogBtw(View view, Context context) {

    Dialog dialog = new Dialog(context, R.style.DialogTheme);
    dialog.setContentView(view);
    Window window = dialog.getWindow();
    // 设置弹出位置
    window.setGravity(Gravity.BOTTOM);
    // 设置弹出动画
    window.setWindowAnimations(R.style.main_menu_animStyle);
    // 设置对话框大小(宽度为父布局宽度，高度为自适应)
    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.show();
    return dialog;
  }
  // 第二种方法：使用PopupWindow弹出

  public static PopupWindow PopupWindowBtw(View layoutView, View parentView) {

    final PopupWindow popupWindow =
        new PopupWindow(
            layoutView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT); // 参数为1.View 2.宽度 3.高度
    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外边可取消
    popupWindow.setOutsideTouchable(true); // 这两步用于点击手机的返回键的时候，不是直接关闭activity,而是关闭pop框
    popupWindow.setFocusable(true); // 设置获得焦点，负责无法点击
    popupWindow.setAnimationStyle(R.style.main_menu_animStyle); // 设置动画
    popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    return popupWindow;
  }
}
