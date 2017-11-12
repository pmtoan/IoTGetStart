package com.example.minhtoan.iot_temp.cache;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.minhtoan.iot_temp.homescreen.Manager;

/**
 * Created by trungtien on 10/2/17.
 */

public class StoreManager {
    public static SharedPreferences sharedPref;
    public static void savePass(Activity activity, String data) {
        if (sharedPref == null)
            sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Manager.SAVE_PASS, data);
        editor.commit();
    }

    public static String getPass(Activity activity) {
        if (sharedPref == null)
            sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(Manager.SAVE_PASS, "");
    }
}
