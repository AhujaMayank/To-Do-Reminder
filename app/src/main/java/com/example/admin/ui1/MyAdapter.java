package com.example.admin.ui1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ADMIN on 26-Jun-17.
 */

public class MyAdapter extends ArrayAdapter<Expense> {
    ArrayList<Expense> todolist;
    Context context;
    public MyAdapter(@NonNull Context context, ArrayList<Expense> todolist ) {
        super(context, 0);
        this.context=context;
        this.todolist=todolist;
    }
    @Override
    public int getCount() {
        return todolist.size();
    }

    static class ExpenseViewHolder{
        TextView todoTextView;
        TextView dateTextView;
        TextView timeTextView;
        ExpenseViewHolder(TextView todoTextView,TextView dateTextView,TextView timeTextView){
            this.todoTextView=todoTextView;
            this.dateTextView=dateTextView;
            this.timeTextView=timeTextView;

        }

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            TextView todoTextView=(TextView)convertView.findViewById(R.id.todoTextView);
            TextView dateTextView=(TextView)convertView.findViewById(R.id.dateTextView);
            TextView timeTextView=(TextView)convertView.findViewById(R.id.timeTextView);
            ExpenseViewHolder expenseViewholder=new ExpenseViewHolder(todoTextView,dateTextView,timeTextView);
            convertView.setTag(expenseViewholder);
        }
        Expense e=todolist.get(position);
        ExpenseViewHolder expenseViewholder=(ExpenseViewHolder) convertView.getTag();
        //we can make button here and call expenseViewholder.button.setonclicklistener
        expenseViewholder.todoTextView.setText(e.todo);
        expenseViewholder.dateTextView.setText(e.date);
        expenseViewholder.timeTextView.setText(e.time);
        return convertView;
    }
}