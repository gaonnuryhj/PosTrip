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
import android.widget.LinearLayout;
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

/**
 * Created by daeun on 2016-11-16.
 */
public class TInsideFragment1 extends Fragment {

    ImageButton ib;
    ViewGroup rootView;
    ArrayList<Memo> m_arr;
    MemoAdapter memoAdapter;
    ListView lv;
    Memo m1;
    int mm_share;

    /////////////////////////////
    int t_number;
    int c_num;
    int check_num = 1; //메모


    myFragListener ac;

    public interface myFragListener {
        public int get1();

        public int get2();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof myFragListener) {
            ac = (myFragListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child1, container, false);


        t_number = ac.get1();
        c_num = ac.get2();

        init();

        return rootView;
    }


    public void init() {

        m_arr = new ArrayList<Memo>();
        lv = (ListView) rootView.findViewById(R.id.listView);
        memoAdapter = new MemoAdapter(getContext(), R.layout.memo, m_arr);
        lv.setAdapter(memoAdapter);
        show_memo(t_number);
        memoAdapter.notifyDataSetChanged();

        ib = (ImageButton) rootView.findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메모 화면뜨게 한다.
                Intent intent = new Intent(getActivity().getApplication(), MemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("travel_number", "" + t_number);
                bundle.putString("c_num", "" + c_num);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        memoAdapter.notifyDataSetChanged();

    }


    void show_memo(final int travel_number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeobo.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.show_memo(12, travel_number, new Callback<JsonObject>() {
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
                            int check_num = obj.get("check_num").getAsInt();
                            Memo m = new Memo(s_num, t_num, share_url, share_img, share_description, share_title, m_title, m_content, check_num);

                            m_arr.add(i, m);
                            memoAdapter.notifyDataSetChanged();
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

    void delete_memo(final int share_num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeobo.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.delete_memo(13, share_num, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        JsonArray result = jsonObject.getAsJsonArray("result");
                        String errcode = ((JsonObject) result.get(0)).get("errorCode").getAsString();

                        if (errcode.equals("success")) {
                            Toast.makeText(getActivity(), "메모를 삭제하였습니다!", Toast.LENGTH_SHORT).show();
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


    /**
     * Created by daeun on 2016-11-10.
     */
    public class MemoAdapter extends ArrayAdapter<Memo> {

        private static final String TAG = "LogCatTest";

        ArrayList<Memo> m_items;
        Context context;
        Bitmap urlbitmapImage;

        public void upDateItemList(ArrayList<Memo> m_items) {
            this.m_items = m_items;
            notifyDataSetChanged();
        }

        public MemoAdapter(Context context, int resource, ArrayList<Memo> objects) {
            super(context, resource, objects);
            this.context = context;
            this.m_items = objects;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {//뷰가 없을 시 row 레이아웃 만들어준다.
                LayoutInflater vi =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.memo, null);
            }
            if (v != null) {
                LayoutInflater vi =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.memo, null);
            }
            final Memo mi = m_items.get(position);//final로 선언해줘야 한다.
            if (mi != null) {
                final TextView titleView = (TextView) v.findViewById(R.id.titleView);
                final TextView subtitleView = (TextView) v.findViewById(R.id.subTitleView);
                final TextView urlView = (TextView) v.findViewById(R.id.urlView);
                final ImageView urlimage = (ImageView) v.findViewById(R.id.url_image);
                final ImageButton cancel_btn = (ImageButton) v.findViewById(R.id.cancel_btn);
                final String iurl = mi.getI_url();
                final String url = mi.getS_url();

                final String text = String.valueOf(m_items.get(position));
                final ImageButton button = (ImageButton)v.findViewById(R.id.cancel_btn);

                LinearLayout layout  = (LinearLayout)v.findViewById(R.id.iblayout);
                LinearLayout.LayoutParams layparam = (LinearLayout.LayoutParams) layout.getLayoutParams();
                if(url.equals("")) {
                    layparam.leftMargin = 420;
                    layout.setLayoutParams(layparam);
                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mm_share = m_arr.get(position).getS_num();
                        //길게 누르면 삭제 버튼 생성
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());// 여기서 this는 Activity의 this
                        // 여기서 부터는 알림창의 속성 설정
                        builder.setMessage("Are you sure you want to delete?")        // 메세지 설정
                                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        delete_memo(mm_share);
                                        m_arr.remove(position);
                                        memoAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("no", new DialogInterface.OnClickListener() {
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