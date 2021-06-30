package com.example.VisualAnalysis;

import android.widget.TableRow;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Table {
    String organizationName;
    int vsiCount;
    int salesOutLateCount;
    int skuCount;
    int quantityCount;
    double totalSalesAmountAfterTax;


    public Table(String organizationName, int vsiCount, int salesOutLateCount, int skuCount, int quantityCount, double totalSalesAmountAfterTax) {
        this.organizationName = organizationName;
        this.vsiCount = vsiCount;
        this.salesOutLateCount = salesOutLateCount;
        this.skuCount = skuCount;
        this.quantityCount = quantityCount;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
    }

    public Table(String organizationName, int salesOutLateCount, int skuCount, int quantityCount, double totalSalesAmountAfterTax) {
        this.organizationName = organizationName;
        this.salesOutLateCount = salesOutLateCount;
        this.skuCount = skuCount;
        this.quantityCount = quantityCount;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getVsiCount() {
        return vsiCount;
    }

    public void setVsiCount(int vsiCount) {
        this.vsiCount = vsiCount;
    }

    public int getSalesOutLateCount() {
        return salesOutLateCount;
    }

    public void setSalesOutLateCount(int salesOutLateCount) {
        this.salesOutLateCount = salesOutLateCount;
    }

    public int getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(int skuCount) {
        this.skuCount = skuCount;
    }

    public int getQuantityCount() {
        return quantityCount;
    }

    public void setQuantityCount(int quantityCount) {
        this.quantityCount = quantityCount;
    }

    public double getTotalSalesAmountAfterTax() {
        return totalSalesAmountAfterTax;
    }

    public void setTotalSalesAmountAfterTax(double totalSalesAmountAfterTax) {
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
    }
}
