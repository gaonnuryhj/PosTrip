package com.poster.danbilap.project_yeobo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.danbilap.project_yeobo.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TInsideFragment3 extends Fragment {

    ViewGroup rootView;
    ArrayList<Memo> m_arr2;
    MemoAdapter memoAdapter2;
    ListView lv2;
    Memo m1;
    int mm_share;
    int t_number;
    int c_num;
    int travel_num = 0;
    String share_url=null;
    String share_img=null;
    String share_description=null ;
    String share_title=null;
    String memo_content =null;
    String memo_title=null ;
    int c_num2 = 0 ;
    int check_num2 = 3;

    myFragListener3 ac;

    public interface myFragListener3{
        public int get5();
        public int get6();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof myFragListener3){
            ac =(myFragListener3)activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child3, container, false);

        t_number=ac.get5();
        c_num=ac.get6();
        init();

        return rootView;
    }


    public void init() {

        m_arr2 = new ArrayList<Memo>();
        lv2 = (ListView) rootView.findViewById(R.id.listView2);
        memoAdapter2 = new MemoAdapter(getContext(), R.layout.memo2, m_arr2);
        lv2.setAdapter(memoAdapter2);
        show_other_memo(c_num);
        memoAdapter2.notifyDataSetChanged();
    }

    void show_other_memo(final int c_num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeobo.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.show_other_memo(14, c_num, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        JsonArray result = jsonObject.getAsJsonArray("result");

                        for (int i = 0; i < result.size(); i++) {
                            JsonObject obj = (JsonObject) result.get(i);
                            int s_num = obj.get("share_num").getAsInt();
                            int t_num = obj.get("travel_number").getAsInt();
                            String share_url = obj.get("share_url").getAsString();
                            String share_img = obj.get("share_img").getAsString();
                            String share_description = obj.get("share_description").getAsString();
                            String share_title = obj.get("share_title").getAsString();
                            String m_title = obj.get("memo_title").getAsString();
                            String m_content = obj.get("memo_content").getAsString();
                            int c_num = obj.get("c_num").getAsInt();
                            int check_num = obj.get("check_num").getAsInt();
                            Memo m = new Memo(s_num, t_num, share_url, share_img, share_description, share_title, m_title, m_content, c_num, check_num);

                            m_arr2.add(i, m);
                            memoAdapter2.notifyDataSetChanged();
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

    void share_others(final int travel_num,final String share_url,final String share_img,
                      final String share_description,final String share_title,
                      final String memo_title, final String memo_content, final int c_num2, final int check_num2){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeobo.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.share_other_memo(15 ,travel_num, share_url, share_img, share_description, share_title,
                        memo_title,memo_content, c_num2, check_num2,new Callback<JsonObject>() {
                            @Override
                            public void success(JsonObject jsonObject, Response response) {
                                JsonArray result = jsonObject.getAsJsonArray("result");
                                String errcode = ((JsonObject)result.get(0)).get("errorCode").getAsString();
                                Toast.makeText(getContext(), "공유되었습니다!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
            }
        }).start();
    }

    /**
     * Created by daeun on 2016-11-10.
     */
    public class MemoAdapter extends ArrayAdapter<Memo> {

        private static final String TAG = "LogCatTest";

        ArrayList<Memo> m_items2;
        Context context;
        Bitmap urlbitmapImage;

        public void upDateItemList(ArrayList<Memo> m_items) {
            this.m_items2 = m_items;
            notifyDataSetChanged();
        }

        public MemoAdapter(Context context, int resource, ArrayList<Memo> objects) {
            super(context, resource, objects);
            this.context = context;
            this.m_items2 = objects;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {//뷰가 없을 시 row 레이아웃 만들어준다.
                LayoutInflater vi =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.memo2, null);
            }
            if (v != null) {
                LayoutInflater vi =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.memo2, null);
            }
            final Memo mi = m_items2.get(position);//final로 선언해줘야 한다.
            if (mi != null) {
                final TextView titleView = (TextView) v.findViewById(R.id.titleView2);
                final TextView subtitleView = (TextView) v.findViewById(R.id.subTitleView2);
                final TextView urlView = (TextView) v.findViewById(R.id.urlView2);
                final ImageView urlimage = (ImageView) v.findViewById(R.id.url_image2);
                final String iurl = mi.getI_url();
                final String url = mi.getS_url();
                final ImageButton share_btn = (ImageButton)v.findViewById(R.id.share_btn);

                share_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        travel_num = t_number;
                        share_url = mi.getS_url();
                        share_img =mi.getI_url();
                        share_description = mi.getS_description();
                        share_title = mi.getS_title();
                        memo_content = mi.getM_content();
                        memo_title = mi.getM_title();
                        c_num2 = mi.getC_num();
                        check_num2 = 3;
                        //길게 누르면 삭제 버튼 생성
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());// 여기서 this는 Activity의 this

                        // 여기서 부터는 알림창의 속성 설정
                        builder.setTitle("공 유")        // 제목 설정
                                .setMessage("공유 하시겠습니까?")        // 메세지 설정
                                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        share_others(travel_num,share_url,share_img,share_description,share_title,
                                                memo_content,memo_title,c_num2,check_num2);
                                        //m_arr2.remove(position);
                                        //memoAdapter2.notifyDataSetChanged();
                                    }
                                })

                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog dialog = builder.create();    // 알림창 객체 생성
                        dialog.show();    // 알림창 띄우기
                    }
                });

                if (!mi.getM_title().equals("")) {
                    titleView.setText(mi.getM_title());
                    urlimage.setVisibility(View.GONE);
                }
                if (!mi.getS_title().equals("")) {
                    titleView.setText(mi.getS_title());
                }
                if (!mi.getM_content().equals("")) {
                    urlimage.setVisibility(View.GONE);
                    subtitleView.setText(mi.getM_content());//부제목은 null값 들어오면 아무것도 보이지 않은 상태로 두면 됨.
                }
                if (!mi.getS_description().equals("")) {
                    subtitleView.setText(mi.getS_description());
                    ActionMenuView.LayoutParams params = new ActionMenuView.LayoutParams(850, ActionMenuView.LayoutParams.WRAP_CONTENT);
                    subtitleView.setLayoutParams(params);
                }

                if (!iurl.equals("")) {
                    Glide.with(getContext()).load(iurl).into(urlimage);
                    urlimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            startActivity(myIntent);
                        }
                    });
                }

                if (!url.equals("")) {
                    urlView.setText(Html.fromHtml("<a href = \"" + url + "\">" + url + ""));
                    urlView.setMovementMethod(LinkMovementMethod.getInstance());
                    ActionMenuView.LayoutParams params = new ActionMenuView.LayoutParams(850, ActionMenuView.LayoutParams.WRAP_CONTENT);
                    urlView.setLayoutParams(params);
                    if (iurl.equals("")) {
                        urlimage.setImageResource(R.drawable.travel);
                        urlimage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent myIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(url));
                                startActivity(myIntent);
                            }
                        });
                    }
                }
            }
            return v;

        }
    }


}