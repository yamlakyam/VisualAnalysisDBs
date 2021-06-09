package data;

import java.util.ArrayList;

public class TableData {
    private ArrayList<String> tableHeaders;
    private ArrayList<ArrayList<String>> tableValues;


    public ArrayList<String> getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(ArrayList<String> tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public ArrayList<ArrayList<String>> getTableValues() {
        return tableValues;
    }

    public void setTableValues(ArrayList<ArrayList<String>> tableValues) {
        this.tableValues = tableValues;
    }

    public TableData() {

    }

    public TableData(ArrayList<String> tableHeaders, ArrayList<ArrayList<String>> tableValues) {
        this.tableHeaders = tableHeaders;
        this.tableValues = tableValues;
    }
}
