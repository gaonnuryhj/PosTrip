package com.poster.danbilap.project_yeobo;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.danbilap.project_yeobo.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Tour_info extends AppCompatActivity {
    String nation_url;
    //int ination_id=0;
    TextView tv;
    ImageView img;


    ArrayList<info> i_arr;
    ListView listView1;
    ListAdapter listAdapter;
    ProgressDialog m_pdlg;//다운중이면 계속 돌아가는것
    Context context;
    int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_info1);
        context=this;

        init();



    }

    void init() {
        //  tv=(TextView)findViewById(R.id.tv);

        //img=(ImageView)findViewById(R.id.img);

        //지역번호만 넘어오기
        Bundle bundle = getIntent().getExtras(); // intent로 페이지 넘어옴.
        nation_url = bundle.getString("nation_id");
        //tv.setText(nation_url);//test

        new DownloadWebPageTask().execute(nation_url);

        //ination_id = Integer.parseInt(nation_id);
        // tv.setText("" + ination_id);//test
        //지역번호 넘어오기

        i_arr = new ArrayList<info>();
        listView1 = (ListView) findViewById(R.id.listview1);
        listAdapter =new com.poster.danbilap.project_yeobo.ListAdapter(this,R.layout.listview1_item,i_arr);
        listView1.setAdapter(listAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                info t = i_arr.get(position);
                String title_s = t.getT_title();
                Uri uri = Uri.parse("https://www.google.com/search?q=" + title_s);
                Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gSearchIntent);


            }
        });

    }
    public class DownloadWebPageTask extends AsyncTask<String, Integer, String> {//원래는 integer아니고 void였음.데이터타입에따라

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            m_pdlg=new ProgressDialog(context);
            m_pdlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            m_pdlg.setMax(100);
            m_pdlg.setMessage("다운로드중입니다.");
            m_pdlg.show();
        }

        // onPreExecute() 실행 후에 백그라운드 쓰레드로 수행됨
        @Override
        protected String doInBackground(String... urls) {//백그라운드
            try{
                return (String)downloadUrl((String)urls[0]);
            }catch (IOException e){
                return "다운로드 실패";
                //e.printStackTrace();
            }
            //return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            m_pdlg.setProgress(values[0]);//처음값
        }

        @Override
        protected void onPostExecute(String result) {//작업이 끝난후

            String word ="";
            String word2 ="";
            String word3 ="";
            String word4 ="";

            int pt_start=-1;
            int pt_end=-1;
            int pt_start2=-1;
            int pt_end2=-1;
            int pt_start3=-1;
            int pt_end3=-1;
            int pt_start4=-1;
            int pt_end4=-1;


            int r_start=-1;
            int r_end=-1;

            int item_start=-1;
            int item_end=-1;

            int line1=0;

            String r_tag_start="<body>";
            String r_tag_end="</body>";

            String item_tag_start="<item>";

            r_start=result.indexOf(r_tag_start);//큰 tag안 시작
            r_end=result.indexOf(r_tag_end);//큰 tag안 끝

            item_start=result.indexOf(item_tag_start);//itemt시작


            // tv.append("  파싱결과 :" + r_end);
        /*    pt_end = result.indexOf(tag_end, 0);//작은 tag안 끝

            tv.append("  파싱결과 :" + pt_end);
            pt_end = result.indexOf(tag_end, 746);//작은 tag안 끝
            tv.append("  파싱결과 :" + pt_end);*/

//<addr1>(2)  <firstimage2>(4)  <tel>(2)  <title>(2)
            while(line1<r_end) {
                String item_tag_end="</item>";
                item_end=result.indexOf(item_tag_end,line1);//item 끝

                //[1.주소]
                String tag_start="<addr1>";
                String tag_end="</addr1>";

                pt_start = result.indexOf(tag_start, line1);//작은 tag안 시작
                pt_end = result.indexOf(tag_end, line1);//작은 tag안 끝
                if(pt_start==-1)
                    break;


                word = result.substring(pt_start + tag_start.length(), pt_end);
                if(item_end<pt_start)
                    word="-";
                //tv.append("파싱결과 :" +item_end+"/"+pt_start+"/"+ word+"\n");

                //[1.주소]

                //[2.이미지]
                String tag_start2="<firstimage>";
                String tag_end2="</firstimage>";

                pt_start2 = result.indexOf(tag_start2, line1);//작은 tag안 시작
                pt_end2 = result.indexOf(tag_end2, line1);//작은 tag안 끝
                if(pt_start2==-1)
                    break;

                word2 = result.substring(pt_start2 + tag_start2.length(), pt_end2);
                if(item_end<pt_start2)
                    word2="no";
                //tv.append("파싱결과2 :" + pt_start2+"/"+word2+"\n");
                //[2.이미지]

                //[3.전화번호]
                String tag_start3="<tel>";
                String tag_end3="</tel>";

                pt_start3 = result.indexOf(tag_start3, line1);//작은 tag안 시작
                pt_end3 = result.indexOf(tag_end3, line1);//작은 tag안 끝
                if(pt_start3==-1)
                    break;

                word3 = result.substring(pt_start3 + tag_start3.length(), pt_end3);
                if(item_end<pt_start3)
                    word="-";
                String[] arr = word3.split("&lt;br&gt;");
                //tv.append("파싱결과3 :" + arr[0]+"\n");
                //[3.전화번호]

                String tag_start4="<title>";
                String tag_end4="</title>";

                pt_start4 = result.indexOf(tag_start4, line1);//작은 tag안 시작
                pt_end4 = result.indexOf(tag_end4, line1);//작은 tag안 끝

                word4 = result.substring(pt_start4 + tag_start4.length(), pt_end4);
                // tv.append("  파싱결과4 :" + word4);
                String[] arr2 = word4.split("\\(");
                String[] arr3=arr2[0].split("\\[");
                //tv.append("파싱결과4 :"+pt_start4+arr3[0]+"\n\n");

                //4.title

                info i =new info( arr2[0], word, arr[0], word2, "String img2");
                i_arr.add(i);
                line1 = item_end + 1;

            }

            m_pdlg.dismiss();//ProgressDialog를 없애는것
        }

        private String downloadUrl(String myurl) throws IOException{
            HttpURLConnection conn = null;
            try{
                URL url = new URL(myurl);
                conn = (HttpURLConnection)url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));//bufferReader
                String line = null;
                String page="";
                while((line=bufreader.readLine())!=null){//라인단위
                    page += line;
                    count++;
                    publishProgress(count);
                }
                return page;
            }finally {
                conn.disconnect();//성공이 되던 안되던 disconnect
            }
        }

    }//public class DownloadWebPageTask extends AsyncTask<String, Void, String>
    public void searchWeb(String query) {
        Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



}