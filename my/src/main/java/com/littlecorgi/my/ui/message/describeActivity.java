package com.littlecorgi.my.ui.message;

import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.my.R;
import com.littlecorgi.my.logic.dao.TextInputHelper;

public class describeActivity extends BaseActivity {
  public static int REQUEST_CODE = 1;
  public static String REQUEST_DATA = "EditTitleData";

  private AppCompatTextView returnButton;
  private AppCompatEditText editText;
  private AppCompatButton SureButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_describe);
    initView();
    initData();
  }

  private void initData() {
    setButtonColor();
  }

  private void setButtonColor() {
    /*
    根据EditView是否有输入改变SureButton的颜色及是否可以点击
     */
    TextInputHelper helper =
        new TextInputHelper(
            SureButton,
            (view, isEmpty) -> {
              if (!isEmpty) {
                view.setAlpha(1);
                view.setBackgroundColor(Color.parseColor("#4CAF50"));
              } else {
                view.setAlpha((float) 0.5);
                view.setBackgroundColor(Color.parseColor("#C49A9999"));
              }
            });
    helper.addViews(editText);
  }

  private void initView() {
    AppCompatImageView imageView = findViewById(R.id.my_SetTheme_bg);
    returnButton = findViewById(R.id.my_SetTheme_returnButton);
    editText = findViewById(R.id.my_describe_EditText);
    SureButton = findViewById(R.id.my_describe_SureButton);
    initBarColor();
    initImage();
    initClick();
  }

  private void initBarColor() {
    setWindowStatusBarColor(this, R.color.blue);
  }

  private void initImage() {
    /*
    设置背景？
     */
  }

  private void initClick() {
    returnButton.setOnClickListener(
        v -> {
          editText.setText("");
          finish();
        });
    SureButton.setOnClickListener(
        v -> {
          Intent intent = new Intent();
          intent.putExtra(REQUEST_DATA, editText.getText().toString());
          setResult(RESULT_OK, intent);
          finish();
        });
  }

  public static void StartDescribeActivity(Activity activity) {
    Intent intent = new Intent(activity, describeActivity.class);
    activity.startActivityForResult(intent, REQUEST_CODE);
  }
}
