package com.utb.iftekhar.cityweatherappsnapshot1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by syedy on 02-04-2020.
 */

public class Tab1Fragment extends Fragment{

    private static final String TAG="Tab1Fragment";
    private MainActivity mainActivity;
    private Button getWeatherButton;
    private EditText searchCityByName;
    private TextView weatherResultTextView=null;
    private DownloadTask downloadTask;
    private String weatherResults;

    private String cityNameSearched;
    HistoryDatabase historyDatabase;
    SQLiteStatement statement;
    private SQLiteDatabase sqLiteDatabase;

    public Tab1Fragment(){
        Log.i("Tab1Fragmrnt","called");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.tab1_frag, container, false);
        getWeatherButton=(Button)view.findViewById(R.id.getWeatherButton);
        searchCityByName=(EditText)view.findViewById(R.id.searchCityByName);
        weatherResultTextView=(TextView)view.findViewById(R.id.weatherResult);
        mainActivity=new MainActivity();
        historyDatabase=HistoryDatabase.getInstance(getActivity());
        sqLiteDatabase=historyDatabase.sqLiteDatabase;
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadTask=new DownloadTask(new DownloadTask.AsyncResponse() {

                    @Override
                    public void processFinish(String output) {

                        if(!output.equals("")){
                            weatherResultTextView.setText(output);
                        }else{
                            weatherResultTextView.setText("");
                        }
                        historyDatabase.updateListView();
                    }

                });

                cityNameSearched=searchCityByName.getText().toString().trim();
                historyDatabase.insertHistory(cityNameSearched);

                historyDatabase.updateListView();

                try {

                    String encodedCityName= URLEncoder.encode(cityNameSearched,"UTF-8" );
//                    downloadTask.execute("https://openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=5a4e565cd06d9f08c34f6a7237b2e240");
                    downloadTask.execute("https://openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=439d4b804bc8187953eb36d2a8c26a02");

                } catch (Exception e) {
                    e.printStackTrace();
                }



                InputMethodManager mgr=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(searchCityByName.getWindowToken(), 0);

            }
        });
        return view;
    }

}
