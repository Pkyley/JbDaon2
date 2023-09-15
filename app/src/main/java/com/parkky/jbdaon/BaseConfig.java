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
//    public static String Car_Login_Url = "http://192.168.100.113/Web_Service_Test/Car_login_check.php";
//    public static String Car_ReLogin_Url = "http://192.168.100.113/Web_Service_Test/Car_relogin_check.php";
//    public static String Oil_ListUrl = "http://192.168.100.113/Web_Service_Test/Car_Oil_List.php";
//    public static String Car_WorkOn_listUrl = "http://192.168.100.113/Web_Service_Test/Car_WorkOn_List.php";
//    public static String Car_WorkOn_execUrl = "http://192.168.100.113/Web_Service_Test/Car_WorkOn_check.php";

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
