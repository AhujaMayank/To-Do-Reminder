package com.example.admin.ui1;

//import android.support.design.widget.BaseTransientBottomBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ArrayList<Expense> todolist;
    MyAdapter myadapter;
    ListView listview;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TODO APP");


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
                i.putExtra(ExpenseDetailActivity.myid,"2");
                startActivityForResult(i,1);

            }
        });
        listview=(ListView)findViewById(R.id.listview);
        todolist=new ArrayList<>();
        myadapter=new MyAdapter(this,todolist);
        updatelist();
        listview.setAdapter(myadapter);
        listview.setNestedScrollingEnabled(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
                i.putExtra(IntentConstants.EXPENSE_TITLE,todolist.get(position).todo);
                i.putExtra(IntentConstants.EXPENSE_DATE,todolist.get(position).date);
                i.putExtra(IntentConstants.EXPENSE_TIME,todolist.get(position).time);
                i.putExtra(IntentConstants.EXPENSE_NOTES,todolist.get(position).notes);
                i.putExtra(ExpenseOpenHelper.EXPENSE_NAME,todolist.get(position).todo);
                i.putExtra(ExpenseOpenHelper.EXPENSE_DATE,todolist.get(position).date);
                i.putExtra(ExpenseOpenHelper.EXPENSE_TIME,todolist.get(position).time);
                i.putExtra(ExpenseOpenHelper.EXPENSE_NOTES,todolist.get(position).notes);
                i.putExtra(ExpenseDetailActivity.myid,"1");
                //   i.putExtra(ExpenseDetailActivity.posi,""+todolist.get(position).id);
                pos=position;
                i.putExtra(ExpenseOpenHelper.EXPENSE_ID,todolist.get(position).id);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){

                updatelist();
            }
            else if(resultCode==RESULT_CANCELED){
                //
            }
            else if(resultCode==2){
                int myuseid=data.getIntExtra(ExpenseOpenHelper.EXPENSE_ID,-1);

                changeTextView(myuseid);
            }
            else if(resultCode==3){

                todolist.remove(pos);
                myadapter.notifyDataSetChanged();
            }
        }
    }

    private void changeTextView(int myuseid) {
        ExpenseOpenHelper expenseOpenHelper=new ExpenseOpenHelper(this);
        SQLiteDatabase database=expenseOpenHelper.getReadableDatabase();
        Cursor cursor=database.query(ExpenseOpenHelper.EXPENSE_TABLENAME,null,ExpenseOpenHelper.EXPENSE_ID+"="+myuseid,null,null,null,null);
        cursor.moveToNext();
        String todo=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_NAME));
        String time=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
        String date=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
        String notes=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_NOTES));
        int id=cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
        Expense e=new Expense(todo,time,date,notes,id);
        todolist.set(pos,e);
        myadapter.notifyDataSetChanged();
    }

    private void updatelist() {
        ExpenseOpenHelper expenseOpenHelper=new ExpenseOpenHelper(this);
        todolist.clear();
        SQLiteDatabase database=expenseOpenHelper.getReadableDatabase();
        Cursor cursor=database.query(ExpenseOpenHelper.EXPENSE_TABLENAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String todo=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_NAME));
            String time=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
            String date=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
            String notes=cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_NOTES));
            int id=cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
            Expense e=new Expense(todo,time,date,notes,id);
            todolist.add(e);
        }
        myadapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(R.id.add==id){
            Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
            i.putExtra(ExpenseDetailActivity.myid,"2");
            startActivityForResult(i,1);

        }
        else if(id==R.id.aboutus){
            Intent i=new Intent();
            i.setAction(Intent.ACTION_VIEW);
            Uri uri=Uri.parse("http://www.google.com");
            i.setData(uri);
            startActivity(i);
        }
        else if(id==R.id.contactus){
            Intent i=new Intent();
            i.setAction(Intent.ACTION_CALL);
            Uri uri=Uri.parse("tel:85860");
            i.setData(uri);
            startActivity(i);
        }
        else if(id==R.id.feedback){
            Intent i=new Intent();
            i.setAction(Intent.ACTION_SENDTO);
            Uri uri=Uri.parse("mailto:divij.aggarwal@yahoo.in");
            i.setData(uri);
            if(i.resolveActivity(getPackageManager())!=null) {
                startActivity(i);
            }
        }
        return true;
    }
}



