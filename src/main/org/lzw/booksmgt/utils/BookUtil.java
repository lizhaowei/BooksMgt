package org.lzw.booksmgt.utils;

import java.util.HashMap;
import java.util.Map;

public class BookUtil {

    private static Map<String, String> columnsToSort;
    static {
        initColumns();
    }

    private static void initColumns() {
        columnsToSort = new HashMap<String, String>();
        columnsToSort.put(FieldKey.TITLE, ColumnName.TITLE);
        columnsToSort.put(FieldKey.AUTHOR, ColumnName.AUTHOR);
        columnsToSort.put(FieldKey.SPECIFICATION, ColumnName.SPECIFICATION);
        columnsToSort.put(FieldKey.PUBLISHER, ColumnName.PUBLISHER);
        columnsToSort.put(FieldKey.BOOK_STATUS, ColumnName.BOOK_STATUS);
    }

    public static String getColumnByField(String fieldName) {
        String column = null;
        if (checkStr(fieldName)) {
            column = columnsToSort.get(fieldName);
        }
        if (!checkStr(column)) {
            column = ColumnName.TITLE;
        }
        return column;
    }

    public static boolean checkStr(String str) {
        if (null == str || str.isEmpty()) {
            return false;
        }
        return true;
    }

    public static int getPages(int totalRecord, int maxResult) {
        int pages = totalRecord % maxResult == 0 ? totalRecord / maxResult : totalRecord / maxResult + 1;
        return pages;
    }

    public static int getStart(int currentPage, int pageSize) {
        int posion = (currentPage - 1) * pageSize;
        return posion;
    }

    public static void main(String[] args) {
        int currentPage = 1;
        int pageSize = 3;
        for (int i = 0; i < 5; i++) {
            currentPage = i + 1;
            int start = getStart(currentPage, pageSize);
            System.err.println(start);
        }
    }
}
