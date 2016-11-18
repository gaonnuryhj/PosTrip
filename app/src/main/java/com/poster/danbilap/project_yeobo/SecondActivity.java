package com.poster.danbilap.project_yeobo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.danbilap.project_yeobo.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TInsideFragment1.myFragListener,
        TInsideFragment2.myFragListener2,TInsideFragment3.myFragListener3,TInsideFragment4.myFragListener4 {

    String t_title, id, url, image;
    int t_num;
    TextView title;
    int tmp_cnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();


    }

    void init() {

        title = (TextView) findViewById(R.id.title);

        Bundle bundle = getIntent().getExtras();

        t_title = bundle.getString("t_title");
        title.setText(t_title);

        t_num = bundle.getInt("t_num");
        id = bundle.getString("u_id");
        url = bundle.getString("url");
        tmp_cnum = bundle.getInt("c_num");
        if (tmp_cnum >= 9)
            tmp_cnum = tmp_cnum + 22;
//        image=bundle.getString("imgurl");
        if (url != null) {
            // saveUrl(url,t_num);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //은슬
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //은슬
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(TestFragment1.newInstance(t_num, tmp_cnum));
        fragmentArrayList.add(TestFragment2.newInstance(t_num, tmp_cnum));
        fragmentArrayList.add(TestFragment3.newInstance(tmp_cnum));
        fragmentArrayList.add(TestFragment4.newInstance(tmp_cnum));


        TestViewPagerAdapter adapter = new TestViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        pager.setAdapter(adapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);


    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {
            Intent i = new Intent(SecondActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("set", "yes");
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() { // 뒤로 가기 했을 때 MainActivity로 돌아가도록
        //       super.onBackPressed();
//        SecondActivity.this.finish();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public int get1() {
        return t_num;
    }

    @Override
    public int get2() {
        return tmp_cnum;
    }

    @Override
    public int get3() {
        return t_num;
    }

    @Override
    public int get4() {
        return tmp_cnum;
    }

    @Override
    public int get5() {
        return t_num;
    }

    @Override
    public int get6() {
        return tmp_cnum;
    }

    @Override
    public int get7() {
        return t_num;
    }

    @Override
    public int get8() {
        return tmp_cnum;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


