package com.poster.danbilap.project_yeobo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daeun on 2016-11-16.
 */
public class TInsideFragment2 extends Fragment {


    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_TRAVEL = "travel_number";
    private static final String TAG_CITY = "c_num";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_PICTURE = "picture_name";
    private static final String TAG_GOOD = "good";


    ArrayList<HashMap<String, String>> reviewList;

    ListView list;

    ImageView iv;

    static ArrayList<String> titleList = new ArrayList<String>();
    static ArrayList<String> contentList = new ArrayList<String>();
    //  static ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    static ArrayList<String> nameList = new ArrayList<String>();
    static ArrayList<String> goodList = new ArrayList<String>();


    JSONArray review = null;

    int t_num;
    int c_num;

    Bitmap bitmap;

    public interface myFragListener2{
        public int get3();
        public int get4();
    }

    myFragListener2 ac;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof myFragListener2){
            ac =(myFragListener2)activity;
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("start", 1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child2, container, false);


        t_num=ac.get3();
        c_num=ac.get4();

        list = (ListView) rootView.findViewById(R.id.listView);

        reviewList = new ArrayList<HashMap<String, String>>();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  insertToDatabase(titleList.get(position), contentList.get(position), nameList.get(position));
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
            // getData("http://203.252.182.94//load2.php");

        } else {
//            Toast.makeText(getContext(), "second", Toast.LENGTH_SHORT).show();
//            CustomAdapter m_adapter = new CustomAdapter(getContext(), R.layout.custom_list, titleList, contentList, bitmapList, nameList);
//            list.setAdapter(m_adapter);

        }

        titleList = new ArrayList<String>();
        contentList = new ArrayList<String>();
        nameList = new ArrayList<String>();
        goodList=new ArrayList<String>();

        getData("http://203.252.182.94//load.php");
        getData("http://203.252.182.94//load3.php");

        CustomAdapter m_adapter = new CustomAdapter(getContext(), R.layout.custom_list, titleList, contentList, nameList,goodList);
        list.setAdapter(m_adapter);
        m_adapter.notifyDataSetChanged();

        return rootView;
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            review = jsonObj.getJSONArray(TAG_RESULTS);



            for (int i = 0; i < review.length(); i++) {
                JSONObject c = review.getJSONObject(i);

                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_CONTENT);
                String picture_name = c.getString(TAG_PICTURE);
                String good = c.getString(TAG_GOOD);

                    titleList.add(title);
                    contentList.add(content);
                    nameList.add(picture_name);
                    goodList.add(good);


            }


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
                    //   Toast.makeText(getApplicationContext(),uri,Toast.LENGTH_SHORT).show();
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode(TAG_TRAVEL, "UTF-8") + "=" + URLEncoder.encode(String.valueOf(t_num), "UTF-8");


                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                    wr.write( data );
                    wr.flush();
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
                //  Toast.makeText(getActivity(),"gO",Toast.LENGTH_SHORT).show();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }



}


