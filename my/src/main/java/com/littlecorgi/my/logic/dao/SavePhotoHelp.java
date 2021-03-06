package com.littlecorgi.my.logic.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class SavePhotoHelp {

    // android保存图片到本地
  /*
  博客：
  https://blog.csdn.net/similing/article/details/102659968?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_baidulandingword-2&spm=1001.2101.3001.4242
   */
    public static final String PIC_DIR_NAME =
            "myPhotos"; // 在系统的图片文件夹下创建了一个相册文件夹，名为“myPhotos"，所有的图片都保存在该文件夹下。
    private static final File mPicDir =
            new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    PIC_DIR_NAME); // 图片统一保存在系统的图片文件夹中

    public static Uri saveBitmapToGallery(final Context mContext, String fileName, Bitmap bitmap) {
        OutputStream out = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream(1920 * 1920);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            long size = stream.size();
            stream.close();
            mPicDir.mkdirs();
            String mPicPath = new File(mPicDir, fileName).getAbsolutePath();
            ContentValues values = new ContentValues();
            ContentResolver resolver = mContext.getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, mPicPath);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
            // 将图片的拍摄时间设置为当前的时间
            long current = System.currentTimeMillis() / 1000;
            values.put(MediaStore.Images.ImageColumns.DATE_ADDED, current);
            values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, current);
            values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, current);
            values.put(MediaStore.Images.ImageColumns.SIZE, size);
            values.put(MediaStore.Images.ImageColumns.WIDTH, bitmap.getWidth());
            values.put(MediaStore.Images.ImageColumns.HEIGHT, bitmap.getHeight());
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                out = resolver.openOutputStream(uri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                return uri;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
