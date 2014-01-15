package org.lzw.booksmgt.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lzw.booksmgt.bean.BookBean;
import org.lzw.booksmgt.utils.BookUtil;
import org.lzw.booksmgt.utils.FieldKey;

/**
 * created by zwli on 2013-9-12 Detailed comment
 */
public class BookJson {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static BookBean jsonToBean(String json) {
        BookBean toReturn = null;
        // json =
        // "{\"authorDetail\" : \"王朔 著\",\"pages\" : \"300\",\"avaliableToLend\" : \"可借\",\"bookDetail\" : \"王朔文集全15册在库\",\"publisher\" : \"北京十月文艺出版社\",\"evaluation\" : \"-1\",\"bookStatus\" : \"还未开始\",\"bookRemark\" : \"\",\"id\" : \"435\",\"author\" : \"\",\"title\" : \"一半是火焰 一半是海水\",\"isbn\" : \"9787530211625\",\"subtitle\" : \"\",\"specification\" : \"平装\",\"reqType\" : \"addBook\",\"publicationDate\" : \"2012-01-01\"}";
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        toReturn = new BookBean();
        if (jsonObj.has(FieldKey.ID)) {
            String strId = jsonObj.getString(FieldKey.ID);
            if (BookUtil.checkStr(strId)) {
                toReturn.setId(Integer.parseInt(strId));
            }
        }
        toReturn.setIsbn(jsonObj.getString(FieldKey.ISBN));
        toReturn.setTitle(jsonObj.getString(FieldKey.TITLE));
        toReturn.setSubtitle(jsonObj.getString(FieldKey.SUBTITLE));
        toReturn.setAuthor(jsonObj.getString(FieldKey.AUTHOR));
        toReturn.setSpecification(jsonObj.getString(FieldKey.SPECIFICATION));
        toReturn.setPages(jsonObj.getString(FieldKey.PAGES));
        toReturn.setPublisher(jsonObj.getString(FieldKey.PUBLISHER));

        String date = jsonObj.getString(FieldKey.PUBLICATION_DATE);
        if (null != date) {
            try {
                toReturn.setPublicationDate(SDF.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
                toReturn.setPublicationDate(null);
            }
        }

        toReturn.setBookStatus(jsonObj.getString(FieldKey.BOOK_STATUS));
        toReturn.setEvaluation(jsonObj.getString(FieldKey.EVALUATION));
        toReturn.setBookRemark(jsonObj.getString(FieldKey.BOOK_REMARK));
        toReturn.setBookDetail(jsonObj.getString(FieldKey.BOOK_DETAIL));
        toReturn.setAuthorDetail(jsonObj.getString(FieldKey.AUTHOR_DETAIL));
        toReturn.setAvailableToLend(jsonObj.getString(FieldKey.AVALIABLE_TO_LEND));

        return toReturn;
    }

    public static String beanToJson(BookBean b) {
        JSONObject json = getJsonObject(b);
        return json.toString();
    }

    private static JSONObject getJsonObject(BookBean b) {
        JSONObject json = new JSONObject();
        json.put(FieldKey.AUTHOR, b.getAuthor());
        json.put(FieldKey.BOOK_REMARK, b.getBookRemark());
        json.put(FieldKey.BOOK_STATUS, b.getBookStatus());
        json.put(FieldKey.EVALUATION, b.getEvaluation());
        json.put(FieldKey.ID, b.getId());
        json.put(FieldKey.ISBN, b.getIsbn());
        json.put(FieldKey.PAGES, b.getPages());
        if (null != b.getPublicationDate()) {
            json.put(FieldKey.PUBLICATION_DATE, b.getPublicationDate());
        } else {
            json.put(FieldKey.PUBLICATION_DATE, " - ");
        }
        json.put(FieldKey.PUBLISHER, b.getPublisher());
        json.put(FieldKey.SPECIFICATION, b.getSpecification());
        json.put(FieldKey.SUBTITLE, b.getSubtitle());
        json.put(FieldKey.TITLE, b.getTitle());
        json.put(FieldKey.BOOK_DETAIL, b.getBookDetail());
        json.put(FieldKey.AUTHOR_DETAIL, b.getAuthorDetail());
        json.put(FieldKey.AVALIABLE_TO_LEND, b.getAvailableToLend());
        return json;
    }

    public static String beansToJson(List<BookBean> books) {
        JSONArray jsonArray = new JSONArray();
        for (BookBean b : books) {
            jsonArray.put(getJsonObject(b));
        }
        return jsonArray.toString();
    }

    public static void main(String[] args) {
        // BookJdbcDao dao = new BookJdbcDao();
        // List<BookBean> books = dao.listBooks();
        // String json = toJsonFromBeans(books);
        // System.err.println(json);
        String json = "{\"totalCount\":\"473\",\"books\":[{\"id\":194,\"bookStatus\":\"参考书\",\"author\":\"小宽\",\"title\":\"100元吃遍北京 \",\"pages\":\"232\",\"isbn\":\"9787563721627\",\"subtitle\":\"\",\"specification\":\"平装\",\"publicationDate\":\" - \",\"publisher\":\"旅游教育出版社\",\"evaluation\":\"5\"}]}";
        json = "{\"totalCount\":\"6679\",\"topics\":[{\"title\":\"XTemplate with in EditorGridPanel\",\"threadid\":\"133690\",\"username\":\"kpr@emco\",\"userid\":\"272497\",\"dateline\":\"1305604761\",\"postid\":\"602876\",\"forumtitle\":\"Ext 3.x: Help\",\"forumid\":\"40\",\"replycount\":\"2\",\"lastpost\":\"1305857807\",\"lastposter\":\"kpr@emco\",\"excerpt\":\"Hi , \\n \\nI have an EditiorGridPanel whose one column i am using XTemplate to render and another Column is Combo Box Field .\\nWhen i render the EditorGri...\"}]}";
        JSONObject jo = new JSONObject(json);
        System.err.println(jo.toString(2));
    }
}
