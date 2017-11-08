package com.example.minhtoan.iot_temp.homescreen.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.minhtoan.iot_temp.homescreen.Manager;
import com.example.minhtoan.iot_temp.R;

public class HomeFragment extends Fragment {

    View myView;
    WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_home_layout, container, false);
        webview = myView.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(Manager.URL_TEST);
        webview.setHorizontalScrollBarEnabled(false);
        return myView;

    }
}
