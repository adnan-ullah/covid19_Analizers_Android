package com.example.covid19analizes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.covid19analizes.R;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import im.dacer.androidcharts.LineView;


public class each_country_data extends AppCompatActivity {
    public String[] colounm;
    public HashMap<String, Float> map;
    public DBHandler countryDB;
    public   int total = 0;
    TextView text;
    TextView activeCases,countryName,todayCases, deathCases, recover, todayDeaths, todayRecover,cases, totalTest;
    PieChart mPieChart;
    BarChart barChart,barChart3;

    BlurImageView blurImag,barBg;
    CardView cardView;

    ImageFilterView flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.each_country_data);

        activeCases =findViewById(R.id.activeCasesCountry);
        todayCases = findViewById(R.id.updateConfirmCountry);
        deathCases = findViewById(R.id.deathTextCountry);
        recover = findViewById(R.id.recoverTextCountry);
        todayDeaths = findViewById(R.id.updatedeathCaseTextCountry);
        todayRecover =findViewById(R.id.updateRecoveredCountry);
        countryName = findViewById(R.id.countryName);
        cases = findViewById(R.id.ConfirmCasesCountry);
        totalTest = findViewById(R.id.totalTestCountry);
        blurImag = findViewById(R.id.CovidBlurImageView);
        flag = findViewById(R.id.flagPostion);





        map =  new HashMap<>();

        colounm = new String[]{
                "updated",
                "country",
                "cases",
                "todayCases",
                "deaths",
                "todayDeaths",
                "recovered",
                "todayRecovered",
                "active",
                "critical",
                "casesPerOneMillion",
                "deathsPerOneMillion",
                "tests",
                "testsPerOneMillion",
                "population",
                "continent",
                "oneCasePerPeople",
                "oneDeathPerPeople",
                "oneTestPerPeople",
                "activePerOneMillion",
                "recoveredPerOneMillion",
                "criticalPerOneMillion",
        };



        final ArrayList<String> ar = new ArrayList<>();
        countryDB = new DBHandler(this);
        countryDB.deleteCountryData();
        mPieChart = (PieChart) findViewById(R.id.countryPieChart);








        // db = new DBHandler(getActivity());
        //db.deleteAll();

        Intent i = getIntent();

        String country_url = i.getStringExtra("countryname");
        String flagString = i.getStringExtra("flagLink");

        Picasso.with(getApplicationContext()).load(flagString).resize(160, 100).
                centerCrop().into(flag);



        String url = "https://disease.sh/v3/covid-19/countries/"+ country_url;
        //url = url + "Japan/";
        //text = findViewById(R.id.textView);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject dataList = new JSONObject(response.toString());



                    for (int i = 0; i < 22; i++) {
                        //  Log.d("ml", dataList.getString((colounm[i])));
                        ar.add(dataList.getString((colounm[i])).toString());


                    }

                    //db.addNewStudent(ar);
                    countryDB.addCountryData(ar);
                    showingData();

                    //  Log.d("abc",String.valueOf(total));


                    ArrayList NoOfEmp = new ArrayList();

                    NoOfEmp.add(new Entry((100*Float.parseFloat(cases.getText().toString())/total), 0));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(deathCases.getText().toString())/total), 1));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(activeCases.getText().toString())/total), 2));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(recover.getText().toString())/total), 3));

                    PieDataSet dataSet = new PieDataSet(NoOfEmp, countryName.getText()+" Cases");

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(0);
                    ArrayList year = new ArrayList();

                    if(100*Float.parseFloat(cases.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(cases.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");

                    if(100*Float.parseFloat(deathCases.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(deathCases.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");
                    if(100*Float.parseFloat(activeCases.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(activeCases.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");
                    if(100*Float.parseFloat(recover.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(recover.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");



                    PieData data = new PieData(year, dataSet);
                    data.setValueTextSize(10f);

                    // data.setValueTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.BOLD));
                    data.setDrawValues(false);
                    int[] colors = new int[10];
                    int counter = 0;

                    colors[0] = Color.parseColor("#FFE0AF");
                    colors[1] =  Color.parseColor("#FF5353");
                    colors[2] = Color.parseColor("#A4F8FF");
                    colors[3] = Color.parseColor("#8AFF8E");
                    dataSet.setColors(colors);

                    List<Integer> colorTextValue = new ArrayList<>();
                    colorTextValue.add(Color.parseColor("#FFFFFF"));
                    colorTextValue.add(Color.parseColor("#FFE9E9"));
                    colorTextValue.add(Color.parseColor("#575D5E"));
                    colorTextValue.add(Color.parseColor("#1A1E1A"));




                    data.setValueTextColors(colorTextValue);


                    mPieChart.setData(data);
                    dataSet.setColors(colors);
                    data.setHighlightEnabled(true);
                    mPieChart.animateXY(5000, 5000);
                    mPieChart.setDescription("");







                } catch (JSONException e) {
                    //Log.d("ax",e.getMessage().toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);





    }


    public void showingData() {

        Cursor cursor = countryDB.readCountryData();

        if (cursor.getCount() == 0) {
            Log.d("adnan", "Not data");
        } else {
            Log.d("ullah", "Yes data");

            while (cursor.moveToNext()) {
                for (int i = 0; i < 22; i++)
                {


                    if(cursor.getColumnName(i).equals("cases")){
                        cases.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("todayCases")){
                        todayCases.setText("+"+cursor.getString(i));

                    }
                    else if(cursor.getColumnName(i).equals("country")){
                        countryName.setText(cursor.getString(i));

                    }
                    else if(cursor.getColumnName(i).equals("deaths")){
                        deathCases.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("todayDeaths")) {
                        todayDeaths.setText("+"+cursor.getString(i));

                    }
                    else if(cursor.getColumnName(i).equals("recovered")) {
                        recover.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("todayRecovered")) {
                        todayRecover.setText("+"+cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("active")) {
                        activeCases.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("tests")) {
                        totalTest.setText(cursor.getString(i));

                    }

                }


                // total =  Float.parseFloat(activeCases.getText().toString())+ Float.parseFloat(deathCases.getText().toString())+Float.parseFloat(todayCases.getText().toString())+Float.parseFloat(recover.getText().toString());



            }
        }
    }





}