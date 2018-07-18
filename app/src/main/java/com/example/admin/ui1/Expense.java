package com.example.admin.ui1;

/**
 * Created by ADMIN on 09-Jul-17.
 */
import java.io.Serializable;

/**
 * Created by ADMIN on 26-Jun-17.
 */

public class Expense implements Serializable {
    String todo;
    String date;
    String time;
    String notes;
    int id;
    Expense(String todo, String time, String date, String notes, int id){
        this.date=date;
        this.notes=notes;
        this.time=time;
        this.id=id;
        this.todo=todo;
    }
}