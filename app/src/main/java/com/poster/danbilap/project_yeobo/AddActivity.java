package com.poster.danbilap.project_yeobo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danbilap.project_yeobo.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddActivity extends Activity {


    ArrayList<String> countryenname;
    ArrayList<String> countryname;
    ArrayList<String> countryid;

    Button btn1;
    Button btn_start, btn_end;
    Spinner spinner1;

    EditText title;

    String t_title, t_city,t_start, t_finish, id;
    int num;

    Calendar calendar = Calendar.getInstance();
    // 년도 구하기
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int date = calendar.get(Calendar.DAY_OF_MONTH);

    ArrayList<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent i = getIntent();
        id = i.getStringExtra("id");


        init();
    }

    void init() {

        title = (EditText) findViewById(R.id.title);

        final TextView start = (TextView) findViewById(R.id.start);
        final TextView end = (TextView) findViewById(R.id.end);
        String[] str = {"Seoul","In Cheon","Dae Jeon","Dae Gu","Gwang Ju","Bu San","Ul San","Se Jong","Gyeong Gi-do","Gang Won-do","Chung Cheong Buk-do","Chung Cheong Nam-do","Gyeong Sang Buk-do","Gyeong Sang Nam-do","Jeol La Buk-do","Jeol La Nam-do","JeJu"};
        adapter = new ArrayList<String>();
        Collections.addAll(adapter, str);
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(AddActivity.this, adapter);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(customSpinnerAdapter);
        spinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#0d95e9"));
                        num = i + 1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );


        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate =
                                Integer.toString(year) + "-" +
                                        Integer.toString(monthOfYear + 1) + "-" +
                                        Integer.toString(dayOfMonth);
                        start.setText(strDate);
                        // start.setTextColor(Color.BLACK);


                    }
                }, year, month, date); // month는 -1한 값으로 지정
                datePickerDialog.show();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate =
                                Integer.toString(year) + "-" +
                                        Integer.toString(monthOfYear + 1) + "-" +
                                        Integer.toString(dayOfMonth);
                        start.setText(strDate);
                        //  start.setTextColor(Color.BLACK);


                    }
                }, year, month, date); // month는 -1한 값으로 지정
                datePickerDialog.show();
            }
        });

        btn_end = (Button) findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate =
                                Integer.toString(year) + "-" +
                                        Integer.toString(monthOfYear + 1) + "-" +
                                        Integer.toString(dayOfMonth);
                        end.setText(strDate);
                        // end.setTextColor(Color.BLACK);

                    }
                }, year, month, date); // month는 -1한 값으로 지정
                datePickerDialog.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate =
                                Integer.toString(year) + "-" +
                                        Integer.toString(monthOfYear + 1) + "-" +
                                        Integer.toString(dayOfMonth);
                        end.setText(strDate);
                        //  end.setTextColor(Color.BLACK);

                    }
                }, year, month, date); // month는 -1한 값으로 지정
                datePickerDialog.show();
            }
        });

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력한 것들 DB에 넣기
                t_title = title.getText().toString();
                t_start = start.getText().toString();
                t_finish = end.getText().toString();
                t_city = (String) spinner1.getAdapter().getItem(spinner1.getSelectedItemPosition());
                // t_nation, n_id은 onPostExecute에서

                if (TextUtils.isEmpty(t_title) || TextUtils.isEmpty(t_city) || TextUtils.isEmpty(t_start) || TextUtils.isEmpty(t_finish)) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_r,
                            (ViewGroup) findViewById(R.id.toast_layout));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Please enter all");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                } else {

                    create_travel(id, t_title, t_city, t_start, t_finish, num);
                }

                AddActivity.this.finish();

                /*informationSearchTask = new InformationSearchTask();
                informationSearchTask.execute();

                contactSearchTask = new ContactSearchTask();
                contactSearchTask.execute();*/
            }
        });

        countryname = new ArrayList<String>();
        countryid = new ArrayList<String>();
        countryenname = new ArrayList<String>();


    }


    void create_travel(final String u_id, final String t_title, final String t_city, final String t_start, final String t_finish, final int c_num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://203.252.182.94/yeoboH.php").build();
                Retrofit retrofit = restAdapter.create(Retrofit.class);
                retrofit.create_travel(4, u_id, t_city, t_title, t_start, t_finish, c_num, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        JsonArray result = jsonObject.getAsJsonArray("result");
                        String errcode = ((JsonObject) result.get(0)).get("errorCode").getAsString();

                        if (errcode.equals("success")) {
                            LayoutInflater inflater2 = getLayoutInflater();
                            View layout2 = inflater2.inflate(R.layout.custom_toast,
                                    (ViewGroup) findViewById(R.id.toast_layout));

                            TextView text = (TextView) layout2.findViewById(R.id.text);
                            text.setText("New trip added!");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout2);
                            toast.show();
                            Intent myIntent = new Intent(AddActivity.this, MainActivity.class);
                            myIntent.putExtra("id", id);
                            startActivity(myIntent);
                            AddActivity.this.finish();
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

    public void onBackPressed() { // 뒤로 가기 했을 때 MainActivity로 돌아가도록
        super.onBackPressed();
        AddActivity.this.finish();
    }


    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(AddActivity.this);
            txt.setPadding(23, 23, 23, 23);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#0d95e9"));
            txt.setBackgroundColor(Color.parseColor("#ffffff"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(AddActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(23, 23, 23, 23);
            txt.setTextSize(16);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#0d95e9"));
            txt.setBackgroundColor(Color.parseColor("#ffffff"));
            return txt;
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
