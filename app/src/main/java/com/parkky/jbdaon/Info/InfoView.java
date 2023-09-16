package com.parkky.jbdaon.Info;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.parkky.jbdaon.MapsFragment;
import com.parkky.jbdaon.R;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoView extends BaseConfig {
    private static final String TAG = "InfoView";

    TextView view_text_title, view_text_etc, view_text_content, view_text_addr;
    ViewPager pager;
    MapsFragment mapFragment = new MapsFragment();

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
        view_text_addr = findViewById(R.id.view_text_addr);

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
        Print_Log(TAG, "htmlmode = " + infoViewJson.htmlmode);


        view_text_title.setText(infoViewJson.title);

        String wname = infoViewJson.wname;
        String date_view_y = infoViewJson.wdate.substring(0, 4);
        String date_view_m = infoViewJson.wdate.substring(4, 6);
        String date_view_d = infoViewJson.wdate.substring(6, 8);
        String date_view_h = infoViewJson.wdate.substring(8, 10);
        String date_view_mi = infoViewJson.wdate.substring(10, 12);
        String data_view_set = date_view_y + "-" + date_view_m + "-" + date_view_d + " " + date_view_h + ":" + date_view_mi;
        String v_etc = wname + " ("+data_view_set+")";
        view_text_etc.setText(v_etc);


        String htmlmode = infoViewJson.htmlmode;
        if(htmlmode.equals("Html")) {
            view_text_content.setText(Html.fromHtml(infoViewJson.content));
            view_text_content.setMovementMethod(LinkMovementMethod.getInstance());

            view_text_content.setMovementMethod(new ScrollingMovementMethod());
        } else {
            view_text_content.setText(infoViewJson.content);
        }

        String addr = infoViewJson.addr;
        if(TextUtils.isEmpty(addr)) {
            Print_Log(TAG, "address = " + infoViewJson.addr);
        } else {
            view_text_addr.setText(infoViewJson.addr);
            Print_Log(TAG, "address = " + infoViewJson.addr);

            // 여기서 지도를 호출 하도록 하자....

            String Lat_D = infoViewJson.lat;
            String Lng_D = infoViewJson.lng;

            double Lat_N = Double.parseDouble(Lat_D);
            double Lng_N = Double.parseDouble(Lng_D);

            //double Lat_N = 37.556;
            //double Lng_N = 126.97;
            //String Lat_S = Double.toString(Lat_N);

            Bundle bundle = new Bundle();
            bundle.putDouble("Lat_N", Lat_N);
            bundle.putDouble("Lng_N", Lng_N);

            mapFragment.setArguments(bundle);

            pager = findViewById(R.id.pager);
            pager.setOffscreenPageLimit(1);

            MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

            //Fragment1 fragment1 = new Fragment1();
            adapter.addItem(mapFragment);
            pager.setAdapter(adapter);

        }


    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item) {
            items.add(item);
        }
        public Fragment getItem(int position) {
            return items.get(position);
        }
        public int getCount() {
            return items.size();
        }
    }

}
