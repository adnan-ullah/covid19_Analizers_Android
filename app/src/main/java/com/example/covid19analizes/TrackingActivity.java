package com.example.covid19analizes;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrackingActivity extends AppCompatActivity {

   public String[] colounm ;


    public DBHandler dbHandler,db;

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        colounm =new String[] {
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
                "undefined",
                "activePerOneMillion",
                "recoveredPerOneMillion",
                "criticalPerOneMillion"};

       final  ArrayList<String> ar = new ArrayList<>();

//db =  new DBHandler(this);
//db.deleteAll();

        String url = ("https://disease.sh/v3/covid-19/countries/Bangladesh/");
        //text = findViewById(R.id.textView);


        StringRequest request =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject dataList = new JSONObject(response.toString());

                    for(int i = 0; i<23; i++)
                    {
                       // Log.d("ml", dataList.getString((colounm[i])));
                    //    ar.add(dataList.getString((colounm[i])));


                    }
                  //  db.addNewStudent(ar);
               //     showingData();


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



    public void Retriving()
    {

    }




}