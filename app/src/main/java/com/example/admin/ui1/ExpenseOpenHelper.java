package com.example.admin.ui1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ADMIN on 30-Jun-17.
 */

public class ExpenseOpenHelper extends SQLiteOpenHelper{
    public final static String EXPENSE_TABLENAME="Todo";
    public final static String EXPENSE_ID="id";
    public final static String EXPENSE_NAM      E="name";
    public final static String EXPENSE_NOTES="notes";
    public final static String EXPENSE_DATE="date";
    public final static String EXPENSE_TIME="time";
    public ExpenseOpenHelper(Context context){
        super(context,"Todo.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table " + EXPENSE_TABLENAME +" ( " + EXPENSE_ID + " integer primary key autoincrement, " + EXPENSE_NAME +" text, "
                + EXPENSE_DATE + " text, " + EXPENSE_NOTES + " text, " + EXPENSE_TIME + " text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
