package com.example.covid19analizes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class each_BDState_data extends AppCompatActivity {

    public HashMap<String, Float> map;
     DBHandler stateDB;
    public   int total = 0;
    TextView bdDivName;
    TextView bdDeath,bdNewDeath,bdActive, bdRecoverd, bdNewRecoverd, bdConfirm, bdNewConfirm,totalTestBd;
    PieChart mPieChart;
    BarChart barChart,barChart3;

    BlurImageView blurImag,barBg;
    CardView cardView;

    ImageFilterView flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.each_bd_state_data);

        bdDeath =findViewById(R.id.bdDeath);
        bdNewDeath = findViewById(R.id.bdNewDeath);
        bdActive = findViewById(R.id.bdActive);
        bdRecoverd = findViewById(R.id.bdRecoverd);
        bdNewRecoverd = findViewById(R.id.bdNewRecoverd);
        bdConfirm =findViewById(R.id.bdConfirm);
        bdNewConfirm = findViewById(R.id.bdNewConfirm);
        totalTestBd = findViewById(R.id.totalTestBd);


        bdDivName = findViewById(R.id.bdDivName);

        bdDivName.setSelected(true);
        Intent i = getIntent();
        if(i!=null)
        bdDivName.setText(i.getStringExtra("divName"));
        else
            bdDivName.setText("Dhaka");




        map =  new HashMap<>();





        final ArrayList<String> ar = new ArrayList<>();
        stateDB = new DBHandler(this);
       showingData();


        mPieChart = (PieChart) findViewById(R.id.statePieChart);






                    ArrayList NoOfEmp = new ArrayList();

                    NoOfEmp.add(new Entry((100*Float.parseFloat(bdConfirm.getText().toString())/total), 0));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(bdDeath.getText().toString())/total), 1));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(bdActive.getText().toString())/total), 2));
                    NoOfEmp.add(new Entry((100*Float.parseFloat(bdRecoverd.getText().toString())/total), 3));

                    PieDataSet dataSet = new PieDataSet(NoOfEmp, bdDivName.getText()+" Cases");

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(0);
                    ArrayList year = new ArrayList();

                    if(100*Float.parseFloat(bdConfirm.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(bdConfirm.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");

                    if(100*Float.parseFloat(bdDeath.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(bdDeath.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");
                    if(100*Float.parseFloat(bdActive.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(bdActive.getText().toString())/total))+"%");
                    }
                    else
                        year.add("");
                    if(100*Float.parseFloat(bdRecoverd.getText().toString())/total>3.5f)
                    {
                        year.add(String.valueOf(df.format(100*Float.parseFloat(bdRecoverd.getText().toString())/total))+"%");
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



    }


    public void showingData() {

        Cursor cursor = stateDB.readOneState(bdDivName.getText().toString());


        if(cursor!=null)
            Log.d("CheckDB", "Y");
        else
            Log.d("CheckDB", "N");

            while (cursor.moveToNext()) {
                for (int i = 0; i < 8; i++)
                {

                    if(cursor.getColumnName(i).equals("stateDeathCases")){
                        bdDeath.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("stateNewDeathCases")){
                        bdNewDeath.setText("+"+cursor.getString(i));

                    }
                    else if(cursor.getColumnName(i).equals("stateActiveCases")){
                        bdActive.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("stateRecoveredCases")){
                        bdRecoverd.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("stateNewRecoveredCases")) {
                        bdNewRecoverd.setText("+"+cursor.getString(i));

                    }
                    else if(cursor.getColumnName(i).equals("stateCases")) {
                        bdConfirm.setText(cursor.getString(i));
                        total = total+Integer.parseInt(cursor.getString(i));
                    }
                    else if(cursor.getColumnName(i).equals("stateNewCases")) {
                        bdNewConfirm.setText("+"+cursor.getString(i));
                    }


                }






            }
        }
    }





