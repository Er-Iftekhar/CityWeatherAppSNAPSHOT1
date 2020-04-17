package com.utb.iftekhar.cityweatherappsnapshot1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by syedy on 02-04-2020.
 */

public class Tab2Fragment extends Fragment {
    private static final String TAG="Tab2Fragment";
    private Button button;
    private static  final String CITY_NAME_SEARCHED="cityNameSearched";
    private ArrayList<String> historyList;
    private ListView historyListView;
    private ArrayAdapter<String> arrayAdapter;
    private SharedPreferences sharedPreferences;
    private SQLiteDatabase sqLiteDatabase;
    ViewPager viewPager;
    String cityNameSearched="";
    MainActivity mainActivity;
    HistoryDatabase historyDatabase;



public Tab2Fragment(){
    Log.i("Tab2Fragmentconstructor","called");
}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tab2_frag, container, false);
        historyListView=view.findViewById(R.id.historyList);
        button=view.findViewById(R.id.getWeatherButton);
        historyDatabase=HistoryDatabase.getInstance(getActivity());
        sqLiteDatabase=historyDatabase.sqLiteDatabase;
        arrayAdapter=historyDatabase.arrayAdapter;
        historyList=historyDatabase.historyList;
        historyListView.setAdapter(arrayAdapter);

        historyDatabase.updateListView();
        arrayAdapter.notifyDataSetChanged();

        historyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                historyDatabase.deleteHistoryItem(position);
                return false;
            }
        });

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), historyList.get(position), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
 }
}
