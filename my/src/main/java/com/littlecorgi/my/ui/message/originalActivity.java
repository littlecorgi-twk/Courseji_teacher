package com.littlecorgi.my.ui.message;

import static com.littlecorgi.my.logic.dao.BTWHelp.dialogBtw;
import static com.littlecorgi.my.logic.dao.SavePhotoHelp.saveBitmapToGallery;
import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.bumptech.glide.Glide;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.my.R;

public class originalActivity extends BaseActivity {
  private AppCompatImageView display_image;
  private View emptyView1;
  private View emptyView2;
  private String path;
  private Dialog dialog;
  private View displayBtw;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_picuure_display);
    initView();
    initData();
    initClick();
  }

  private void initClick() {
    display_image.setOnLongClickListener(
        v -> {
          showBtw();
          return true;
        });
    display_image.setOnClickListener(v -> finish());
    emptyView1.setOnClickListener(v -> finish());
    emptyView2.setOnClickListener(v -> finish());
  }

  private void showBtw() {
    if (dialog != null) {
      dialog.show();
    } else {
      dialog = dialogBtw(displayBtw, this);
    }
  }

  private void initBarColor() {
    setWindowStatusBarColor(this, R.color.blue);
  }

  private void initView() {
    initBarColor();
    display_image = findViewById(R.id.my_display_image);
    emptyView1 = findViewById(R.id.display_emptyView1);
    emptyView2 = findViewById(R.id.display_emptyView2);
    displayBtw = View.inflate(this, R.layout.my_display_btw, null);
    AppCompatTextView save = displayBtw.findViewById(R.id.display_btw_save);
    AppCompatTextView cancel = displayBtw.findViewById(R.id.display_btw_cancel);
    save.setOnClickListener(
        v -> {
          saveImage();
          dialog.dismiss();
        });
    cancel.setOnClickListener(v -> dialog.dismiss());
  }

  private void saveImage() {
    /*
       吧图片保存到本地
    */
    Bitmap bitmap = BitmapFactory.decodeFile(path);
    // 使用bitmap.hashCode()作为文件名来保存图片
    saveBitmapToGallery(this, bitmap.hashCode() + "", bitmap);
    Toast.makeText(this, "图片已保存", Toast.LENGTH_LONG).show();
  }

  private void initData() {
    Intent intent = getIntent();
    path = intent.getStringExtra("imagePath");
    Glide.with(this).load(path).into(display_image);
  }

  public static void StartOriginalActivity(Context context, String imagePath) {
    Intent intent = new Intent(context, originalActivity.class);
    intent.putExtra("imagePath", imagePath);
    context.startActivity(intent);
  }
}
