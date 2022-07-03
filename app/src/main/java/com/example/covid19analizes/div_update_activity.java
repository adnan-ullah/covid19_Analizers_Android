package com.example.covid19analizes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.FragmentManager;

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
import com.mc0239.ComboBox;
import com.mc0239.ComboBoxAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class div_update_activity extends AppCompatActivity {

    EditText confirmStateCases, newCases, activeCases, RecoveredCases, NewRecoveredCases, DeathCases, NewDeathCases;

    ListView listView;
    ComboBox comboBox1;
    ComboBoxAdapter comboBoxAdapter1;
    Button updateButton, addButton;
    ArrayList<Pair<String, String>> dp;


    String state ;
    String statePreCases;
    String updateValue ;

    DBHandler stateDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_data_entry);



        stateDatabase = new DBHandler(this);
        comboBox1= findViewById(R.id.updateStateCombo);
        confirmStateCases = findViewById(R.id.stateConfirmCases);
        newCases = findViewById(R.id.stateNewCases);
        activeCases = findViewById(R.id.stateActiveCases);
        RecoveredCases = findViewById(R.id.stateRecoverCases);
        NewRecoveredCases = findViewById(R.id.statenNewRecoverCases);
        DeathCases = findViewById(R.id.stateDeathCases);
        NewDeathCases = findViewById(R.id.stateNewDeathCases);

        updateButton = findViewById(R.id.updateDivisionData);
        addButton = findViewById(R.id.AddState);


        ArrayList<Pair<String, String>>  stateNames = new ArrayList<>();
        stateNames.add(new Pair<String, String>("Barisal",""));
        stateNames.add(new Pair<String, String>("Chittagong",""));
        stateNames.add(new Pair<String, String>("Dhaka",""));
        stateNames.add(new Pair<String, String>("Khulna",""));
        stateNames.add(new Pair<String, String>("Mymensingh",""));
        stateNames.add(new Pair<String, String>("Rajshahi",""));
        stateNames.add(new Pair<String, String>("Rangpur",""));
        stateNames.add(new Pair<String, String>("Sylhet",""));


        comboBoxAdapter1 = new ComboBoxAdapter(this, stateNames,"");
        comboBox1.setAdapter(comboBoxAdapter1);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> infoList = new ArrayList<>();
                infoList.add(comboBox1.getSelectedEntry());
                infoList.add(confirmStateCases.getText().toString());
                infoList.add(newCases.getText().toString());
                infoList.add(activeCases.getText().toString());
                infoList.add(RecoveredCases.getText().toString());
                infoList.add(NewRecoveredCases.getText().toString());
                infoList.add(DeathCases.getText().toString());
                infoList.add(NewDeathCases.getText().toString());
                stateDatabase.updateAllStateInfo(comboBox1.getSelectedEntry(), infoList);
                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> eachStateInfo = new ArrayList<>();
                eachStateInfo.add(comboBox1.getSelectedEntry());
                eachStateInfo.add(confirmStateCases.getText().toString());
                eachStateInfo.add(newCases.getText().toString());
                eachStateInfo.add(activeCases.getText().toString());
                eachStateInfo.add(RecoveredCases.getText().toString());
                eachStateInfo.add(NewRecoveredCases.getText().toString());
                eachStateInfo.add(DeathCases.getText().toString());
                eachStateInfo.add(NewDeathCases.getText().toString());


                stateDatabase.addState(eachStateInfo);
                showingData();
                Toast.makeText(getApplicationContext(), "Added division successfully", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void showingData() {

        Cursor cursor = stateDatabase.readState();


        while (cursor.moveToNext()) {
            for (int i = 0; i < 8; i++)
            {
                Log.d("stateDatabase: ", cursor.getColumnName(i)+" : " +cursor.getString(i));
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

            TextView stateName = view.findViewById(R.id.stateName);
            TextView stateCasesText = view.findViewById(R.id.stateCasesText);

            stateName.setText(itemsModelsl.get(position).first);
            stateCasesText.setText(itemsModelsl.get(position).second);





            return view;
        }



    }









}