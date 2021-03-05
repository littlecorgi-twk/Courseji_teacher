package com.littlecorgi.my.logic.dao;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class WindowHelp {
  /*
  动态修改标题栏的颜色
   */
  public static void setWindowStatusBarColor(Activity activity, int colorResId) {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 顶部状态栏
        window.setStatusBarColor(activity.getResources().getColor(colorResId));
        // 底部导航栏
        // window.setNavigationBarColor(activity.getResources().getColor(colorResId));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
