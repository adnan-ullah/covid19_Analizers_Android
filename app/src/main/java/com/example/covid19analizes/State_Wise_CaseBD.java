package com.example.covid19analizes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mc0239.ComboBox;
import com.mc0239.ComboBoxAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class State_Wise_CaseBD extends Fragment {

    ListView listView;
    ComboBox comboBox;
    ComboBoxAdapter comboBoxAdapter;
    Button updateButton;
    ArrayList<Pair<String, String>> dp;
    Bundle bundle;

    String state ;
    String statePreCases;
    String updateValue ;

    DBHandler stateDatabase;

    HashMap<String, Float> map;

    BarChart barChart;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser)
        {


        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dv_wise_bd, container, false);
        listView = v.findViewById(R.id.listViewBDState1);

        updateButton = v.findViewById(R.id.updateState1);
        barChart = v.findViewById(R.id.barchart3);

        stateDatabase = new DBHandler(getActivity());
        map = new HashMap<>();

        ArrayList<Pair<String,String>> dp  = showingData1();


        final Map<String, Float> res = sortByValue(map);
       final ArrayList<String> keys = new ArrayList<String>(res.keySet());

       BarLoading(res,keys);


        FinalAdapter adapter = new FinalAdapter(getActivity(),dp);
        listView.setAdapter(adapter);



        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(getActivity(), div_update_activity.class));



            // comboBoxAdapter = new ComboBoxAdapter(getActivity(), dp,"");
            // comboBox.setAdapter(comboBoxAdapter);

            }
        });

        return v;
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

    public   ArrayList<Pair<String, String>>  showingData1() {
        ArrayList<Pair<String, String>> dp;
        dp = new ArrayList<>();
        String name, cases;
        name = null;
        cases = null;
        Cursor cursor = stateDatabase.readState();

            while (cursor.moveToNext()) {
                for (int i = 0; i < 8; i++)
                {


                    if(cursor.getColumnName(i).equals("stateName")){
                      name = cursor.getString(i);



                    }
                    else if(cursor.getColumnName(i).equals("stateCases")){
                        cases= cursor.getString(i);
                    }

                        /**
                        if(name.equalsIgnoreCase(comboBox.getSelectedEntry()))
                        {

                            cases = String.valueOf(Integer.parseInt(cases) + Integer.parseInt(amountCases.getText().toString()));
                            stateDatabase.update(comboBox.getSelectedEntry(),cases);
                        }
                         **/





               }

                dp.add(new Pair<String, String>(name.toString(),cases));
                map.put(name,Float.parseFloat(cases));
                // total =  Float.parseFloat(activeCases.getText().toString())+ Float.parseFloat(deathCases.getText().toString())+Float.parseFloat(todayCases.getText().toString())+Float.parseFloat(recover.getText().toString());



            }



        return dp;
    }



    public class FinalAdapter extends BaseAdapter {

        public ArrayList<Pair<String, String>> itemsModelsl;
        public List<Pair<String, String>> itemsModelListFiltered;
        private Context context;

        public FinalAdapter(Context context, ArrayList<Pair<String, String>> itemsModelsl ) {
            this.itemsModelsl = itemsModelsl;

            this.itemsModelListFiltered = itemsModelsl;
            this.context = context;
        }


        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_item_state, null);

            final TextView stateName = view.findViewById(R.id.stateName);
            TextView stateCasesText = view.findViewById(R.id.stateCasesText);

            stateName.setText(itemsModelsl.get(position).first);
            stateCasesText.setText(itemsModelsl.get(position).second);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   startActivity(new Intent(getActivity(), each_BDState_data.class).putExtra("divName",stateName.getText().toString()));
                }
            });


            return view;
        }



    }




}





