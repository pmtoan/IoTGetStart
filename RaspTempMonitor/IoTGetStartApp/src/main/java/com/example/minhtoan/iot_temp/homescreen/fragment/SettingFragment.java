package com.example.minhtoan.iot_temp.homescreen.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhtoan.iot_temp.R;
import com.example.minhtoan.iot_temp.cache.StoreManager;

/**
 * Created by user on 12/31/15.
 */
public class SettingFragment extends Fragment {

    private EditText oldPassword, newPassword, conformPassword;
    private View btnChange;
    private TextView tvError;
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_setting_layout, container, false);
        setupView();
        return myView;
    }

    private void setupView() {

        oldPassword = myView.findViewById(R.id.oldPassword);
        newPassword = myView.findViewById(R.id.newPassword);
        conformPassword = myView.findViewById(R.id.conformPassword);
        btnChange = myView.findViewById(R.id.btnChange);
        tvError = myView.findViewById(R.id.tvError);


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setVisibility(View.GONE);
                if (!oldPassword.getText().toString().equals(StoreManager.getPass(getActivity()))) {
                    tvError.setText(getResources().getString(R.string.error_incorrect));
                    tvError.setVisibility(View.VISIBLE);
                } else if (!newPassword.getText().toString().equals(conformPassword.getText().toString())) {
                    tvError.setText(getResources().getString(R.string.same_pass));
                    tvError.setVisibility(View.VISIBLE);
                } else {
                    updatePassword(newPassword.getText().toString());
                    Toast.makeText(getActivity(), "Changed Password", Toast.LENGTH_SHORT).show();
                }

            }

            private void updatePassword(String s) {
                StoreManager.savePass(getActivity(), s);
            }
        });

    }
}
