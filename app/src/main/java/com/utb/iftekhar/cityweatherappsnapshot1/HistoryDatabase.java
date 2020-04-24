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
//        internal storage, specific to app, to instantiate a cursor when query is called
        sqLiteDatabase=context.openOrCreateDatabase("History", MODE_PRIVATE,null);
        //In order to display items in the list, call setAdapter
        // to associate an adapter with the list
        historyList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(context,R.layout.rows,historyList);
    }

    public void updateListView() {
        //Runs the provided SQL and returns a Cursor over the result set, arguments in where clause.
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM history", null);
//        Returns the zero-based index for the given column name,
//          or -1 if the column doesn't exist.
        int searchIndex = c.getColumnIndex("search");
        if (c.moveToFirst()) {
            historyList.clear();
            do {
                historyList.add(c.getString(searchIndex));
            } while (c.moveToNext());
//            Notifies the attached observers that the underlying data
//          has been changed and any View reflecting the data set should refresh itself.
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS history (search VARCHAR)");
    }

    public void insertHistory(String search){
        String insertHistory="INSERT INTO history(search) VALUES(?)";
        if(!historyList.contains(search) && !search.equals("")){
//            Compiles an SQL statement into a reusable pre-compiled statement object.
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