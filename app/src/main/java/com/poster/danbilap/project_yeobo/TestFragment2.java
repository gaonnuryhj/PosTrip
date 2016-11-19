package com.poster.danbilap.project_yeobo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.danbilap.project_yeobo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class TestFragment2 extends Fragment {

    String myJSON;

    private static final String TAG_RESULTS = "result";

    private static final String TAG_TRAVEL = "travel_number";
    private static final String TAG_CITY = "c_num";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_PICTURE = "picture_name";


    ArrayList<HashMap<String, String>> reviewList;

    ListView list;

    ImageView iv;

    static ArrayList<String> titleList = new ArrayList<String>();
    static ArrayList<String> contentList = new ArrayList<String>();
    static ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    static ArrayList<String> nameList = new ArrayList<String>();


    JSONArray review = null;

    int t_num;
    int c_num;

    Bitmap bitmap;
    //loading task;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("start", 1);
    }


    public static TestFragment2 newInstance(int t_num, int c_num) {
        TestFragment2 instance = new TestFragment2();
        instance.t_num = t_num;
        instance.c_num = c_num;

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test_2, container, false);

        list = (ListView) rootView.findViewById(R.id.listView);

        reviewList = new ArrayList<HashMap<String, String>>();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> urlInfo = new HashMap<String, String>();


            }
        });


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WriteActivity.class);

                intent.putExtra(TAG_TRAVEL, t_num);
                intent.putExtra(TAG_CITY, c_num);

                startActivity(intent);
            }
        });


        if (savedInstanceState == null) {

        } else {
            titleList = new ArrayList<String>();
            contentList = new ArrayList<String>();
            bitmapList = new ArrayList<Bitmap>();
            nameList = new ArrayList<String>();

        }


        init();
        return rootView;
    }

    public void init() {
        getData("http://203.252.182.94//load2.php");
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            review = jsonObj.getJSONArray(TAG_RESULTS);


            titleList = new ArrayList<String>();
            contentList = new ArrayList<String>();
            bitmapList = new ArrayList<Bitmap>();
            nameList = new ArrayList<String>();

            for (int i = 0; i < review.length(); i++) {
                JSONObject c = review.getJSONObject(i);

                int travel = c.getInt(TAG_TRAVEL);
                int city = c.getInt(TAG_CITY);
                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_CONTENT);
                String picture_name = c.getString(TAG_PICTURE);

                if (t_num == travel) {
                    titleList.add(title);
                    contentList.add(content);

                    nameList.add(picture_name);

                }

            }


            CustomAdapter m_adapter = new CustomAdapter(getContext(), R.layout.custom_list, titleList, contentList, nameList);
            list.setAdapter(m_adapter);
            m_adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void loading(String img) {
        final String serverUrl = "http://203.252.182.94/uploads/";
        class GetData extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmapImg = null;

                try {
                    URL url = new URL(serverUrl +
                            URLEncoder.encode(params[0], "utf-8"));
                    // Character is converted to 'UTF-8' to prevent broken

                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmapImg = BitmapFactory.decodeStream(is);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                return bitmapImg;
            }
            protected void onPostExecute(Bitmap img) {
                bitmap = img;
                bitmapList.add(bitmap);
            }
        }


        class loading extends AsyncTask<String, Integer, Bitmap> {
            Bitmap tmp_bitmap;

            @Override
            protected Bitmap doInBackground(String... urls) {
                // TODO Auto-generated method stub
                try {
                    URL myFileUrl = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();

                    tmp_bitmap = BitmapFactory.decodeStream(is);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tmp_bitmap;
            }

            protected void onPostExecute(Bitmap img) {
                bitmap = img;
                bitmapList.add(bitmap);

            }

        }


    }

}

