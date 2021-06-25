package com.example.VisualAnalysis;

import android.widget.TableRow;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Table {
    String branchName;
    String percentage;
    String total;
    TableRow tableRow;
    Date date;
    String day;
    String lastSeen;


    String val1;
    String val2;
    String val3;
    String val4;
    String val5;
    String val6;
    String val7;
    String val8;
    String val9;

    public Table(String branchName, String percentage, String total, TableRow tableRow, Date date, String day) {
        this.branchName = branchName;
        this.percentage = percentage;
        this.total = total;
        this.tableRow = tableRow;
        this.date = date;
        this.day = day;
//        this.lastSeen=lastSeen;
    }

    public Table(String branchName, String percentage, String total, TableRow tableRow, String val1) {
        this.branchName = branchName;
        this.percentage = percentage;
        this.total = total;
        this.tableRow = tableRow;
        this.val1 = val1;
    }

    public Table(String branchName, String percentage, String total, TableRow tableRow, String val1, String val2) {
        this.branchName = branchName;
        this.percentage = percentage;
        this.total = total;
        this.tableRow = tableRow;
        this.val1 = val1;
        this.val2 = val2;
    }

    public Table(String branchName, String percentage, String total, TableRow tableRow, String val1, String val2, String val3) {
        this.branchName = branchName;
        this.percentage = percentage;
        this.total = total;
        this.tableRow = tableRow;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    public Table(String branchName, String percentage, String total, TableRow tableRow, String val1, String val2, String val3, String val4) {
        this.branchName = branchName;
        this.percentage = percentage;
        this.total = total;
        this.tableRow = tableRow;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
    }

    public Table(String branchName, String percentage, String total, TableRow tableRow, String val1, String val2, String val3, String val4, String val5) {
        this.branchName = branchName;
        this.percentage = percentage;
        this.total = total;
        this.tableRow = tableRow;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public TableRow getTableRow() {
        return tableRow;
    }

    public void setTableRow(TableRow tableRow) {
        this.tableRow = tableRow;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getVal4() {
        return val4;
    }

    public void setVal4(String val4) {
        this.val4 = val4;
    }

    public String getVal5() {
        return val5;
    }

    public void setVal5(String val5) {
        this.val5 = val5;
    }
}
