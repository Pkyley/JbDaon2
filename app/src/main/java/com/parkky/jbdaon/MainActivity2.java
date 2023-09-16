package com.parkky.jbdaon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView textView2;
    ViewPager pager;
    MapsFragment mapFragment = new MapsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView2 = findViewById(R.id.textView2);
        textView2.setText("This is Map Under !!!! ");



        double Lat_N = 37.556;
        double Lng_N = 126.97;
        String Lat_S = Double.toString(Lat_N);

        //Toast.makeText(this,Lat_S,Toast.LENGTH_SHORT).show();

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