package com.example.admin.ui1;

/**
 * Created by ADMIN on 09-Jul-17.
 */
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ADMIN on 26-Jun-17.
 */

public class ExpenseDetailActivity extends AppCompatActivity{
    String title;
    String date1;
    String time;
    String notes;
    String position;
    EditText expensedetailname;
    EditText expensedetailtime;
    EditText expensedetailnotes;
    EditText expensedetaildate;
    public static String myid;
    //public static  String posi;
    int useid;
    long date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensedetail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        expensedetailtime=(EditText)findViewById(R.id.expensedetailtime);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TODO DETAILS");
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        String str=expensedetailtime.getText().toString();
        if(str!=""){
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=expensedetailtime.getText().toString();
                setalarm(str);

            }
        });}
        expensedetailname=(EditText)findViewById(R.id.expensedetailname);

        expensedetaildate=(EditText)findViewById(R.id.expensedetaildate);
        expensedetailnotes=(EditText)findViewById(R.id.expensedetailnotes);
        Button submit=(Button)findViewById(R.id.expensedetailsubmitbutton);
        submit.setText("SAVE DETAILS");
        final Intent i=getIntent();
        title=i.getStringExtra(IntentConstants.EXPENSE_TITLE);
        time=i.getStringExtra(IntentConstants.EXPENSE_TIME);
        date1=i.getStringExtra(IntentConstants.EXPENSE_DATE);
        notes=i.getStringExtra(IntentConstants.EXPENSE_NOTES);
        position=i.getStringExtra(IntentConstants.EXPENSE_POSITION);
        expensedetailname.setText(title);
        expensedetaildate.setText(date1);
        expensedetailtime.setText(time);
        expensedetailnotes.setText(notes);
        myid=i.getStringExtra(ExpenseDetailActivity.myid);
        //    useid=Integer.parseInt(i.getStringExtra(ExpenseDetailActivity.posi));
        if(myid.charAt(0)=='2'){
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newtitle=expensedetailname.getText().toString();
                    String newtime=expensedetailtime.getText().toString();
                    String newdate=expensedetaildate.getText().toString();
                    String newnotes=expensedetailnotes.getText().toString();
                    ExpenseOpenHelper expenseOpenHelper=new ExpenseOpenHelper(ExpenseDetailActivity.this);
                    SQLiteDatabase database= expenseOpenHelper.getWritableDatabase();
                    ContentValues cv=new ContentValues();
                    cv.put(ExpenseOpenHelper.EXPENSE_NAME,newtitle);
                    cv.put(ExpenseOpenHelper.EXPENSE_TIME,newtime);
                    cv.put(ExpenseOpenHelper.EXPENSE_DATE,newdate);
                    cv.put(ExpenseOpenHelper.EXPENSE_NOTES,newnotes);
                    database.insert(ExpenseOpenHelper.EXPENSE_TABLENAME,null,cv);
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
        if(myid.charAt(0)=='1') {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newtitle = expensedetailname.getText().toString();
                    String newtime = expensedetailtime.getText().toString();
                    String newdate = expensedetaildate.getText().toString();
                    String newnotes = expensedetailnotes.getText().toString();
                    int dataid = i.getIntExtra(ExpenseOpenHelper.EXPENSE_ID, -1);
                    ExpenseOpenHelper expenseOpenHelper = new ExpenseOpenHelper(ExpenseDetailActivity.this);
                    SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(ExpenseOpenHelper.EXPENSE_NAME, newtitle);
                    cv.put(ExpenseOpenHelper.EXPENSE_TIME, newtime);
                    cv.put(ExpenseOpenHelper.EXPENSE_DATE, newdate);
                    cv.put(ExpenseOpenHelper.EXPENSE_NOTES, newnotes);
                    i.putExtra(ExpenseOpenHelper.EXPENSE_ID, dataid);
                    database.update(ExpenseOpenHelper.EXPENSE_TABLENAME, cv, ExpenseOpenHelper.EXPENSE_ID + "=" + dataid, null);
                    setResult(2, i);
                    finish();
                }
            });
        }
        expensedetaildate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                int dte=newCalendar.get(Calendar.DATE);
                showDatePicker(ExpenseDetailActivity.this, year, month, dte);
            }
        });
        expensedetailtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ExpenseDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        expensedetailtime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }
    public void setalarm(String str){



        String [] arr=str.split(":");
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.i("error",arr[0]);
        Date dat = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        Log.i("date",dat.toString());
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(arr[0]));
        cal_alarm.set(Calendar.MINUTE,Integer.parseInt(arr[1]));
        cal_alarm.set(Calendar.SECOND,0);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
        }

        Intent myIntent = new Intent(ExpenseDetailActivity.this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ExpenseDetailActivity.this, 0, myIntent, 0);

        manager.set(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(), pendingIntent);
    }

    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        date = calendar.getTime().getTime();
                        // Setting date selected in the edit text
                        expensedetaildate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(R.id.delete==id){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("delete");
            builder.setCancelable(false);
            View v=getLayoutInflater().inflate(R.layout.dialog2,null);
            final TextView tv=(TextView)v.findViewById(R.id.dialogTextView2);
            tv.setText("are you sure you want to delete");
            builder.setView(v);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ExpenseOpenHelper expenseOpenHelper=new ExpenseOpenHelper(ExpenseDetailActivity.this);
                    SQLiteDatabase database= expenseOpenHelper.getWritableDatabase();
                    // Intent i=new Intent();
                    int dataid=getIntent().getIntExtra(ExpenseOpenHelper.EXPENSE_ID,-1);
                    database.delete(ExpenseOpenHelper.EXPENSE_TABLENAME,ExpenseOpenHelper.EXPENSE_ID+"="+dataid,null);
                    setResult(3);
                    finish();

                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        return true;
    }



    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

}
