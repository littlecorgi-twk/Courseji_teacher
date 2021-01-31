package com.example.attendance.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.attendance.R;

public class TitleView extends RelativeLayout {

    private LinearLayout layoutLeft,layoutRight;
    private TextView tvTitle;

    private LeftClickListener leftClickListener;
    private RightClickListener rightClickListener;

    private LinearLayout rlViewGroup;
    private Button btnReturn,btnReturnText,btnMenuText,btnMenu;

    private View viewLine;

    public TitleView(Context context){
        super(context,null);
    }
    public TitleView(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_titleview,this);
        initView();
        initAttrs(context,attrs);
        onButtonClick();
    }
    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void setLeftClickListener(LeftClickListener leftClickListener){
        this.leftClickListener = leftClickListener;
    }
    public void setLeftClickListener(RightClickListener rightClickListener){
        this.rightClickListener = rightClickListener;
    }

    public void setTitleText(String title){
        tvTitle.setText(title);
    }

    public void setRightTextSize(float size){
        if(btnMenuText!=null){
            btnMenuText.setTextSize(size);
        }
    }

    private void onButtonClick(){
        layoutLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftClickListener != null){
                    leftClickListener.OnLeftButtonClick();
                }
            }
        });

        layoutRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightClickListener!=null){
                    rightClickListener.OnRightButtonClick();
                }
            }
        });
    }

    private void initView(){
        rlViewGroup = findViewById(R.id.rl_viewGroup);
        layoutLeft = findViewById(R.id.ll_left);
        layoutRight = findViewById(R.id.ll_right);
        btnMenuText = findViewById(R.id.tv_menu);
        btnReturnText = findViewById(R.id.tv_return);
        tvTitle = findViewById(R.id.tv_title);
        btnMenu = findViewById(R.id.btn_menu);
        btnReturn = findViewById(R.id.btn_return);
        viewLine = findViewById(R.id.view_up_line);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        Drawable viewGroupDrawable = typedArray.getDrawable(R.styleable.TitleView_titleBackground);
        if(viewGroupDrawable==null){
            viewGroupDrawable = getResources().getDrawable(R.drawable.shape_gradient_title);
        }
        rlViewGroup.setBackground(viewGroupDrawable);

        float titleSize = typedArray.getDimension(R.styleable.TitleView_titleTextSize,22);
        int titleColor = typedArray.getColor(R.styleable.TitleView_titleColor, Color.WHITE);
        String titleText = typedArray.getString(R.styleable.TitleView_titleText);
        tvTitle.setText(titleText);
        tvTitle.setTextSize(titleSize);
        tvTitle.setTextColor(titleColor);

        Drawable leftIcon = typedArray.getDrawable(R.styleable.TitleView_leftIcon);
        if(leftIcon==null){
            leftIcon = getResources().getDrawable(R.mipmap.title_return);
        }
        String leftText = typedArray.getString(R.styleable.TitleView_leftText);
        if(leftText == null || leftText.equals("")){
            leftText = "返回";
        }
        float leftTextSize = typedArray.getDimension(R.styleable.TitleView_leftTextSize,20);
        int leftTextColor = typedArray.getColor(R.styleable.TitleView_titleColor, Color.WHITE);
        boolean leftIconVisibility = typedArray.getBoolean(R.styleable.TitleView_leftIconVisibility,true);
        boolean leftTextVisibility = typedArray.getBoolean(R.styleable.TitleView_leftTextVisibility,true);
        btnReturn.setBackground(leftIcon);
        if(leftIconVisibility){
            btnReturn.setVisibility(View.VISIBLE);
        }else{
            btnReturn.setVisibility(View.GONE);
        }
        if(leftTextVisibility){
            btnReturnText.setVisibility(View.VISIBLE);
        }else{
            btnReturnText.setVisibility(View.GONE);
        }
        btnReturnText.setText(leftText);
        btnReturnText.setTextColor(leftTextColor);
        btnReturnText.setTextSize(leftTextSize);

        Drawable rightIcon = typedArray.getDrawable(R.styleable.TitleView_rightIcon);
        if(rightIcon==null){
            rightIcon = getResources().getDrawable(R.mipmap.title_menu);
        }
        String rightText = typedArray.getString(R.styleable.TitleView_rightText);
        if(rightText==null || leftText.equals("")){
            rightText = "菜单";
        }
        float rightTextSize = typedArray.getDimension(R.styleable.TitleView_rightTextSize,20);
        int rightTextColor = typedArray.getColor(R.styleable.TitleView_rightTextColor,Color.WHITE);
        boolean rightIconVisibility = typedArray.getBoolean(R.styleable.TitleView_rightIconVisibility,false);
        boolean rightTextVisibility = typedArray.getBoolean(R.styleable.TitleView_rightTextVisibility,false);
        btnMenu.setBackground(rightIcon);
        if(rightIconVisibility){
            btnMenu.setVisibility(View.VISIBLE);
        }else{
            btnMenu.setVisibility(View.GONE);
        }
        if(rightTextVisibility){
            btnMenuText.setVisibility(View.VISIBLE);
        }else{
            btnMenuText.setVisibility(View.GONE);
        }
        btnMenuText.setText(rightText);
        btnMenuText.setTextColor(rightTextColor);
        btnMenuText.setTextSize(rightTextSize);
        typedArray.recycle();
    }

    public void setRightMenuTextVisible(boolean visible){
        if(visible){
            layoutRight.setVisibility(View.VISIBLE);
        }else{
            layoutRight.setVisibility(View.GONE);
        }
    }

    public void setRightTextVisible(boolean visible){
        if(visible){
            btnMenuText.setVisibility(View.VISIBLE);
        }else{
            btnMenuText.setVisibility(View.GONE);
        }
    }

    public void setLeftMenuTextVisible(boolean visible){
        if(visible){
            layoutLeft.setVisibility(View.VISIBLE);
        }else{
            layoutLeft.setVisibility(View.GONE);
        }
    }

    public void setViewUpLineVisible(boolean b){
        if(b){
            viewLine.setVisibility(VISIBLE);
        }else{
            viewLine.setVisibility(GONE);
        }
    }

    public LinearLayout getLayoutRight(){
        return layoutRight;
    }

    public void setRightText(String text){
        btnMenuText.setText(text);
    }

    public interface LeftClickListener {
        void OnLeftButtonClick();
    }

    public interface RightClickListener {
        void OnRightButtonClick();
    }



}
