package com.parkky.jbdaon;

import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BaseConfig extends AppCompatActivity {


    // 동작 테스트를 위한 주소 구성
    //public static String Server_Url = "http://cug.knowhow.or.kr/jjokji/";
    // 실제 운영을 위한 주소 구성
    public static String Server_Url = "http://www.jbdaon.com/main/";

    public static String Info_ListUrl = Server_Url + "info_list_json.php";
    public static String Info_ViewUrl = Server_Url + "info_view_json.php";


    private static Handler handler = new Handler();
    public static void Print_Log(String TAG, String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, data);
            }
        });
    }


}
