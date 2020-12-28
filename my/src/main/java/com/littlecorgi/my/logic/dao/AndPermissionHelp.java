package com.littlecorgi.my.logic.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;


import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class AndPermissionHelp {
    @SuppressLint("WrongConstant")
    public static void andPermission(Context mContext, String... permissionList){
        AndPermission.with(mContext)
                .runtime()
                .permission(permissionList)
                /*
                可以通过实现Rationale接口重写showRationale方法，
                    这个方法是会在如果用户拒绝过该权限，则下次会走showRationale方法，这里面一般是以一个弹窗方式来提示用户。
                    .rationale(this)则是设置监听
                 */
                /*参数是
                 Rationale<List<String>> rationale接口
                 里面有一个方法
                  void showRationale(Context context, T data, RequestExecutor executor);
              */
                //onGranted则是授权成功之后的回调
                .rationale((context, data, executor) -> {
                    List<String> permissionNames = Permission.transformText(context, data);
                    String message = "请授权该下的权限" + "\n" + permissionNames;
                    new android.app.AlertDialog.Builder(context)
                            .setCancelable(false)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", (dialog, which) -> executor.execute())
                            .setNegativeButton("取消", (dialog, which) -> executor.cancel())
                            .show();
                })
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                //onDenied是权限被拒绝时的回调
                .onDenied(data -> {
                    Toast.makeText(mContext, "没有获取照相机权限，该功能无法使用", Toast.LENGTH_SHORT).show();
                    if (AndPermission.hasAlwaysDeniedPermission(mContext, data)) {
                        //true，弹窗再次向用户索取权限
                        Toast.makeText(mContext, "没有获取权限，该功能无法使用", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }
}
