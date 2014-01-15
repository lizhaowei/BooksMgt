package org.lzw.booksmgt.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.lzw.booksmgt.bean.BookBean;
import org.lzw.booksmgt.dao.BookJdbcDao;

/**
 * created by zwli on Sep 11, 2013 Detailed comment
 */
public class BooksDataImporter {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");

    public static void importBooksDatas(String csvFileName) {
        File csvFile = new File(csvFileName);
        if (!csvFile.exists()) {
            System.err.println("### ERROR ### - There is no CSV file [" + csvFileName + "]");
            return;
        }

        List<BookBean> books = new ArrayList<BookBean>();
        getBooks(csvFile, books);

        insertBooks(books);
    }

    private static void insertBooks(List<BookBean> books) {
        BookJdbcDao dao = new BookJdbcDao();
        try {
            dao.addAll(books);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getBooks(File csvFile, List<BookBean> books) {
        BookBean book = null;
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "GBK"));
                String line;
                String[] arr;
                while (br.ready()) {
                    line = br.readLine();
                    if (checkStr(line)) {
                        // line = new String(line.getBytes("UTF8"));
                        int index = 0;
                        arr = line.split(",");
                        // System.err.println(">>> " + Arrays.toString(arr));
                        // System.err.println(">>> " + arr.length);
                        // if (arr.length == 11 || arr.length == 10) {
                        // System.err.println(">>> " + line);
                        try {
                            book = new BookBean();
                            book.setIsbn(arr[index++]);
                            book.setTitle(arr[index++]);
                            book.setSubtitle(arr[index++]);
                            book.setAuthor(arr[index++]);
                            book.setSpecification(arr[index++]);
                            book.setPages(getPages(arr[index++]));
                            book.setPublisher(arr[index++]);
                            book.setPublicationDate(getDate(arr[index++]));
                            book.setBookStatus(arr[index++]);
                            if (10 == arr.length) {
                                book.setEvaluation(Integer.valueOf(arr[index++]).toString());
                            } else {
                                book.setEvaluation(String.valueOf(-1));
                            }
                            book.setBookRemark(arr[index++]);

                            books.add(book);
                        } catch (Exception e) {
                            // System.err.println(">>> " + line);
                            // e.printStackTrace();
                        }
                        // } else {
                        // System.err.println(">>> " + line);
                        // }
                    }
                }
            } finally {
                if (null != br) {
                    br.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getPages(String str) {
        if (checkStr(str)) {
            return Integer.valueOf(str).toString();
        }
        return null;
    }

    private static Date getDate(String str) throws ParseException {
        if (checkStr(str)) {
            return SDF.parse(str);
        }
        return null;
    }

    private static boolean checkStr(String str) {
        return (null != str && !str.isEmpty());
    }

    public static void main(String[] args) {
        String csvFileName = "D:/Work/BooksMgt/datas/datas_20130917.csv";
        csvFileName = "D:/Work/BooksMgt/datas/b.csv";
        importBooksDatas(csvFileName);
    }
}
