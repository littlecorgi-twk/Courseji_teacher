package com.littlecorgi.my.logic.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * android保存图片到本地
 *
 * <p>博客： https://blog.csdn.net/similing/article/details/102659968?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_baidulandingword-2&spm=1001.2101.3001.4242
 */
public class SavePhotoHelp {

    private static final String TAG = "SavePhotoHelp";

    // 在系统的图片文件夹下创建了一个相册文件夹，名为“myPhotos"，所有的图片都保存在该文件夹下。
    public static final String PIC_DIR_NAME = "myPhotos";
    // 图片统一保存在系统的图片文件夹中
    private static final File mPicDir = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            PIC_DIR_NAME);

    /**
     * 保存Bitmap到相册中
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param bitmap   图片Bitmap对象
     * @return 图片文件的Uri
     */
    public static Uri saveBitmapToGallery(final Context context, String fileName, Bitmap bitmap) {
        OutputStream out = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream(1920 * 1920);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            boolean mkDirsSuc = mPicDir.mkdirs();
            if (mkDirsSuc) {
                String picPath = new File(mPicDir, fileName).getAbsolutePath();
                ContentValues values = new ContentValues();
                ContentResolver resolver = context.getContentResolver();
                values.put(MediaStore.Images.ImageColumns.DATA, picPath);
                values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
                // 将图片的拍摄时间设置为当前的时间
                long current = System.currentTimeMillis() / 1000;
                values.put(MediaStore.Images.ImageColumns.DATE_ADDED, current);
                values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, current);
                values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, current);
                long size = stream.size();
                values.put(MediaStore.Images.ImageColumns.SIZE, size);
                values.put(MediaStore.Images.ImageColumns.WIDTH, bitmap.getWidth());
                values.put(MediaStore.Images.ImageColumns.HEIGHT, bitmap.getHeight());
                Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    out = resolver.openOutputStream(uri);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    return uri;
                }
            } else {
                Log.e(TAG, "saveBitmapToGallery:");
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
