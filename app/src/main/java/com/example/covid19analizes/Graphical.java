package com.example.covid19analizes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Graphical extends Fragment {
    EditText searchView;
    ListView listView;
    MyObject object;
    public ArrayList<MyObject> ar;
   public FinalAdapter adp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.graphical, container, false);
        listView = v.findViewById(R.id.listViewCountry);
        searchView = v.findViewById(R.id.searchCountry);




        ar = new ArrayList<>();






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
                                String index = String.valueOf(i+1);
                                String cases = jsonObject.getString("cases");

                                JSONObject flagObject = (JSONObject) jsonObject.get("countryInfo");
                                String flagString =flagObject.getString("flag");

                                    Log.d("messi", flagString);


                                object = new MyObject();




                                object.countryName = countryName;
                                object.index = index;
                                object.totalCase = cases;
                                if(countryName.compareTo("MS Zaandam")==0){
                                    object.flagString = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/MS_Zaandam.JPG/1024px-MS_Zaandam.JPG";
                                }
                                else if(countryName.compareTo("Diamond Princess")==0)
                                {
                                    object.flagString = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Diamond_Princess_%28ship%2C_2004%29_-_cropped.jpg/800px-Diamond_Princess_%28ship%2C_2004%29_-_cropped.jpg";
                                }
                                else
                                object.flagString = flagString;


                                ar.add(object);



                            }
                            adp = new FinalAdapter(getActivity(),ar);
                            listView.setAdapter(adp);

                            searchView.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    adp.getFilter().filter(charSequence);
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });





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



        return v;
    }

    public class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;

        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


    public void updateBarcValue() {




    }

    public class MyObject
    {
        public String index, countryName , totalCase, flagString;
        public String getIndex()
        {
            return getIndex();
        }
        public String getFlagString(){
            return getFlagString();
        }
        public String getCountryName()
        {
            return getCountryName();
        }

        public String getTotalCase()
        {
            return getTotalCase();
        }


    }



    public class FinalAdapter extends BaseAdapter implements Filterable {

        public ArrayList<MyObject> itemsModelsl;
        public List<MyObject> itemsModelListFiltered;
        private Context context;

        public FinalAdapter(Context context, ArrayList<MyObject> itemsModelsl) {
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
            View view = getLayoutInflater().inflate(R.layout.list_item_country, null);

            ImageView flag = view.findViewById(R.id.flag);
            TextView index = view.findViewById(R.id.indexList);
            TextView country = view.findViewById(R.id.listTextCountry);
            TextView totalcases = view.findViewById(R.id.caseTextList);

            MyObject o ;
            o = itemsModelListFiltered.get(position);
            index.setText(o.index);
            country.setText(o.countryName);

            totalcases.setText(o.totalCase);


            Picasso.with(context).load(o.flagString).resize(60, 40).
                    centerCrop().into(flag);


            view.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                   startActivity(new Intent(getActivity(), each_country_data.class).putExtra("countryname",itemsModelListFiltered.get(position).countryName).putExtra("flagLink",itemsModelListFiltered.get(position).flagString));

                }
            });



            return view;
        }


        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if (constraint == null || constraint.length() == 0) {
                        filterResults.count = itemsModelsl.size();
                        filterResults.values = itemsModelsl;

                    } else {
                        List<MyObject> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for (MyObject itemsModel : itemsModelsl) {
                            if (itemsModel.countryName.toString().toLowerCase().contains(searchStr) || itemsModel.countryName.toString().toLowerCase().contains(searchStr)) {
                                resultsModel.add(itemsModel);


                            }
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }


                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (ArrayList) results.values;


                    notifyDataSetChanged();


                    Log.d("Result", String.valueOf(results.values));

                }
            };
            return filter;
        }
    }
}
