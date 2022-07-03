package com.example.covid19analizes;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mc0239.ComboBox;
import com.mc0239.ComboBoxAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Update_State_Data extends Fragment {
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.country_data_entry, container, false);


        stateDatabase = new DBHandler(getActivity());
        comboBox1= v.findViewById(R.id.updateStateCombo);
        confirmStateCases = v.findViewById(R.id.stateConfirmCases);
        newCases = v.findViewById(R.id.stateNewCases);
        activeCases = v.findViewById(R.id.stateActiveCases);
        RecoveredCases = v.findViewById(R.id.stateRecoverCases);
        NewRecoveredCases = v.findViewById(R.id.statenNewRecoverCases);
        DeathCases = v.findViewById(R.id.stateDeathCases);
        NewDeathCases = v.findViewById(R.id.stateNewDeathCases);

        updateButton = v.findViewById(R.id.updateDivisionData);
        addButton = v.findViewById(R.id.AddState);


        ArrayList<Pair<String, String>>  stateNames = new ArrayList<>();
        stateNames.add(new Pair<String, String>("Barisal",""));
        stateNames.add(new Pair<String, String>("Chittagong",""));
        stateNames.add(new Pair<String, String>("Dhaka",""));
        stateNames.add(new Pair<String, String>("Khulna",""));
        stateNames.add(new Pair<String, String>("Mymensingh",""));
        stateNames.add(new Pair<String, String>("Rajshahi",""));
        stateNames.add(new Pair<String, String>("Rangpur",""));
        stateNames.add(new Pair<String, String>("Sylhet",""));


       comboBoxAdapter1 = new ComboBoxAdapter(getActivity(), stateNames,"");
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
            }
        });



        return v;
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
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });
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





