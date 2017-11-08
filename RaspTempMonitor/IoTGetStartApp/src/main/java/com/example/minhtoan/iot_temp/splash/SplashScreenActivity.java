package com.example.minhtoan.iot_temp.splash;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.minhtoan.iot_temp.R;
import com.example.minhtoan.iot_temp.cache.StoreManager;
import com.example.minhtoan.iot_temp.homescreen.HomeActivity;
import com.example.minhtoan.iot_temp.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private ObjectAnimator animation;
    private ProgressBar progressBar;
    private TextView tvLogout,tvSuccess;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        pass = getIntent().getStringExtra(LoginActivity.LOGIN);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        tvSuccess = (TextView) findViewById(R.id.tvSuccess);
        startProgress();
        tvLogout.setVisibility(View.GONE);
        tvSuccess.setVisibility(View.GONE);
    }

    private void startProgress() {
        smoothProgress(100, 1000, new Runnable() {
            @Override
            public void run() {
                if (pass.equals(StoreManager.getPass(SplashScreenActivity.this))) {
                    finishProgress();
                } else {
                    progressBar.setProgress(0);
                    tvLogout.setVisibility(View.VISIBLE);
                    tvLogout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                    return;
                }
            }
        });
    }

    private void finishProgress() {
        smoothProgress(100, 200, new Runnable() {
            @Override
            public void run() {
                tvSuccess.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void smoothProgress(int toProgress, int inMilliseconds, final Runnable endRunnable) {
        animation = ObjectAnimator.ofInt(progressBar, "progress", toProgress);
        animation.setDuration(inMilliseconds);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.removeAllListeners();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                endRunnable.run();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animation.start();
    }

}
