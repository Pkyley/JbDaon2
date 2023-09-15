package com.parkky.jbdaon.Info;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parkky.jbdaon.BaseConfig;
import com.parkky.jbdaon.DataJson.InfoViewJson;
import com.parkky.jbdaon.R;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;

public class InfoView extends BaseConfig {
    private static final String TAG = "InfoView";

    TextView view_text_title, view_text_etc, view_text_content;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);

        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);


        view_text_title = findViewById(R.id.view_text_title);
        view_text_etc = findViewById(R.id.view_text_etc);
        view_text_content = findViewById(R.id.view_text_content);

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        makeRequest(id);

        Button button_close = findViewById(R.id.view_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

    }

    private void makeRequest(String id) {
        String Url = Info_ViewUrl;
        Print_Log(TAG, "Start Info_ViewUrl = " + Info_ViewUrl);
        Print_Log(TAG, "Start View id = " + id);

        StringRequest request = new StringRequest(
                Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                params.put("id", id);
                //params.put("id", id);
                Print_Log(TAG, " params : " + params);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
        Print_Log(TAG, "Send Request");

    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        InfoViewJson infoViewJson = gson.fromJson(response, InfoViewJson.class);
        Print_Log(TAG, "json_Result = " + infoViewJson.Result);
        Print_Log(TAG, "json_id = " + infoViewJson.id);

        view_text_title.setText(infoViewJson.title);
        view_text_etc.setText(infoViewJson.wdate);
        //textView.setText(Html.fromHtml(htmlText));
        //txt.setMovementMethod(new ScrollingMovementMethod());
        view_text_content.setText(Html.fromHtml(infoViewJson.content));
        view_text_content.setMovementMethod(new ScrollingMovementMethod());

    }


}
