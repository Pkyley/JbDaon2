package com.parkky.jbdaon.Info;

import static com.parkky.jbdaon.BaseConfig.Print_Log;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parkky.jbdaon.DataJson.InfoListJson2;
import com.parkky.jbdaon.R;

import java.security.Security;
import java.util.ArrayList;

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.ViewHolder> {

    private static final String TAG = "InfoListAdapter";
    ArrayList<InfoListJson2> items = new ArrayList<>();
    public static Context context;

    public InfoListAdapter(Context context) { this.context = context; }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_info_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        InfoListJson2 item = items.get(position);
        viewHolder.setItem(item);
        //String PSec_Key = SaveSharedPreference.getAttribute(context, "PSec_Key");
        viewHolder.view_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), CarView.class);
                Intent intent = new Intent(context, InfoView.class);
                intent.addFlags(intent.FLAG_ACTIVITY_SINGLE_TOP); // activity 중복생성 방지를 위해서.
                //intent.putExtra("id", Security.decrypt(item.id, PSec_Key));
                intent.putExtra("id", item.id);
                //v.getContext().startActivity(intent);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return items.size();
    }
    public void addItem(InfoListJson2 item) {
        items.add(item);
    }
    public void setItems(ArrayList<InfoListJson2> items) {
        this.items = items;
    }
    public InfoListJson2 getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView view_01;
        TextView view_02;
        TextView view_03;

//        String PSec_Key = SaveSharedPreference.getAttribute(context, "PSec_Key");
        public ViewHolder(View view) {
            super(view);
            view_01 = view.findViewById(R.id.list_text_1);
            view_02 = view.findViewById(R.id.list_text_2);
            view_03 = view.findViewById(R.id.list_text_3);
        }

        public void setItem(InfoListJson2 item) {
            //String date_view = null;
            //String date_set = item.wdate;
            String date_view_y = item.wdate.substring(2, 4);
            String date_view_m = item.wdate.substring(4, 6);
            String date_view_d = item.wdate.substring(6, 8);
            String data_view_set = date_view_y + "-" + date_view_m + "-" + date_view_d;
            //Print_Log(TAG, "date_view_L : " + date_set.length());
            //String date_view = Security.decrypt(item.lastdrvdt, PSec_Key);
            //if(date_view == null || date_view.equals("")) {
            //    date_view_set = "";
            //} else {
            //    date_view_set = date_view.substring(2, 10);
            //}
            //Print_Log (TAG, "data_view" + date_view);
            //Print_Log(TAG, "date_view_set : " + date_view_y);
            Print_Log(TAG, "date_view_set_M : " + data_view_set);
            //view_01.setText(Security.decrypt(item.carno, PSec_Key));
            view_01.setText(item.title);
            view_02.setText(item.wname);
            view_03.setText(data_view_set);

        }
    }


}
