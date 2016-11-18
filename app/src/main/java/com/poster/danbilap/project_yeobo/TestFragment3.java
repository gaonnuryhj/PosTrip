package com.poster.danbilap.project_yeobo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.danbilap.project_yeobo.R;

public class TestFragment3 extends Fragment {
    int travel_number;
    int t_number;
    int c_number;
    int c_num;

    private Button btnThirdGallery;
    private Button btnForthGallery;

    private ViewPager pager;

    private Fragment galleryFragment3;
    private Fragment galleryFragment4;



    public static TestFragment3 newInstance(int c_num) {
        TestFragment3 instance = new TestFragment3();
        instance.c_number = c_num;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_3, container, false);

        c_num = c_number;
        galleryFragment3 = new TInsideFragment3();
        galleryFragment4 = new TInsideFragment4();

        btnThirdGallery = (Button) view.findViewById(R.id.btnFirst3);
        btnThirdGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        btnForthGallery = (Button) view.findViewById(R.id.btnSecond3);
        btnForthGallery.setOnClickListener(new View.OnClickListener() {
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

        // Title을 설정합니다.
        getActivity().setTitle("Other Fragment");

        // Inflate the layout for this fragment
        return view;
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * View Pager의 Fragment 들은 각각 Index를 가진다.
         * Android OS로 부터 요청된 Pager의 Index를 보내주면,
         * 해당되는 Fragment를 리턴시킨다.
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return galleryFragment3;
            } else {
                return galleryFragment4;
            }
        }

        /**
         * View Pager에 몇개의 Fragment가 들어가는지 설정
         *
         * @return
         */
        @Override
        public int getCount() {
            return 2;
        }
    }
}


