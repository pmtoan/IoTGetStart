package com.example.minhtoan.iot_temp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.minhtoan.iot_temp.R;
import com.example.minhtoan.iot_temp.splash.SplashScreenActivity;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN = "LOGIN";
    private Button btnLogin;
    private EditText edt_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SplashScreenActivity.class);
                intent.putExtra(LOGIN, edt_pass.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
