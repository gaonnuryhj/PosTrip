package com.poster.danbilap.project_yeobo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TestViewPagerAdapter2 extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragmentArrayList;

    public TestViewPagerAdapter2(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "Url & Memo";  // 후기
            case 1 :
                return "Review";  // 멘토 리스트
        }
        return "position "+position;
    }

    @Override
    public Fragment getItem(int position) {
        // 해당하는 page의 Fragment를 생성합니다.
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

}