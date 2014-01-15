package org.lzw.booksmgt.io;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.lzw.booksmgt.bean.BookBean;
import org.lzw.booksmgt.dao.BookJdbcDao;

/**
 * created by zwli on 2013-9-17 Detailed comment
 */
public class ExcelDataImporter {

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
        Workbook workbook = null;
        try {
            try {
                workbook = Workbook.getWorkbook(csvFile);
                Sheet sheet = workbook.getSheet(0);
                Cell[] cells = null;
                for (int i = 0; i < sheet.getRows(); i++) {
                    cells = sheet.getRow(i);
                    book = getBook(cells);

                    if (null != book) {
                        System.out.println("### " + book);
                        books.add(book);
                    }
                }
            } finally {
                if (null != workbook) {
                    workbook.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BookBean getBook(Cell[] cells) throws ParseException {
        if (14 != cells.length) {
            // System.out.println(cells.length);
            return null;
        }
        // StringBuilder info = new StringBuilder();
        // info.append("### {");
        // for (Cell cell : cells) {
        // info.append(cell.getContents()).append(", ");
        // }
        // info.setLength(info.length() - 2);
        // info.append("}");
        // System.err.println(info);

        BookBean book = new BookBean();
        int index = 0;
        book.setIsbn(cells[index++].getContents());
        book.setTitle(cells[index++].getContents());
        book.setSubtitle(cells[index++].getContents());
        book.setBookDetail(cells[index++].getContents());
        book.setAuthor(cells[index++].getContents());
        book.setAuthorDetail(cells[index++].getContents());
        book.setSpecification(cells[index++].getContents());
        book.setPages(getNumber(cells[index++].getContents(), ""));
        book.setPublisher(cells[index++].getContents());
        book.setPublicationDate(getDate(cells[index++]));
        book.setBookStatus(cells[index++].getContents());
        book.setEvaluation(getNumber(cells[index++].getContents(), "-1"));
        book.setBookRemark(cells[index++].getContents());
        book.setAvailableToLend(cells[index++].getContents());
        return book;
    }

    private static Date getDate(Cell cell) {
        if (CellType.DATE == cell.getType()) {
            return ((DateCell) cell).getDate();
        }
        return null;
    }

    private static String getNumber(String toCheck, String defaultValue) {
        if (checkStr(toCheck)) {
            try {
                return Integer.valueOf(toCheck).toString();
            } catch (NumberFormatException e) {}
        }
        return defaultValue;

        // return toCheck;
    }

    private static boolean checkStr(String str) {
        return (null != str && !str.isEmpty());
    }

    public static void main(String[] args) {
        String csvFileName = "D:/Work/BooksMgt/datas/datas_20130917.csv";
        csvFileName = "D:/Work/BooksMgt/datas/c.xls";
        importBooksDatas(csvFileName);
    }
}
