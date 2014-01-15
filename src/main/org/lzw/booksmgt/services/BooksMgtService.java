package org.lzw.booksmgt.services;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.lzw.booksmgt.bean.BookBean;
import org.lzw.booksmgt.core.BookJson;
import org.lzw.booksmgt.dao.BookJdbcDao;
import org.lzw.booksmgt.exception.BooksMgtException;
import org.lzw.booksmgt.utils.ColumnName;
import org.lzw.booksmgt.utils.FieldKey;
import org.lzw.booksmgt.utils.SortDir;

/**
 * created by zwli on Sep 10, 2013 Detailed comment
 */
public class BooksMgtService {

    private static BooksMgtService instance;

    private BookJdbcDao dao;

    public static synchronized BooksMgtService getInstance() {
        if (instance == null) {
            instance = new BooksMgtService();
        }
        return instance;
    }

    private BooksMgtService() {
        dao = new BookJdbcDao();
    }

    public String getBooks(String sort, SortDir dir, int start, int pageSize) throws BooksMgtException {
        try {
            int totalCount = dao.findTotalCount();
            List<BookBean> books = dao.listBooks(sort, dir.name(), start, pageSize);
            return buildJson(totalCount, books);
        } catch (SQLException e) {
            throw new BooksMgtException(e.getMessage(), e);
        }
    }

    public BookBean addOrUpdate(BookBean b) throws BooksMgtException {
        if (null == b) {
            return null;
        }
        Integer id = b.getId();
        try {
            if (null == id) {
                dao.add(b);
            } else {
                dao.update(b);
            }
            return b;
        } catch (Exception e) {
            throw new BooksMgtException(e.getMessage(), e);
        }
    }

    private String buildJson(int totalCount, List<BookBean> books) {
        String jsonBooks = BookJson.beansToJson(books);
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"").append(FieldKey.TOTAL_COUNT).append("\":\"").append(totalCount).append("\",");
        json.append("\"").append(FieldKey.BOOKS).append("\":").append(jsonBooks);
        json.append("}");
        return json.toString();
    }

    public String getBooks(String column, String condition, int start, int pageSize) throws BooksMgtException {
        try {
            int totalCount = dao.findTotalCountByCondition(column, condition);
            List<BookBean> books = dao.listBooksByCondition(column, condition, start, pageSize);
            return buildJson(totalCount, books);
        } catch (SQLException e) {
            throw new BooksMgtException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String column = ColumnName.TITLE;
        String condition = "食堂";
        int start = 0;
        int max = 3;
        try {
            String json = BooksMgtService.getInstance().getBooks(column, condition, start, max);
            System.err.println("# " + json);
            JSONObject jo = new JSONObject(json);
            json = jo.toString(2);
            System.err.println(json);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BooksMgtException e) {
            e.printStackTrace();
        }
    }
}
