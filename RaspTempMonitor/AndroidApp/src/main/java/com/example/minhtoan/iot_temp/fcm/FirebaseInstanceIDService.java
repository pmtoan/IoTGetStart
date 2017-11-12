
package com.example.minhtoan.iot_temp.fcm;

import android.util.Log;

import com.example.minhtoan.iot_temp.homescreen.Manager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by trungtien on 9/29/17.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = FirebaseInstanceIDService.class.getSimpleName();

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        onTokenRefresh();
//    }

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null)
            registerToken(token);
    }

    private void registerToken(final String token) {

        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        final Request request = new Request.Builder()
                .url(Manager.getURL())
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    client.newCall(request).execute();
                    Log.e(TAG, "Refreshed token: " + token);

                }
            }
        });
    }
}
