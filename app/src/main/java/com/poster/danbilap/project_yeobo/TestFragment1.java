package com.poster.danbilap.project_yeobo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.danbilap.project_yeobo.R;

import java.util.ArrayList;

public class TestFragment1 extends Fragment {

    int t_number;
    int c_number;
    int travel_number = 3;
    int c_num = 0;


    public TestFragment1() {
        // Required empty public constructor
    }


    public static TestFragment1 newInstance(int t_number, int c_num) {
        TestFragment1 instance = new TestFragment1();
        instance.t_number = t_number;
        instance.c_number = c_num;
        return instance;
    }


    private Button btnFirstGallery;
    private Button btnSecondGallery;

    private ViewPager pager;

    private Fragment galleryFragment1;
    private Fragment galleryFragment2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_1, container, false);

        travel_number = t_number;
        c_num = c_number;
        galleryFragment1 = new TInsideFragment1();
        galleryFragment2 = new TInsideFragment2();

        btnFirstGallery = (Button) view.findViewById(R.id.btnFirstGallery);
        btnFirstGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
              //  btnFirstGallery.setBackgroundColor(Color.parseColor("#0d95e9"));
            }
        });
        btnSecondGallery = (Button) view.findViewById(R.id.btnSecondGallery);
        btnSecondGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });

        // View Pager를 선언합니다.
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        // 처음으로 0번째 Fragment를 보여줍니다.
        pager.setCurrentItem(0);
        btnSecondGallery.setBackgroundColor(Color.parseColor("#d7d7d7"));
        btnSecondGallery.setTextColor(Color.parseColor("#ffffff"));
        btnFirstGallery.setBackgroundColor(Color.parseColor("#ffffff"));

        // Title을 설정합니다.
        getActivity().setTitle("Gallery Fragment");

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    btnSecondGallery.setBackgroundColor(Color.parseColor("#d7d7d7"));
                    btnSecondGallery.setTextColor(Color.parseColor("#ffffff"));
                    btnFirstGallery.setBackgroundColor(Color.parseColor("#ffffff"));
                    btnFirstGallery.setTextColor(Color.parseColor("#0d95e9"));
                }
                else{
                    btnFirstGallery.setBackgroundColor(Color.parseColor("#d7d7d7"));
                    btnFirstGallery.setTextColor(Color.parseColor("#ffffff"));
                    btnSecondGallery.setBackgroundColor(Color.parseColor("#ffffff"));
                    btnSecondGallery.setTextColor(Color.parseColor("#0d95e9"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // Inflate the layout for this fragment
        return view;

    }



    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return galleryFragment1;
            } else {
                return galleryFragment2;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }





}