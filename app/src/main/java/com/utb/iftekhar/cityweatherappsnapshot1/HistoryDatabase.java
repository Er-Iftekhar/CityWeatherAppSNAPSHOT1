package com.utb.iftekhar.cityweatherappsnapshot1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by syedy on 09-04-2020.
 */

public class HistoryDatabase {
    private static HistoryDatabase historyDatabase=null;
    SQLiteDatabase sqLiteDatabase;
    Context context;
    ArrayList<String> historyList;
    ArrayAdapter<String> arrayAdapter;
    SQLiteStatement statement;

    private HistoryDatabase(Context context){
        this.context=context;
        sqLiteDatabase=context.openOrCreateDatabase("History", MODE_PRIVATE,null);
        Log.i(" HistryDatbase created ",sqLiteDatabase.toString());
        historyList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(context,R.layout.rows,historyList);
    }

    public void updateListView() {
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM history", null);
        int searchIndex = c.getColumnIndex("search");
        if (c.moveToFirst()) {
            historyList.clear();
            do {
                historyList.add(c.getString(searchIndex));
            } while (c.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
    }

    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS history (search VARCHAR)");
    }

    public void insertHistory(String search){
        String insertHistory="INSERT INTO history(search) VALUES(?)";
        if(!historyList.contains(search) && !search.equals("")){
            statement=sqLiteDatabase.compileStatement(insertHistory);
            statement.bindString(1,search);
            statement.execute();
        }
        updateListView();
    }

    public void deleteHistoryItem(int position){
        String deleteHistory="DELETE FROM history WHERE search=?";
        statement=sqLiteDatabase.compileStatement(deleteHistory);
        statement.bindString(1,historyList.get(position));
        statement.execute();
        updateListView();
    }

    public static HistoryDatabase getInstance(Context context){
        if(historyDatabase==null){
            historyDatabase=new HistoryDatabase(context);
        }
        return  historyDatabase;
    }
}
