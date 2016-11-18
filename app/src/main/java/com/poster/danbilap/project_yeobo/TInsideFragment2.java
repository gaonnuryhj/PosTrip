package com.poster.danbilap.project_yeobo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.net.HttpURLConnection;
import java.net.URL;
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


    ArrayList<HashMap<String, String>> reviewList;

    ListView list;

    ImageView iv;

    static ArrayList<String> titleList = new ArrayList<String>();
    static ArrayList<String> contentList = new ArrayList<String>();
    //  static ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    static ArrayList<String> nameList = new ArrayList<String>();


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


        if (savedInstanceState == null) {
            // getData("http://203.252.182.94//load2.php");

        } else {
//            Toast.makeText(getContext(), "second", Toast.LENGTH_SHORT).show();
//            CustomAdapter m_adapter = new CustomAdapter(getContext(), R.layout.custom_list, titleList, contentList, bitmapList, nameList);
//            list.setAdapter(m_adapter);

        }

        getData("http://203.252.182.94//load.php");
        return rootView;
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            review = jsonObj.getJSONArray(TAG_RESULTS);


            Bitmap bitmap;

            titleList = new ArrayList<String>();
            contentList = new ArrayList<String>();
            nameList = new ArrayList<String>();

            for (int i = 0; i < review.length(); i++) {
                JSONObject c = review.getJSONObject(i);

                int travel = c.getInt(TAG_TRAVEL);
                int city = c.getInt(TAG_CITY);
                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_CONTENT);
                String picture_name = c.getString(TAG_PICTURE);

                if(t_num==travel)
                {
                    titleList.add(title);
                    contentList.add(content);
                    nameList.add(picture_name);
                }
                // bitmap = new ImageRoader().getBitmapImg(picture_name);
                // bitmapList.add(bitmap);
                // loading(picture_name);
            }

//            Toast.makeText(getContext(),"TES3",Toast.LENGTH_SHORT).show();
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
                    //   Toast.makeText(getApplicationContext(),uri,Toast.LENGTH_SHORT).show();
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
                //  Toast.makeText(getActivity(),"gO",Toast.LENGTH_SHORT).show();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }



}


