package com.littlecorgi.middle.ui.student;

import static com.littlecorgi.middle.logic.dao.WindowHelp.setWindowStatusBarColor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.bumptech.glide.Glide;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.model.Details;

public class middleDetailsActivity extends BaseActivity {

  /*
  详情页
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.middle_details);
    initView();
    initData();
    initClick();
  }

  private void initView() {
    changeBarColor();
  }

  private void changeBarColor() {
    setWindowStatusBarColor(this, R.color.blue);
  }

  private void initClick() {
    AppCompatTextView ReturnButton = findViewById(R.id.middle_sign_returnButton);
    ReturnButton.setOnClickListener(v -> finish());
  }

  private void initData() {
    AppCompatImageView imageView = findViewById(R.id.middle_sign_Image);
    AppCompatTextView name = findViewById(R.id.middle_sign_name);
    AppCompatTextView occupational = findViewById(R.id.middle_sign_occupational);
    AppCompatTextView label = findViewById(R.id.middle_sign_label);
    AppCompatTextView startTime = findViewById(R.id.middle_sign_startTime);
    AppCompatTextView endTime = findViewById(R.id.middle_sign_endTime);
    AppCompatTextView theme = findViewById(R.id.middle_sign_theme);
    AppCompatTextView title = findViewById(R.id.middle_sign_title);

    Intent intent = getIntent();
    Details details = (Details) intent.getSerializableExtra("details");
    assert details != null;
    Glide.with(this).load(details.getImage()).into(imageView); // Glide加载图片
    name.setText(details.getName());
    occupational.setText(details.getOccupational());
    label.setText(details.getLabel());
    startTime.setText(details.getStartTime());
    endTime.setText(details.getEndTime());
    theme.setText(details.getTheme());
    title.setText(details.getTitle());
  }

  public static void StartDetails(Context context, Details details) {
    Intent intent = new Intent(context, middleDetailsActivity.class);
    intent.putExtra("details", details);
    context.startActivity(intent);
  }
}
