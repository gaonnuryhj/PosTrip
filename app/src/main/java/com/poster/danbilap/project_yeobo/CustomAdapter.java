package com.poster.danbilap.project_yeobo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.danbilap.project_yeobo.R;

import java.util.ArrayList;

/**
 * Created by dh814_000 on 2016-11-14.
 */
class CustomAdapter extends ArrayAdapter<String> {
    ArrayList<String> titles;
    ArrayList<String> contents;

    ArrayList<String> names;
    Context context;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> titles , ArrayList<String> contents, ArrayList<String> names ) {
        super(context, textViewResourceId, titles);
        this.titles=titles;
        this.contents=contents;
//        this.bitmaps = bitmaps;
        this.context = context;
        this.names=names;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.custom_list, null);
        }
        String title=titles.get(position);
        String content=contents.get(position);

        if (title != null)
        {
            TextView textView1=(TextView)v.findViewById(R.id.title);
            textView1.setText(title);

            TextView textView2=(TextView)v.findViewById(R.id.content);
            textView2.setText(content);


            ImageView imageView=(ImageView)v.findViewById(R.id.picture_name);

            Glide.with(getContext()).load("http://203.252.182.94//uploads//"+names.get(position)).into(imageView);


        }
        return v;
    }

}
