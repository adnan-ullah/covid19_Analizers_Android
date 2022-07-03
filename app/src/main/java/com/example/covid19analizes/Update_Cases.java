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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.charts.BarChart;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Update_Cases extends Fragment {

    public String[] colounm;
    public HashMap<String, Float> map;
    public DBHandler dbHandler, db,myDb;
 public   int total = 0;
    TextView text;
    TextView activeCases,todayCases, deathCases, recover, todayDeaths, todayRecover,cases, totalTest;
    PieChart mPieChart;
    BarChart barChart,barChart3;
    BlurImageView blurImag,barBg;
    CardView cardView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_case, container, false);
        activeCases = v.findViewById(R.id.activeCases);
        todayCases = v.findViewById(R.id.updateConfirm);
        deathCases = v.findViewById(R.id.deathText);
        recover = v.findViewById(R.id.recoverText);
        todayDeaths = v.findViewById(R.id.updatedeathCaseText);
        todayRecover = v.findViewById(R.id.updateRecovered);
        cases = v.findViewById(R.id.ConfirmCases);
        totalTest = v.findViewById(R.id.totalTest);
        blurImag = v.findViewById(R.id.CovidBlurImageView);
        barBg = v.findViewById(R.id.barBg);
        map =  new HashMap<>();
        cardView = v.findViewById(R.id.myCard);


        cardView.setCardBackgroundColor(Color.parseColor("#B2FFB5"));
        blurImag.setBlur(70);
        barBg.setBlur(70);

        colounm = new String[]{
                "updated",
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
                "oneCasePerPeople",
                "oneDeathPerPeople",
                "oneTestPerPeople",
                "activePerOneMillion",
                "recoveredPerOneMillion",
                "criticalPerOneMillion",
                "affectedCountries"};



        final ArrayList<String> ar = new ArrayList<>();
        myDb = new DBHandler(getActivity());

        myDb.deleteWorldData();
       mPieChart = (PieChart) v.findViewById(R.id.Mypiechart);
        barChart = (BarChart) v.findViewById(R.id.barchart1);







        // db = new DBHandler(getActivity());
        //db.deleteAll();

        String url = ("https://disease.sh/v3/covid-19/all/");
        //url = url + "Japan/";
        //text = findViewById(R.id.textView);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject dataList = new JSONObject(response.toString());

                    Log.d("mlesisi", dataList.toString());


                    for (int i = 0; i < 21; i++) {

                     ar.add(dataList.getString((colounm[i])).toString());


                    }

                   // db.addNewStudent(ar);
                    myDb.addWorldData(ar);
                    showingData();

                  //  Log.d("abc",String.valueOf(total));


                    ArrayList NoOfEmp = new ArrayList();

                    NoOfEmp.add(new Entry((100*Float.parseFloat(cases.getText().toString())/total), 0));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(deathCases.getText().toString())/total), 1));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(activeCases.getText().toString())/total), 2));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(recover.getText().toString())/total), 3));

                    PieDataSet dataSet = new PieDataSet(NoOfEmp, "Worldwide Cases");

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


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

        updateBarcValue();

        return v;

    }


    public void updateBarcValue() {

        String url = ("https://disease.sh/v3/covid-19/countries/");

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String countryName = jsonObject.getString("country");

                                String cases = jsonObject.getString("cases");

                                map.put(countryName,Float.parseFloat(cases));


                            }
                           final Map<String, Float> res = sortByValue(map);
                           final ArrayList<String> keys = new ArrayList<String>(res.keySet());

                            BarLoading(res,keys);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


    }


    private void BarLoading(Map<String, Float> res,ArrayList<String> keys ) {



        ArrayList<BarEntry> NoOfEmp = new ArrayList();

        for(int i = keys.size()-1, j = 0; i>=0; i--, j++)
        {
            if(j>10)break;
            NoOfEmp.add(new BarEntry(res.get(keys.get(i)), j));
        }

        ArrayList year = new ArrayList();
        for(int i = keys.size()-1,j = 0; i>=0; i--, j++)
        {
            if(j>10)break;
            year.add(keys.get(i));
        }



        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Total Cases");

        barChart.setDescription("");
        barChart.animateY(5000);

        BarData data = new BarData(year, bardataset);

        bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        bardataset.setValueTextSize(9f);
        bardataset.setValueTextColor(Color.parseColor("#B2FFB5"));
        bardataset.setValueFormatter(new LargeValueFormatter());

        barChart.getLegend().setTextColor(Color.parseColor("#FF8080"));
        barChart.getAxisLeft().setTextColor(Color.parseColor("#B2FFB5"));
        barChart.getAxis(YAxis.AxisDependency.RIGHT).setTextColor(Color.parseColor("#B2FFB5"));


         XAxis x= barChart.getXAxis();

        x.setPosition(XAxis.XAxisPosition.TOP);
        x.setTextColor(Color.CYAN);
        barChart.setBackgroundColor(Color.TRANSPARENT);
        barChart.setData(data);
        barChart.invalidate();
    }


    public void showingData() {

        Cursor cursor = myDb.readWorldData();

        if (cursor.getCount() == 0) {
            Log.d("adnan", "Not data");
        } else {
            Log.d("ullah", "Yes data");

            while (cursor.moveToNext()) {
                for (int i = 0; i < 21; i++)
                {


                    if(cursor.getColumnName(i).equals("cases")){
                        cases.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                   else if(cursor.getColumnName(i).equals("todayCases")){
                        todayCases.setText("+"+cursor.getString(i));

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
