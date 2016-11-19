package com.poster.danbilap.project_yeobo;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danbilap.project_yeobo.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestFragment4 extends Fragment {
    int n_id;//나라순서

    public static TestFragment4 newInstance(int n_id){
        TestFragment4 instance = new TestFragment4();
        instance.n_id = n_id;//나라 순서
        return instance;
    }
    TextView tv;
    Context context;
    int count=0;
    String real_addr=null;

    String data=null;
    String data2=null;

    int nation_id=1;//은슬

    TextView degree1;//섭씨
    TextView degree2;//화씨
    TextView high;//최고온도
    TextView low;//최저온도
    TextView state;//상태
    ImageView img;//이미지

    Button btn1,btn2,btn3,btn4;


    String url1_1="http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaBasedList?serviceKey=MzCAnESO3z50JKMA%2FmBbn28TIlId4PzXUP3j170v7c3MwCNaP%2FlyVWpFSZy%2BQHNC0dqzya0Ogp8sJbHPit2Ssw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&startPage=1&arrange=B&listYN=Y&areaCode=";
    String url1_2="&cat1=A01&MobileOS=ETC&MobileApp=%EA%B3%B5%EC%9C%A0%EC%9E%90%EC%9B%90%ED%8F%AC%ED%84%B8";
    //버튼 1
    String url2_1="http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaBasedList?serviceKey=MzCAnESO3z50JKMA%2FmBbn28TIlId4PzXUP3j170v7c3MwCNaP%2FlyVWpFSZy%2BQHNC0dqzya0Ogp8sJbHPit2Ssw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&startPage=1&arrange=B&listYN=Y&areaCode=";
    String url2_2="&cat1=A03&MobileOS=ETC&MobileApp=%EA%B3%B5%EC%9C%A0%EC%9E%90%EC%9B%90%ED%8F%AC%ED%84%B8";
    //버튼 2
    String url3_1="http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaBasedList?serviceKey=MzCAnESO3z50JKMA%2FmBbn28TIlId4PzXUP3j170v7c3MwCNaP%2FlyVWpFSZy%2BQHNC0dqzya0Ogp8sJbHPit2Ssw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&startPage=1&arrange=B&listYN=Y&areaCode=";
    String url3_2="&cat1=A04&MobileOS=ETC&MobileApp=%EA%B3%B5%EC%9C%A0%EC%9E%90%EC%9B%90%ED%8F%AC%ED%84%B8";
    //버튼 3(레져 스포츠)
    String url4_1="http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaBasedList?serviceKey=MzCAnESO3z50JKMA%2FmBbn28TIlId4PzXUP3j170v7c3MwCNaP%2FlyVWpFSZy%2BQHNC0dqzya0Ogp8sJbHPit2Ssw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&startPage=1&arrange=B&listYN=Y&areaCode=";
    String url4_2="&cat1=A05&MobileOS=ETC&MobileApp=%EA%B3%B5%EC%9C%A0%EC%9E%90%EC%9B%90%ED%8F%AC%ED%84%B8";
    //버튼 4(쇼핑)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test_4, container, false);

        nation_id=n_id;//넘어온 나라번호 넘겨주기
        context=getActivity();
        //tv=(TextView)rootView.findViewById(R.id.tv);
        degree1=(TextView)rootView.findViewById(R.id.degree1);
        degree2=(TextView)rootView.findViewById(R.id.degree2);
        high=(TextView)rootView.findViewById(R.id.high);
        low=(TextView)rootView.findViewById(R.id.low);
        //state=(TextView)rootView.findViewById(R.id.state);
        img=(ImageView)rootView.findViewById(R.id.img);
        btn1=(Button)rootView.findViewById(R.id.btn1);
        //init();
        real_addr=xmlparsing();//xml파싱 위치를 가져온다.
        //addr="http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1114055000";
        new DownloadWebPageTask().execute(real_addr);//날씨를 가져온다.

        btn1=(Button)rootView.findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity().getApplication(), Tour_info.class);
                Bundle bundle = new Bundle();
                bundle.putString("nation_id", url1_1 + nation_id + url1_2);//은슬
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        btn2=(Button)rootView.findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity().getApplication(), Tour_info.class);
                Bundle bundle = new Bundle();
                bundle.putString("nation_id",url2_1+nation_id+url2_2 );//은슬
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        btn3=(Button)rootView.findViewById(R.id.btn3);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity().getApplication(), Tour_info.class);
                Bundle bundle = new Bundle();
                bundle.putString("nation_id",url3_1 + nation_id + url3_2);//은슬
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });




        btn4 = (Button) rootView.findViewById(R.id.btn4);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity().getApplication(), Tour_info.class);
                Bundle bundle = new Bundle();
                bundle.putString("nation_id",url4_1+nation_id+url4_2 );//은슬
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });


        return rootView;
    }
    String xmlparsing(){
        String file = "basic_info.xml";
        String result = "";
        try{
            InputStream is = getActivity().getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "utf-8");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result));
            int eventType = xpp.getEventType();
            boolean aSet = false;
            boolean bSet = false;


            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType==XmlPullParser.START_DOCUMENT){
                    ;

                }
                else if(eventType== XmlPullParser.START_TAG){
                    String tag_name = xpp.getName();
                    if(tag_name.equals("code")) {
                        bSet=true;
                    }
                    if(tag_name.equals("addr")) {
                        aSet=true;
                    }
                }else if(eventType==XmlPullParser.TEXT){
                    if(bSet){
                        data = xpp.getText();
                        bSet = false;
                    }
                    if(aSet){
                        if(data.equals(""+nation_id)) {//코드번호에 따른 정보를 가져옴.은슬
                            data2 = xpp.getText();
                            real_addr=data2;
                            //tv.append(data2 + "\n");
                            aSet = false;
                        }
                    }
                }


                else if(eventType==XmlPullParser.END_TAG){
                    ;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            tv.setText(e.getMessage());
        }
        return real_addr;
    }
    //xml파싱
    public class DownloadWebPageTask extends AsyncTask<String, Integer, String> {//원래는 integer아니고 void였음.데이터타입에따라
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
        }

        @Override
        protected void onPostExecute(String result) {//작업이 끝난후
            //   tv.append(result+"\n");
            String word ="";
            int pt_start=-1;
            int pt_end=-1;

            //1.현재온도(섭씨) 2.현재온도(화씨)
            String tag_start="<temp>";
            String tag_end="</temp>";
            pt_start=result.indexOf(tag_start);
            if(pt_start != -1){
                pt_end = result.indexOf(tag_end);
                if(pt_end!= -1){
                    word = result.substring(pt_start+tag_start.length(), pt_end);
                    double word2 = Double.parseDouble(word);
                    int cel=(int)word2;
                    int cel2=(int)(word2*1.8)+32;//섭씨->화씨

                    degree1.setText(""+cel);//1.섭씨
                    degree2.setText(""+cel2);//2.화씨
                }else
                    degree1.setText("There is no data");
            }else
                degree1.setText("There is no data");

            //3.최고온도
            tag_start="<tmx>";
            tag_end="</tmx>";
            pt_start=result.indexOf(tag_start);
            if(pt_start != -1){
                pt_end = result.indexOf(tag_end);
                if(pt_end!= -1){
                    word = result.substring(pt_start+tag_start.length(), pt_end);
                    double word2 = Double.parseDouble(word);
                    if(word2==-999.0)
                        high.setText("-");//3.최고온도
                    else {
                        word2 = Double.parseDouble(word);
                        int h = (int) word2;
                        high.setText(""+h);//3.최고온도
                    }
                }else
                    high.setText("There is no data");
            }else
                high.setText("There is no data");


            //4.최저온도
            tag_start="<tmn>";
            tag_end="</tmn>";
            pt_start=result.indexOf(tag_start);
            if(pt_start != -1){
                pt_end = result.indexOf(tag_end);
                if(pt_end!= -1){
                    word = result.substring(pt_start+tag_start.length(), pt_end);
                    double word2 = Double.parseDouble(word);
                    if(word2==-999.0)
                        low.setText("-");//4.최저온도
                    else {
                        word2 = Double.parseDouble(word);
                        int l = (int) word2;
                        low.setText(""+l);//4.최저온도
                    }
                }else
                    low.setText("There is no data");
            }else
                low.setText("There is no data");

            //5.상태
            tag_start="<wfEn>";
            tag_end="</wfEn>";
            pt_start=result.indexOf(tag_start);
            if(pt_start != -1){
                pt_end = result.indexOf(tag_end);
                if(pt_end!= -1){
                    word = result.substring(pt_start+tag_start.length(), pt_end);
                    // state.setText(word);//5.상태
                    if(word.equals("Clear")){
                        img.setImageResource(R.drawable.w1);
                    }
                    if(word.equals("Partly Cloudy")){
                        img.setImageResource(R.drawable.w2);
                    }
                    if(word.equals("Mostly Cloudy")){
                        img.setImageResource(R.drawable.w3);
                    }
                    if(word.equals("Cloudy")){
                        img.setImageResource(R.drawable.w4);
                    }
                    if(word.equals("Rain")){
                        img.setImageResource(R.drawable.w5);
                    }
                    if(word.equals("Snow")){
                        img.setImageResource(R.drawable.w6);
                    }
                }else
                    state.setText("There is no data");
            }else
                state.setText("There is no data");


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




}