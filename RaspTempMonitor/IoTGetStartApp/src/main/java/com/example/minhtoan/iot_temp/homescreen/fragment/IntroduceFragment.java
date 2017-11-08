package com.example.minhtoan.iot_temp.homescreen.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhtoan.iot_temp.R;

/**
 * Created by user on 12/31/15.
 */
public class IntroduceFragment extends Fragment {

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_introduce_layout, container, false);

        return myView;

    }
}
