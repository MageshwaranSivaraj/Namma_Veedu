package com.example.i312458.namma_veedu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by I312458 on 2016-06-12.
 */
public class ExpensesManagement extends SQLiteOpenHelper {

    public static final String DBName = "MyExpenses.db";
    public static final int Version=1;

    public static final String Table_Name="MonthlyExpenses";
    public static final String id="ID";
    public static final String name="Name";
    public static final String amount="Amount";
    public static final String description="Description";

    private SQLiteDatabase Expenses;

    public ExpensesManagement(Context context) {
        super(context, DBName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Query = "CREATE TABLE "+ Table_Name +"("+
                id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                name + " TEXT NOT NULL, " +
                amount + " REAL NOT NULL,"+
                description + " TEXT NOT NULL"+
                ")";
        db.execSQL(Query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void OpenDB()
    {
        Expenses = getWritableDatabase();
    }

    public void closeDB()
    {
        if(Expenses!=null && Expenses.isOpen()) {
            Expenses.close();
        }
    }

    public long Insert(int ID,String Name, Double Amount, String Description)
    {
        ContentValues values=new ContentValues();
        if(ID != -1)
            values.put(id,ID);
        values.put(name,Name);
        values.put(amount,Amount);
        values.put(description,Description);

        return Expenses.insert(Table_Name,null,values);
    }

    public long Update(int ID, String Name, Double Amount, String Description)
    {
        ContentValues values=new ContentValues();
        values.put(name,Name);
        values.put(amount,Amount);
        values.put(description,Description);

        String where = id +"="+ID;

        return Expenses.update(Table_Name,values,where,null);
    }

    public Cursor getAllRecords(){


        String query="SELECT * FROM " + Table_Name;
        return Expenses.rawQuery(query,null);
    }
}
