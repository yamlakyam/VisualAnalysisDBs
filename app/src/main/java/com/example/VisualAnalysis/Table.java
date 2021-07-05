package com.example.VisualAnalysis;

public class Table {
    String organizationName;
    int vsiCount, sN, voucherN, TIN, itemCount;
    int salesOutLateCount;
    int skuCount;
    int quantityCount;
    double totalSalesAmountAfterTax;
    String vsi;
    String startTime, lastSeen;
    double subTotal;
    double VAT;
    int prospect, vCount, active;
    String totalSales;

    public Table(String organizationName, String startTime, String lastSeen, int vsiCount, int salesOutLateCount, int skuCount, int quantityCount, double totalSalesAmountAfterTax, int active, int prospect) {
        this.organizationName = organizationName;
        this.vsiCount = vsiCount;
        this.salesOutLateCount = salesOutLateCount;
        this.skuCount = skuCount;
        this.quantityCount = quantityCount;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
        this.startTime = startTime;
        this.lastSeen = lastSeen;
        this.active=active;
        this.prospect = prospect;
    }

    public Table(String vsi, int salesOutLateCount, int skuCount, int quantityCount, double totalSalesAmountAfterTax, int prospect, String lastSeen) {
        this.salesOutLateCount = salesOutLateCount;
        this.skuCount = skuCount;
        this.quantityCount = quantityCount;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
        this.vsi = vsi;
        this.lastSeen = lastSeen;
        this.prospect = prospect;
    }

    public Table(String vsi, int salesOutLateCount, String lastSeen, int vCount, String totalSales) {
        this.salesOutLateCount = salesOutLateCount;
        this.totalSales = totalSales;
        this.vsi = vsi;
        this.lastSeen = lastSeen;
        this.vCount = vCount;
    }

    public Table(int sN, int voucherN, int salesOutLateCount, int TIN, String dateNtime, int itemCount, double subTotal, double VAT, double totalSalesAmountAfterTax) {
        this.sN = sN;
        this.voucherN = voucherN;
        this.salesOutLateCount = salesOutLateCount;
        this.TIN = TIN;
        this.startTime = dateNtime;
        this.itemCount = itemCount;
        this.subTotal = subTotal;
        this.VAT = VAT;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
    }


    public Table(String organizationName, int vsiCount, int salesOutLateCount, int skuCount, int quantityCount, double totalSalesAmountAfterTax) {
        this.organizationName = organizationName;
        this.vsiCount = vsiCount;
        this.salesOutLateCount = salesOutLateCount;
        this.skuCount = skuCount;
        this.quantityCount = quantityCount;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
    }

    public Table(String vsi, int salesOutLateCount, int skuCount, int quantityCount, double totalSalesAmountAfterTax) {
        this.vsi = vsi;
        this.salesOutLateCount = salesOutLateCount;
        this.skuCount = skuCount;
        this.quantityCount = quantityCount;
        this.totalSalesAmountAfterTax = totalSalesAmountAfterTax;
    }


}
