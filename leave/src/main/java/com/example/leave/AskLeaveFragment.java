package com.example.leave;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class AskLeaveFragment extends Fragment {

    private EditText nameEditText;
    private EditText classTextEditText;

    private RadioGroup radioGroupType1;
    private RadioButton sickButton;
    private RadioButton thingButton ;
    private RadioButton otherButton ;
    private RadioGroup radioGroupType2 ;
    private RadioButton yesButton ;
    private RadioButton noButton ;

    private EditText startTimeEditText ;
    private EditText endTimeEditText ;
    private EditText placeEditText ;
    private EditText myPhoneEditText ;
    private EditText otherPhoneEditText ;
    private EditText leaveSituationEditText ;

    private ImageView addPictureImage ;
    private Button submitButton ;

    private String type1;
    private RadioButton radioButton1;
    private String selectText1;
    private String type2;
    private RadioButton radioButton2;
    private String selectText2;

    private String nameText;
    private String classText;
    private String startTimeText ;
    private String endTimeText ;
    private String placeText ;
    private String myPhoneText ;
    private String otherPhoneText ;
    private String leaveSituationText ;

    private Handler handler;
    private Animator animator;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ask_leave,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameEditText = (EditText) getActivity().findViewById(R.id.edit_text_name);
        classTextEditText = (EditText) getActivity().findViewById(R.id.edit_text_class);

        radioGroupType1 = (RadioGroup) getActivity().findViewById(R.id.rg_type1);
        sickButton = (RadioButton)  getActivity().findViewById(R.id.rg_sick);
        thingButton = (RadioButton)  getActivity().findViewById(R.id.rg_thing);
        otherButton = (RadioButton)  getActivity().findViewById(R.id.rg_other);
        radioGroupType2 = (RadioGroup) getActivity().findViewById(R.id.rg_type2);
        yesButton = (RadioButton)  getActivity().findViewById(R.id.rg_yes);
        noButton = (RadioButton)  getActivity().findViewById(R.id.rg_no);

        startTimeEditText = (EditText) getActivity().findViewById(R.id.edit_text_start_time);
        endTimeEditText = (EditText) getActivity().findViewById(R.id.edit_text_end_time);
        placeEditText = (EditText) getActivity().findViewById(R.id.edit_text_place);
        myPhoneEditText = (EditText) getActivity().findViewById(R.id.edit_text_my_phone);
        otherPhoneEditText = (EditText) getActivity().findViewById(R.id.edit_text_other_phone);
        leaveSituationEditText = (EditText) getActivity().findViewById(R.id.edit_text_leave_situation);

        addPictureImage = (ImageView) getActivity().findViewById(R.id.image_add_picture);
        submitButton = getActivity().findViewById(R.id.submit);




        radioGroupType1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton1 = (RadioButton) getActivity().findViewById(radioGroupType1.getCheckedRadioButtonId());
                selectText1 = radioButton1.getText().toString();
            }
        });
        radioGroupType2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton2 = (RadioButton) getActivity().findViewById(radioGroupType2.getCheckedRadioButtonId());
                selectText2 = radioButton2.getText().toString();
            }
        });

        addPictureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = nameEditText.getText().toString();
                classText = classTextEditText.getText().toString();
                startTimeText = startTimeEditText.getText().toString();
                endTimeText = endTimeEditText.getText().toString();
                placeText = placeEditText.getText().toString();
                myPhoneText = myPhoneEditText.getText().toString();
                otherPhoneText = otherPhoneEditText.getText().toString();
                leaveSituationText = leaveSituationEditText.getText().toString();
                SharedPreferences.Editor editor = (SharedPreferences.Editor)
                        getActivity().getSharedPreferences("data",Context.MODE_PRIVATE).edit();
                editor.putString("name",nameText);
                editor.putString("class",classText);
                editor.putString("type1",selectText1);
                editor.putString("type2",selectText2);
                editor.putString("startTime",startTimeText);
                editor.putString("endTime",endTimeText);
                editor.putString("place",placeText);
                editor.putString("myPhone",myPhoneText);
                editor.putString("otherPhone",otherPhoneText);
                editor.putString("leaveSituation",leaveSituationText);
                editor.apply();

                // TabLayout tabLayout = getActivity().findViewById(R.id.view_up_line);
                // tabLayout.getTabAt(1).select();
                // HistroyFragment histroyFragment = new HistroyFragment();
                // FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                // FragmentTransaction transaction = fragmentManager.beginTransaction();
                // transaction.replace(R.id.ask_leave,histroyFragment);
                // transaction.commit();
                ViewPager viewPager = getActivity().findViewById(R.id.leave_viewpager);
                // viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(1);
            }
        });
    }



    private void showDialog(){
        final Dialog dialog = new Dialog(getActivity(),R.style.DialogTheme);
        View view = View.inflate(getContext(),R.layout.layout_dialog_custom,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        dialog.findViewById(R.id.tv_choose_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void takePhoto(){
        
    }
    private void choosePicture(){


    }

}
