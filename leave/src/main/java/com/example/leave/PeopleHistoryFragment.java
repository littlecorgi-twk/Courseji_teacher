package com.example.leave;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class PeopleHistoryFragment extends Fragment {
    private Button buttonReturn;
    private Button textViewReturn;

    private TextView nameText;
    private TextView numberText;
    private TextView startTimeText;
    private TextView endTimeText;
    private TextView type1Text;
    private TextView type2Text;
    private TextView placeText;
    private TextView myPhoneText;
    private TextView otherPhoneText;
    private TextView reasonText;

    private String name;
    private String number;
    private String startTime;
    private String endTime;
    private String type1;
    private String type2;
    private String place;
    private String myPhone;
    private String otherPhone;
    private String reason;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leave_situation,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameText = (TextView) getActivity().findViewById(R.id.name_text);
        numberText = (TextView) getActivity().findViewById(R.id.number);
        startTimeText = (TextView) getActivity().findViewById(R.id.start_name_text);
        endTimeText = (TextView) getActivity().findViewById(R.id.end_time_text);
        type1Text = (TextView) getActivity().findViewById(R.id.type1_text);
        type2Text = (TextView) getActivity().findViewById(R.id.type2_text);
        placeText = (TextView) getActivity().findViewById(R.id.place_text);
        myPhoneText = (TextView) getActivity().findViewById(R.id.my_phone_text);
        otherPhoneText = (TextView) getActivity().findViewById(R.id.other_phone_text);
        reasonText = (TextView) getActivity().findViewById(R.id.reason_text);
        buttonReturn = (Button) getActivity().findViewById(R.id.btn_return);
        textViewReturn = (Button) getActivity().findViewById(R.id.tv_return);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        textViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        name = pref.getString("name","");
        type1 = pref.getString("type1","");
        type2 = pref.getString("type2","");
        startTime = pref.getString("startTime","");
        endTime = pref.getString("endTime","");
        place = pref.getString("place","");
        myPhone = pref.getString("myPhone","");
        otherPhone = pref.getString("otherPhone","");
        reason = pref.getString("leaveSituation","");

        numberText.setText(name);
        type1Text.setText(type1);
        type2Text.setText(type2);
        startTimeText.setText(startTime);
        endTimeText.setText(endTime);
        placeText.setText(place);
        myPhoneText.setText(myPhone);
        otherPhoneText.setText(otherPhone);
        reasonText.setText(reason);
    }
}
