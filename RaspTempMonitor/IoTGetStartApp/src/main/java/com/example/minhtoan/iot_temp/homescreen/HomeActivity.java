package com.example.minhtoan.iot_temp.homescreen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.minhtoan.iot_temp.R;
import com.example.minhtoan.iot_temp.homescreen.fragment.HomeFragment;
import com.example.minhtoan.iot_temp.homescreen.fragment.IntroduceFragment;
import com.example.minhtoan.iot_temp.homescreen.fragment.SettingFragment;
import com.example.minhtoan.iot_temp.login.LoginActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private View btnMenu;
    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemLogout;
    private ResideMenuItem itemExit;
    private ResideMenuItem itemSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        String mgs = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(this, mgs, Toast.LENGTH_SHORT).show();
        fragmentManager = getFragmentManager();

        mContext = this;
        setUpMenu();
        if (savedInstanceState == null)
            redirectFragment(new HomeFragment());
        setupView();
    }

    private void setupView() {

    }

    private void redirectFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , fragment)
                .commit();
    }


    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.7f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemAbout = new ResideMenuItem(this, R.drawable.ic_about, "About");
        itemLogout = new ResideMenuItem(this, R.drawable.ic_logout, "Logout");
        itemExit = new ResideMenuItem(this, R.drawable.ic_exit, "Exit");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

        itemHome.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        itemLogout.setOnClickListener(this);
        itemExit.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemExit, ResideMenu.DIRECTION_LEFT);


        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome) {
            redirectFragment(new HomeFragment());
        } else if (view == itemAbout) {
            redirectFragment(new IntroduceFragment());
        } else if (view == itemExit) {
            finish();
        } else if (view == itemSettings) {
            redirectFragment(new SettingFragment());
        } else if (view == itemLogout) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {

        }
    };

}

