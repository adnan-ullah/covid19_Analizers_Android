package com.example.covid19analizes;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19analizes.DBHandler;
import com.example.covid19analizes.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jgabrielfreitas.core.BlurImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Country_update extends Fragment {

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



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.country_data, container, false);
        activeCases = v.findViewById(R.id.activeCasesCountry);
        todayCases = v.findViewById(R.id.updateConfirmCountry);
        deathCases = v.findViewById(R.id.deathTextCountry);
        recover = v.findViewById(R.id.recoverTextCountry);
        todayDeaths = v.findViewById(R.id.updatedeathCaseTextCountry);
        todayRecover = v.findViewById(R.id.updateRecoveredCountry);
        countryName = v.findViewById(R.id.countryName);
        cases = v.findViewById(R.id.ConfirmCasesCountry);
        totalTest = v.findViewById(R.id.totalTestCountry);
        blurImag = v.findViewById(R.id.CovidBlurImageView);

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
                "undefined",
                "activePerOneMillion",
                "recoveredPerOneMillion",
                "criticalPerOneMillion",
        };



        final ArrayList<String> ar = new ArrayList<>();
        countryDB = new DBHandler(getActivity());
        countryDB.deleteCountryData();
       mPieChart = (PieChart) v.findViewById(R.id.countryPieChart);








        // db = new DBHandler(getActivity());
        //db.deleteAll();

        String url = ("https://disease.sh/v3/covid-19/countries/Bangladesh");
        //url = url + "Japan/";
        //text = findViewById(R.id.textView);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject dataList = new JSONObject(response.toString());



                    for (int i = 0; i < 23; i++) {
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

                    PieDataSet dataSet = new PieDataSet(NoOfEmp, "Overview");


                    ArrayList year = new ArrayList();

                    year.add("");
                    year.add("");
                    year.add("");
                    year.add("");

                    PieData data = new PieData(year, dataSet);
                    data.setValueTextSize(12f);
                    data.setValueTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.BOLD));
                    List<Integer> colors = new ArrayList<>();
                    colors.add(Color.parseColor("#FF9C9C"));
                    colors.add(Color.parseColor("#4C9C23"));
                    colors.add(Color.parseColor("#FF9C9C"));
                    colors.add(Color.parseColor("#4C9C23"));
                    data.setValueTextColors(colors);

                    mPieChart.setData(data);
                    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
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


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);



        return v;

    }


    public void showingData() {

        Cursor cursor = countryDB.readCountryData();

        if (cursor.getCount() == 0) {
            Log.d("adnan", "Not data");
        } else {
            Log.d("ullah", "Yes data");

            while (cursor.moveToNext()) {
                for (int i = 0; i < 23; i++)
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
    public static HashMap<String, Float> sortByValue(HashMap<String, Float> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Float> >list =
                new LinkedList<Map.Entry<String, Float> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2)
            {
                return ((o1.getValue()).compareTo(o2.getValue()));
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
