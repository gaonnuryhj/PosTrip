package com.poster.danbilap.project_yeobo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.example.danbilap.project_yeobo.R;
import com.tsengvn.typekit.TypekitContextWrapper;

public class BootActivity extends AppCompatActivity {
    CountDownTimer ctimer;
    SharedPreferences setting;
    String sharedText;



    public void finish() {
        super.finish();
        ctimer.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ctimer.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        setting = getSharedPreferences("setting", 0);
        init();

        Intent intent=getIntent();
        String action = intent.getAction();
        String type = intent.getType();


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
               sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);    // 가져온 인텐트의 텍스트 정보

            }
        }
        ctimer.start();
    }
    void init(){
        ctimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                Intent myIntent = new Intent(BootActivity.this, LoginActivity.class);
                myIntent.putExtra("url",sharedText);
                startActivity(myIntent);
                BootActivity.this.finish();  // 이전 액티비티 종료 시킴
            }
        };
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
