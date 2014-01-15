package org.lzw.booksmgt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.lzw.booksmgt.bean.BookBean;
import org.lzw.booksmgt.utils.ColumnName;

/**
 * created by zwli on Sep 11, 2013 Detailed comment
 */
public class BookJdbcDao {

    private static final Logger log = Logger.getLogger(BookJdbcDao.class);
    private static final String TABLE_NAME = " booksmgt_ ";

    private static final String SQL_UPDATE = "update "
            + TABLE_NAME
            + " set isbn=?, title=?, subtitle=?, author=?, specification=?, pages=?, publisher=?, publication_date=?, book_status=?, evaluation=?, book_remark=?, book_detail=?, author_detail=?, avaliable_to_lend=? where id=?";
    private static final String SQL_SELECT_ALL = "select * from " + TABLE_NAME + "";
    private static final String SQL_SELECT_COUNT = "select count(*) as total_count from " + TABLE_NAME + "";
    private static final String SQL_INSERT = "insert into "
            + TABLE_NAME
            + "(isbn, title, subtitle, author, specification, pages, publisher, publication_date, book_status, evaluation, book_remark, book_detail, author_detail, avaliable_to_lend) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    public BookJdbcDao() {
    }

    private BookBean getBookBean(ResultSet results) throws SQLException {
        BookBean b;
        b = new BookBean();
        b.setId(results.getInt(ColumnName.ID));
        b.setIsbn(results.getString(ColumnName.ISBN));
        b.setTitle(results.getString(ColumnName.TITLE));
        b.setSubtitle(results.getString(ColumnName.SUBTITLE));
        b.setAuthor(results.getString(ColumnName.AUTHOR));
        b.setSpecification(results.getString(ColumnName.SPECIFICATION));
        b.setPages(results.getString(ColumnName.PAGES));
        b.setPublisher(results.getString(ColumnName.PUBLISHER));
        b.setPublicationDate(results.getDate(ColumnName.PUBLICATION_DATE));
        b.setBookStatus(results.getString(ColumnName.BOOK_STATUS));
        b.setEvaluation(results.getString(ColumnName.EVALUATION));
        b.setBookRemark(results.getString(ColumnName.BOOK_REMARK));
        b.setBookDetail(results.getString(ColumnName.BOOK_DETAIL));
        b.setAuthorDetail(results.getString(ColumnName.AUTHOR_DETAIL));
        b.setAvailableToLend(results.getString(ColumnName.AVALIABLE_TO_LEND));
        return b;
    }

    private void insert(BookBean b) throws SQLException {
        int index = 1;
        pstat.setString(index++, b.getIsbn());
        pstat.setString(index++, b.getTitle());
        pstat.setString(index++, b.getSubtitle());
        pstat.setString(index++, b.getAuthor());
        pstat.setString(index++, b.getSpecification());
        pstat.setString(index++, b.getPages());
        pstat.setString(index++, b.getPublisher());
        if (null != b.getPublicationDate()) {
            pstat.setDate(index++, new java.sql.Date(b.getPublicationDate().getTime()));
        } else {
            pstat.setDate(index++, null);
        }
        pstat.setString(index++, b.getBookStatus());
        pstat.setString(index++, b.getEvaluation());
        pstat.setString(index++, b.getBookRemark());
        pstat.setString(index++, b.getBookDetail());
        pstat.setString(index++, b.getAuthorDetail());
        pstat.setString(index++, b.getAvailableToLend());
        pstat.executeUpdate();
    }

    public void update(BookBean b) throws SQLException {
        try {
            try {
                prepareDatabase(SQL_UPDATE);
                int index = 1;
                pstat.setString(index++, b.getIsbn());
                pstat.setString(index++, b.getTitle());
                pstat.setString(index++, b.getSubtitle());
                pstat.setString(index++, b.getAuthor());
                pstat.setString(index++, b.getSpecification());
                pstat.setString(index++, b.getPages());
                pstat.setString(index++, b.getPublisher());
                if (null != b.getPublicationDate()) {
                    pstat.setDate(index++, new java.sql.Date(b.getPublicationDate().getTime()));
                } else {
                    pstat.setDate(index++, null);
                }
                pstat.setString(index++, b.getBookStatus());
                pstat.setString(index++, b.getEvaluation());
                pstat.setString(index++, b.getBookRemark());
                pstat.setString(index++, b.getBookDetail());
                pstat.setString(index++, b.getAuthorDetail());
                pstat.setString(index++, b.getAvailableToLend());
                pstat.setInt(index++, b.getId());
                pstat.executeUpdate();
                conn.commit();
            } finally {
                close();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        }
    }

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstance().getConnection();
    }

    public void addAll(List<BookBean> books) throws SQLException {
        try {
            try {
                prepareDatabase(SQL_INSERT);
                for (BookBean b : books) {
                    System.out.println("# " + b);
                    insert(b);
                }
                conn.commit();
            } finally {
                close();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        }
    }

    public void add(BookBean b) throws SQLException {
        try {
            try {
                prepareDatabase(SQL_INSERT);
                insert(b);
                conn.commit();
            } finally {
                close();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        }
    }

    public void delete(Integer bookId) throws SQLException {
        String sql = "delete from " + TABLE_NAME + " where id=?";
        try {
            try {
                prepareDatabase(sql);
                pstat.setInt(1, bookId);
                pstat.executeUpdate();
                conn.commit();
            } finally {
                close();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        }

    }

    private void prepareDatabase(String sql) throws SQLException {
        prepareDatabase(sql, true);
    }

    private void prepareDatabase() throws SQLException {
        prepareDatabase(null, false);
    }

    private void prepareDatabase(String sql, boolean usePstat) throws SQLException {
        conn = getConnection();
        if (usePstat) {
            pstat = conn.prepareStatement(sql);
        } else {
            stat = conn.createStatement();
        }
    }

    public Integer findTotalCount() throws SQLException {
        Integer b = null;
        try {
            prepareDatabase(SQL_SELECT_COUNT);
            rs = pstat.executeQuery();
            if (rs.next()) {
                b = rs.getInt(ColumnName.TOTAL_COUNT);
            }
            return b;
        } finally {
            close();
        }
    }

    public Integer findTotalCountByCondition(String column, String condition) throws SQLException {
        StringBuilder sql = new StringBuilder(SQL_SELECT_COUNT);
        sql.append(" where 1=1 ");
        if (isStrColumn(column)) {
            sql.append(" and ");
            sql.append(column);
            sql.append(" like '%");
            sql.append(condition);
            sql.append("%'");
        }

        // LogUtil.info("SQL - " + sql.toString());
        log.info("SQL - " + sql.toString());

        Integer totalCount = Integer.valueOf(0);
        try {
            prepareDatabase();
            rs = stat.executeQuery(sql.toString());
            if (rs.next()) {
                totalCount = rs.getInt(ColumnName.TOTAL_COUNT);
            }
        } finally {
            close();
        }
        return totalCount;
    }

    public List<BookBean> listBooksByCondition(String column, String condition, Integer start, Integer pageSize)
            throws SQLException {
        StringBuilder sql = new StringBuilder(SQL_SELECT_ALL);
        sql.append(" where 1=1 ");
        if (isStrColumn(column)) {
            sql.append(" and ");
            sql.append(column);
            sql.append(" like '%");
            sql.append(condition);
            sql.append("%' order by ");
            sql.append(column);
            if (null != start && null != pageSize) {
                sql.append(" limit ");
                sql.append(start.intValue());
                sql.append(", ");
                sql.append(pageSize.intValue());
            }
        }

        // LogUtil.info("SQL - " + sql.toString());
        log.info("SQL - " + sql.toString());

        List<BookBean> books = new ArrayList<BookBean>();
        BookBean b = null;
        try {
            prepareDatabase();
            rs = stat.executeQuery(sql.toString());
            while (rs.next()) {
                b = getBookBean(rs);
                books.add(b);
            }
        } finally {
            close();
        }
        return books;
    }

    private static boolean isStrColumn(String column) {
        List<String> strList = new ArrayList<String>();
        strList.add(ColumnName.TITLE);
        strList.add(ColumnName.AUTHOR);
        strList.add(ColumnName.SPECIFICATION);
        strList.add(ColumnName.PUBLISHER);
        strList.add(ColumnName.BOOK_STATUS);
        return strList.contains(column);
    }

    public List<BookBean> listBooks(Integer start, Integer pageSize) throws SQLException {
        StringBuilder sql = new StringBuilder(SQL_SELECT_ALL);
        sql.append(" limit ");
        sql.append(start);
        sql.append(", ");
        sql.append(pageSize);
        List<BookBean> books = new ArrayList<BookBean>();
        BookBean b = null;
        try {
            prepareDatabase();
            rs = stat.executeQuery(sql.toString());
            while (rs.next()) {
                b = getBookBean(rs);
                books.add(b);
            }
        } finally {
            close();
        }
        return books;
    }

    public List<BookBean> listBooks() throws SQLException {
        List<BookBean> books = new ArrayList<BookBean>();
        BookBean b = null;
        String sql = SQL_SELECT_ALL + " order by title";
        try {
            prepareDatabase(sql);
            rs = pstat.executeQuery();
            while (rs.next()) {
                b = getBookBean(rs);
                books.add(b);
            }
        } finally {
            close();
        }
        return books;
    }

    public List<BookBean> listBooks(String sort, String dir, Integer start, Integer pageSize) throws SQLException {
        StringBuilder sql = new StringBuilder(SQL_SELECT_ALL);
        sql.append(" order by ").append(sort).append(" ").append(dir);
        if (null != start && null != pageSize) {
            sql.append(" limit ");
            sql.append(start.intValue());
            sql.append(", ");
            sql.append(pageSize.intValue());
        }

        // LogUtil.info("SQL - " + sql.toString());
        log.info("SQL - " + sql.toString());

        List<BookBean> books = new ArrayList<BookBean>();
        BookBean b = null;
        try {
            prepareDatabase(sql.toString());
            rs = pstat.executeQuery();
            while (rs.next()) {
                b = getBookBean(rs);
                books.add(b);
            }
        } finally {
            close();
        }
        return books;
    }

    public BookBean findByPrimaryKey(Integer bookId) throws SQLException {
        String sql = SQL_SELECT_ALL + " where id=?";
        BookBean b = null;
        try {
            prepareDatabase(sql);
            pstat.setInt(1, bookId);
            rs = pstat.executeQuery();
            if (rs.next()) {
                b = getBookBean(rs);
            }
        } finally {
            close();
        }
        return b;
    }

    private void close() throws SQLException {
        if (null != rs) {
            rs.close();
            rs = null;
        }
        if (null != pstat) {
            pstat.close();
            pstat = null;
        }
        if (null != stat) {
            stat.close();
            stat = null;
        }
        if (null != conn) {
            conn.close();
            conn = null;
        }
    }

    public static void main(String[] args) {
        BookJdbcDao dao = new BookJdbcDao();
        // System.out.println(dao.findAll().size());
        // System.err.println(dao.findTotalCount());
        // List<BookBean> books = dao.findByCondition(ColumnName.TITLE, "食堂");
        // print(books);
        // System.err.println(dao.findTotalCountByCondition(ColumnName.TITLE, "食堂"));
        try {
            print(dao.listBooks(0, 25));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void print(List<BookBean> books) {
        int n = 1;
        for (BookBean book : books) {
            System.err.println(n++ + " > " + book);
        }
    }
}
