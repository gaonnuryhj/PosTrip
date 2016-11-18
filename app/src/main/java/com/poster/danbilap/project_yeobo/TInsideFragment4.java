package com.poster.danbilap.project_yeobo;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.danbilap.project_yeobo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daeun on 2016-11-16.
 */
public class TInsideFragment4 extends Fragment {


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

    public interface myFragListener4{
        public int get7();
        public int get8();
    }

    myFragListener4 ac;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof myFragListener4){
            ac =(myFragListener4)activity;
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("start", 1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child4, container, false);


        t_num=ac.get7();
        c_num=ac.get8();

        list = (ListView) rootView.findViewById(R.id.listView);

        reviewList = new ArrayList<HashMap<String, String>>();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                insertToDatabase(titleList.get(position), contentList.get(position), nameList.get(position));
            }
        });


        if (savedInstanceState == null) {
            // getData("http://203.252.182.94//load2.php");

        } else {
//            Toast.makeText(getContext(), "second", Toast.LENGTH_SHORT).show();
//            CustomAdapter m_adapter = new CustomAdapter(getContext(), R.layout.custom_list, titleList, contentList, bitmapList, nameList);
//            list.setAdapter(m_adapter);

        }

        getData("http://203.252.182.94//load2.php");
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

                if(c_num==city)
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

    private void insertToDatabase(String title, String content, String name){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(),"Share",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{

                    String title = (String)params[0];
                    String content = (String)params[1];
                    String name = (String)params[2];

                    String link="http://203.252.182.94/insert2.php";
                    String data  = URLEncoder.encode(TAG_TRAVEL, "UTF-8") + "=" + URLEncoder.encode(String.valueOf(t_num), "UTF-8");
                    data += "&" + URLEncoder.encode(TAG_CITY, "UTF-8") + "=" + URLEncoder.encode(String.valueOf(c_num), "UTF-8");
                    data += "&" + URLEncoder.encode(TAG_TITLE, "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
                    data += "&" + URLEncoder.encode(TAG_CONTENT, "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
                    data += "&" + URLEncoder.encode(TAG_PICTURE, "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception2: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(title,content,name);
    }


}


