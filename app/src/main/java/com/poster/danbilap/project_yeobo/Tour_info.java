package com.poster.danbilap.project_yeobo;


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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Tour_info extends AppCompatActivity {
    String nation_url;
    int ination_id=0;
    TextView tv,tv1,tv2,tv3;
    ImageView img;


    ArrayList<info> i_arr;
    ListView listView1;
    ListAdapter listAdapter;
    Context context;
    int count=0;
    String par_xml_r=null;
    static String a="a";
    static String a2=null;
    static ArrayList<String> titles = new ArrayList<String>();
    static ArrayList<String> imgs = new ArrayList<String>();
    static ArrayList<String> tels = new ArrayList<String>();
    static ArrayList<String> addrs = new ArrayList<String>();
    int c=0;


    static ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_info1);

        context=this;

        init();

    }

    void init() {

        //img=(ImageView)findViewById(R.id.img);

        //지역번호만 넘어오기
        //   tv=(TextView)findViewById(R.id.tv);

        //tv1=(TextView)findViewById(R.id.tv1);
        // tv2=(TextView)findViewById(R.id.tv2);
        // tv3=(TextView)findViewById(R.id.tv3);

        Bundle bundle = getIntent().getExtras(); // intent로 페이지 넘어옴.
        nation_url = bundle.getString("nation_id");

        //  tv.setText(nation_url);//test
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

        //li m_adapter = new CustomAdapter(getContext(), R.layout.custom_list, titleList, contentList, nameList,goodList);
        //list.setAdapter(m_adapter);
        try {

            a2= new DownloadWebPageTask().execute(nation_url).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        show(a2);
        for(int i=0;i<titles.size();i++) {
            info ii =new info(titles.get(i), addrs.get(i), tels.get(i), imgs.get(i), "img");
            i_arr.add(ii);
        }
        //info ii =new info(titles.get(0), addrs.get(0), tels.get(0), imgs.get(0), "img");
        //i_arr.add(ii);
        // for(int i=0;i<1;i++) {
//                    tv.append("1번" + addrs.get(i) + "\n");
//                    tv.append("2번" + tels.get(i) + "\n");
//                    tv.append("3번" + titles.get(i) + "\n");
//                    tv.append("4번" + imgs.get(i) + "\n\n");
        // info ii =new info(titles.get(i), addrs.get(i), tels.get(i), imgs.get(i), "img");
        // i_arr.add(ii);
        //  }
        //tv.append(titles.get(0));
        //getData(nation_url);


        //tv.append("jojo");
        // tv.append(addr1list[0]);
        //info i =new info("aa", "aa", "aa", "no", "img");

        // info i =new info(ba4, ba1, ba3, ba2, "img");

        //info i =new info(titlelist.get(0), addr1list.get(0), tellist.get(0), imglist.get(0), "img");
        //i_arr.add(i);
//        for(int k=0;k<c;k++){
//        info i =new info(titlelist.get(0), addr1list, tellist, imglist, "img");
//        i_arr.add(i);
//        }

        //ination_id=Integer.parseInt(nation_url);
        //ination_id = Integer.parseInt(nation_id);
        // tv.setText("" + ination_id);//test
        //지역번호 넘어오기




    }
    public void show(String parsing){
        String result=parsing;
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result));
            int eventType = xpp.getEventType();
            boolean aSet = false;//addr1
            boolean bSet = false;//firstimage
            boolean cSet = false;//tel
            boolean dSet = false;//title

            boolean iSet = false;//item

            String t_addrl = null;
            String t_firstimage=null;
            String t_tel=null;
            String t_title=null;
            String temp=null;


            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType==XmlPullParser.START_DOCUMENT){
                    ;
                }//최초 title테그안에 쓸데없는 내용이 있어서 추가해줬음.

                else if (eventType == XmlPullParser.START_TAG) { //스타트테그를 만나면 테그값 저장
                    String tag_name = xpp.getName();
                    if (tag_name.equals("addr1")) {
                        aSet = true;
                    }
                    if (tag_name.equals("firstimage")) {
                        bSet = true;
                    }
                    if (tag_name.equals("tel")) {
                        cSet = true;
                    }
                    if (tag_name.equals("title")) {
                        dSet = true;
                    }

                }
                else if (eventType == XmlPullParser.TEXT) { //스타트테그가 아니라 텍스트일경우
                    if (aSet) {
                        t_addrl = xpp.getText();
                        aSet = false;
                        //temp=t_firstimage;
                        if(bSet==false){
                            t_firstimage="no";
                        }
                    }
                    if (bSet) {
                        t_firstimage = xpp.getText();
                        bSet = false;
                    }
                    if (cSet) {
                        t_tel = xpp.getText();
                        cSet = false;
                    }
                    if (dSet) {
                        t_title = xpp.getText();
                        dSet = false;


                        //String[] arr3=arr2[0].split("\\[");

                        //                          addr1list[c-1]=t_addrl;
                        //                          imglist[c-1]=t_firstimage;
                        //                          tellist[c-1]=t_tel;
                        //                           titlelist[c-1]=t_title;

//                            if(c==1) {
//                                info i = new info(arr2[0], t_addrl, arr[0], "no", "String img2");
//                                i_arr.add(i);
//                            }
//                            info i =new info(arr2[0], t_addrl, arr[0], "no", "String img2");
//                            i_arr.add(i);

                    }
                }

                else if (eventType == XmlPullParser.END_TAG) { //스타트테그를 만나면 테그값 저장
                    String tagName = xpp.getName();

                    if(tagName.equals("item")){

                        String[] arr = t_tel.split("&lt;br&gt;");
                        String[] arr2 = t_title.split("\\(");
                        // 파싱한 데이터 사용 or 저장
                        //c=1;
                        //if(c==1) {
                        //    a = t_addrl;
                        //  a2 = t_firstimage;

                        addrs.add(t_addrl);
                        imgs.add(t_firstimage);
                        tels.add(arr[0]);
                        titles.add(arr2[0]);

                        //tv.append("a" + a + "a2" + a2+"\n");
                        //}
                        //if(c==1)
                        //tv.append("c값"+c+a+a2+"   1:" + t_addrl + "\n");
                        //c=0;
                        //a=null;
                        //           tv.append("2:" + t_firstimage+"\n");
                        //           tv.append("3:" + arr[0]+"\n");
                        //           tv.append("4:" + arr2[0] + "\n\n");
                        t_addrl = null;
                        t_firstimage=null;
                        arr[0]=null;
                        arr2[0]=null;
                        t_tel=null;
                        t_title=null;

                        // a=t_addrl;
//                        info i =new info(arr2[0], t_addrl, arr[0], t_firstimage, "img");
//                         i_arr.add(i);
//                        t_addrl = null;
//                         t_firstimage=null;
//                         t_tel=null;
//                         t_title=null;


                    }



                }

                eventType = xpp.next();

            }//while

        } //try
        catch (Exception e) {
            //tv.setText(e.getMessage());
        }

    }

    class DownloadWebPageTask extends AsyncTask<String, Integer, String> {//원래는 integer아니고 void였음.데이터타입에따라

        // onPreExecute() 실행 후에 백그라운드 쓰레드로 수행됨
        @Override
        protected String doInBackground(String... urls) {//백그라운드
            String uri = urls[0];

            try {
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(buf, "utf-8"));//bufferReader
                StringBuilder par_xml = new StringBuilder();

                String xml;
                while ((xml = bufferedReader.readLine()) != null) {
                    par_xml.append(xml + "\n");
                }

                return par_xml.toString();

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {//작업이 끝난후
            par_xml_r = result;
            //           show(par_xml_r);
//                tv.append("1번" + addrs.size() + "\n");
//                tv.append("1번" + addrs.get(0) + "\n");
//                tv.append("2번" + tels.get(0) + "\n");
//                tv.append("3번" + titles.get(0) + "\n");
//                tv.append("4번" + imgs.get(0) + "\n");


            //tv.append("갯수" + titles.size() + "\n");
//                for(int i=0;i<1;i++) {
////                    tv.append("1번" + addrs.get(i) + "\n");
////                    tv.append("2번" + tels.get(i) + "\n");
////                    tv.append("3번" + titles.get(i) + "\n");
////                    tv.append("4번" + imgs.get(i) + "\n\n");
//                    info ii =new info(titles.get(i), addrs.get(i), tels.get(i), imgs.get(i), "img");
//                    i_arr.add(ii);
//                }
            //com.poster.danbilap.project_yeobo.ListAdapter m_adapter = new com.poster.danbilap.project_yeobo.ListAdapter(getApplication().getApplicationContext(), R.layout.listview1_item, titleList, imgs, tels,addrs);
            //listView1.setAdapter(m_adapter);

//
            addrs.clear();
            tels.clear();
            titles.clear();
            imgs.clear();
            //  for(int i=0;i)

            //tv.append(par_xml_r);
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