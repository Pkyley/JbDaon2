package com.parkky.jbdaon.Info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parkky.jbdaon.BaseConfig;
import com.parkky.jbdaon.DataJson.InfoListJson;
import com.parkky.jbdaon.DataJson.InfoListJson2;
import com.parkky.jbdaon.R;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;

public class InfoList extends BaseConfig {

    private static final String TAG = "InfoList";
    RecyclerView recyclerView;
    InfoListAdapter infoListAdapter;
    static RequestQueue requestQueue;

    int start = 0;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_info_list);

        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);

        Print_Log(TAG, "OnCreate InfoList Start");

        recyclerView = findViewById(R.id.recyclerview);

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemPositionCount = recyclerView.getAdapter().getItemCount()-1;
                Print_Log(TAG, "lastVisibleItemPosition = " + lastVisibleItemPosition);
                Print_Log(TAG, "Scroll Position : Last = " + itemPositionCount);

                if(lastVisibleItemPosition == itemPositionCount) {
                    Print_Log(TAG, "Scroll Position : Last = " + itemPositionCount);
                    makeRequest("", recyclerView.getAdapter().getItemCount());
                }
                Print_Log(TAG, "addOnScrollListener Position : End = ");
            }
        });

        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        Print_Log(TAG, "New infoListAdapter Start !!! ");

    }


    protected void onStart(){
        super.onStart();
        Print_Log(TAG, " onStart Start !!! ");
    }

    protected void onPause(){
        super.onPause();
        Print_Log(TAG, " onPause Pause !!! ");
    }

    protected void onStop(){
        super.onStop();
        Print_Log(TAG, " onStop Stpp !!! ");
    }

    protected void onRestart(){
        super.onRestart();
        Print_Log(TAG, " onRestart Restart !!! ");
    }

    protected void onResume() {
        super.onResume();
        Print_Log(TAG, " onResume onResume !!! ");

        Intent intent = getIntent();
        //search_word = intent.getStringExtra("search_word");
        start = intent.getIntExtra("start", 0);

        int Check_itemTotalCount = recyclerView.getAdapter().getItemCount();
        Print_Log(TAG, "Check_itemTotalCount = " + Check_itemTotalCount);
        if(Check_itemTotalCount == 0) {
// 검색을 할 수 있는 환경이 된다면, 여기를 살린다.
//            if (search_word == null) {
                Print_Log(TAG, "Send makeRequest Start ");
                makeRequest("", start);
//            } else {
//                Print_Log(TAG, "Send makeRequest Search_Wrod Start ");
//                searchText.setText(search_word);
//                makeRequest(search_word, start);
//            }
        }


    }


    public void makeRequest(String s_word, int start) {
        String Url = Info_ListUrl;
        Print_Log(TAG, "Url : " + Url);
//        String UserID = SaveSharedPreference.getAttribute(this, "UserID");
//        String PSec_Key = SaveSharedPreference.getAttribute(this, "PSec_Key");
//        String p_number = SaveSharedPreference.getAttribute(this, "p_number");
//        String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        Print_Log(TAG, "Send Request Start ");
        StringRequest request = new StringRequest(
                Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Print_Log(TAG, "processResponse : 호출됨");
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Print_Log(TAG, "Response Error" + error.getMessage());
                    }
                }
        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("App_id", Security.encrypt(android_id, BaseConfig.OneSec_Key));
//                params.put("PhoneNum", Security.encrypt(p_number, BaseConfig.OneSec_Key));
//                params.put("User_ID", Security.encrypt(UserID, PSec_Key));
//                params.put("search", s_word);
                params.put("start",  String.valueOf(start));
                Print_Log(TAG, " Send Url and params : " + params);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
        Print_Log(TAG, "Send Request End ");

    }

    @SuppressLint("NotifyDataSetChanged")
    public void processResponse(String response) {
        Print_Log(TAG, "processResponse Start ");
        Gson gson = new Gson();
        InfoListJson infoListJson = gson.fromJson(response, InfoListJson.class);
        if(infoListJson.Result.equals("Success")) {
            Print_Log(TAG, "Result: " + infoListJson.Result);
            Print_Log(TAG, "Total_Num: " + infoListJson.Total_Num);
            Print_Log(TAG, "Data Size: " + infoListJson.data.size());

            for (int i = 0 ; i < infoListJson.data.size(); i++) {
                InfoListJson2 infoListJson2 = infoListJson.data.get(i);
                Print_Log("List", "list_json2- I : " + infoListJson2);
                infoListAdapter.addItem(infoListJson2);
            }
            Print_Log(TAG, "processResponse: Send 1");
            infoListAdapter.notifyDataSetChanged();
            Print_Log(TAG, "notifyDataSetChanged: Send 2");
        } else {
            Print_Log(TAG, "Result: " + infoListJson.Result);
            Toast.makeText(this, "검색결과가 없습니다. 다시 검새해주세요.", Toast.LENGTH_LONG).show();
            finish();
        }
        Print_Log(TAG, "processResponse End ");
    }


}
