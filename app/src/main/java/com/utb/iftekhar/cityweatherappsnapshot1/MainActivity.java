package com.utb.iftekhar.cityweatherappsnapshot1;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private SectionsPageAdapter mySectionPageAdapter;
    private ViewPager viewPager;
//    private DataForTabs dataForTabs;
    private Tab1Fragment tab1Fragment;
    static Tab2Fragment tab2Fragment;
    String input="";
    public static SharedPreferences sharedPreferences;
    static  ArrayList<String> arrayList=new ArrayList<>();
    SharedPreferences.Editor editor;
    Button button;
    public static SQLiteDatabase sqLiteDatabase;
   static HistoryDatabase historyDatabase;
    private static final int NUM_PAGES = 3;
    TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Creating an instance of HistoryDatabase singleton class which
        contains methods for database creation and performing CRUD operations
        */
        historyDatabase=HistoryDatabase.getInstance(this);
        historyDatabase.createTable();
        historyDatabase.updateListView();

        //Creating object for Fragments
        tab1Fragment=new Tab1Fragment();
        tab2Fragment=new Tab2Fragment();

        mySectionPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
        //set up the ViewPager with the sections adaptor
        viewPager=(ViewPager)findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(),"Home");
        adapter.addFragment(new Tab2Fragment(),"History");
        adapter.addFragment(new Tab3Fragment(),"About");
        viewPager.setAdapter(adapter);

    }

}
