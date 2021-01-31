package com.littlecorgi.my.logic.dao;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Tool {
    //复制到粘切板
    public static void copyHelper(Context context,String copyTitle){
        //添加到剪切板
        ClipboardManager clipboardManager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        /*之前的应用过期的方法，clipboardManager.setText(copy);*/
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copyTitle));
        if (clipboardManager.hasPrimaryClip()) {
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
        Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
    }



}
