package com.example.minhtoan.iot_temp.homescreen;

/**
 * Created by trungtien on 10/1/17.
 */

public class Manager {
    public static final String SAVE_PASS = "SAVE_PASS";
    public static final String SAVE_HOST = "SAVE_HOST";

    public static final String POST_URL = "http://10.0.143.76/register.php";
    public static final String URL_TEST = "http://10.0.143.76/tempread.php";

    public static String getURL() {
        return POST_URL;
    }
}
