package com.poster.danbilap.project_yeobo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danbilap.project_yeobo.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // 해당 여행 나라의 국기 이미지
    ArrayList<Travel> t_arr;
    int[] background = { R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.m,R.drawable.n,R.drawable.o,R.drawable.p,R.drawable.q,R.drawable.q};


    String u_id;
    String url;

    GridView gridView1;
    GridAdapter gridAdapter;
    String imageUrl;
    String test[];
    ShortenUrlGoogle shorten = new ShortenUrlGoogle();
    String short_url;
    String sharedDescription;
    String sharedTitle;
    int travel_number,c_num;
    int num;
    private BackPressCloseSystem backPressCloseSystem;
    private SwipeRefreshLayout mSwipeRefresh;

    Button close_Btn;

    LinearLayout pop_linear;
    LinearLayout login_Linear;
    View pop_View;

    PopupWindow popupWindow;
    EditText ed;
    int check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        u_id = i.getStringExtra("id");
        show_travel(u_id);
        url = i.getStringExtra("url");
        short_url = shorten.getShortenUrl(url);
        num=0;
        backPressCloseSystem = new BackPressCloseSystem(this);

        check=0;

        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //은슬
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //은슬

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("id", u_id);
                startActivity(intent);
            }
        });


        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

// resource id로 색상을 변경하려면 setColorSchemeResources() 사용
        mSwipeRefresh.setColorSchemeResources(R.color.main);
// Color 객체는 setColorSchemeColors(...)를 사용
//mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.BLUE);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefresh.setRefreshing(false);
            }
        });

    }

    void init() {
        //      i_arr=new ArrayList<ImageUrl>();
        t_arr = new ArrayList<Travel>();

        gridView1 = (GridView) findViewById(R.id.gridView1);




        gridAdapter = new GridAdapter(this, R.layout.gridview1_item, background, t_arr);
        // 커스텀 어댑터를 GridView 에 적용
        gridView1.setAdapter(gridAdapter);
        // 클릭하면 뷰페이저로 넘어감
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Travel t = t_arr.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("t_title", t.getT_title());
                bundle.putInt("t_num", t.getT_id());
                bundle.putString("u_id", t.getU_id());
                bundle.putString("url", t.getUrl());
                bundle.putInt("c_num",t.getC_num());
                //               bundle.putString("imgurl", t.getImgurl());
                travel_number = t.getT_id();
                c_num=t.getC_num();
                if(url!=null){

                    Resources lang_res = getResources();
                    DisplayMetrics lang_dm = lang_res.getDisplayMetrics();
                    int lang_width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, lang_dm);
                    int lang_height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, lang_dm);
                    login_Linear = (LinearLayout) findViewById(R.id.popup_linear);
                    pop_View = View.inflate(MainActivity.this, R.layout.popup_layout, null);
                    if(check==0){
                    popupWindow = new PopupWindow(pop_View, lang_width, lang_height, true);
                    ed=(EditText)pop_View.findViewById(R.id.title);

                    popupWindow.showAtLocation(login_Linear, Gravity.CENTER, 0, 0);}


                    // 팝업 닫기 버튼
                    close_Btn = (Button) pop_View.findViewById(R.id.close_btn);

                    close_Btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            check=1;
                            sharedTitle=ed.getText().toString();
                            ShareTask shareTask = new ShareTask();
                            shareTask.execute(url);
                            popupWindow.dismiss();
                          // 팝업 닫기
                        }

                    });
                    if(check==1){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);}
                }


            }
        });

        gridView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        Travel t = t_arr.get(position);
                                        int t_num = t.getT_id();
                                        delete(t_num);
                                        t_arr.remove(position);
                                        gridAdapter.notifyDataSetChanged();


                                    }
                                })
                        .setNegativeButton("cancle",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                return false;
            }
        });


    }

    void delete(final int t_num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeoboH.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.delete(9, t_num, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("test", error.toString());
                    }
                });
            }
        }).start();
    }

    void show_travel(final String u_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeoboH.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.show_travel(6, u_id, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        JsonArray result = jsonObject.getAsJsonArray("result");

                        for (int i = 0; i < result.size(); i++) {
                            JsonObject obj = (JsonObject) result.get(i);
                            int t_num = obj.get("travel_number").getAsInt();
                            String t_city = obj.get("travel_city").getAsString();
                            String t_start = obj.get("travel_start").getAsString();
                            String t_finish = obj.get("travel_finish").getAsString();
                            String t_title = obj.get("travel_title").getAsString();
                            int c_num=obj.get("city_num").getAsInt();
                            // 국기 이미지 가져와야함
                            Travel t = new Travel(t_num, t_city,t_title, t_start, t_finish, u_id, short_url,c_num);


                            t_arr.add(t);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("test", error.toString());
                    }
                });
            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            /*super.onBackPressed();
            backPressCloseSystem.onBackPressed();*/
        }
        if(num==1){

            super.onBackPressed();
            backPressCloseSystem.onBackPressed();

        }

        if(num==0){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_r,
                    (ViewGroup) findViewById(R.id.toast_layout));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Press the Back button again to exit.");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

        num=1;



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }else if(id==R.id.nav_send){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("set","yes");
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class ShareTask extends AsyncTask<String, Void, String> { //공유하기를 눌렀을 때 실제로 사용자에게 보여주는 데이터를 처리하는 부분.
        //태그의 파싱이 필요하다.
        HttpURLConnection conn = null;

        @Override
        protected String doInBackground(String... urls) {

            String page = "";
            try {
                URL url = new URL(urls[0]);

                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());//바이트 단위로 데이터 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(buf, "utf-8")); //텍스트 형태로 데이터 읽어들임.
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    page += line;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
                return page;
            }


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Document doc = (Document) Jsoup.parse(result);
            // Elements elements = doc.select("meta");
            Elements elements = (Elements) doc.getElementsByTag("meta");

            String imageUrl = null;
            for (int i = 0; i < elements.size(); i++) {
                String property = elements.get(i).attr("property");

                if (property.equals("og:image")) {
                    if(imageUrl==null){ //이미지가 아무것도 들어오지 않은 상태일때만 실행.(이미지가 여러개 들어오는 경우 위해)
                        imageUrl = elements.get(i).attr("content");
                        Toast.makeText(getApplicationContext(),imageUrl,Toast.LENGTH_LONG).show();
                    }
                }
                if (property.equals("og:description")) {
                    sharedDescription = elements.get(i).attr("content");
                }




            }



            share_write(travel_number,url,imageUrl,sharedDescription,sharedTitle,c_num);


        }//onPostExecute 함수


    }

    //DB에 값 저장.
    void share_write(final int travel_number, final String share_Url, final String share_ImageUrl, final String share_description, final String share_title,final int c_num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeoboH.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                int a=travel_number;
                String b=share_Url;
                String c=share_ImageUrl;
                String d="";
                if(share_description!=null && share_description.length()>100){d=share_description.substring(100);}

                String e=share_title;
                int f=c_num;
                retrofit.share_write(0,a,b,c,d,e,f,0,new Callback<JsonObject>(){
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        JsonArray result = jsonObject.getAsJsonArray("result");
                        String errorCode = ((JsonObject)result.get(0)).get("errorCode").getAsString();
                        if(errorCode.equals("success")){
                           // Toast.makeText(MainActivity.this,errorCode, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("test", error.toString());
                    }
                });
            }
        }).start();



    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


}









