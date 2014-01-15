package org.lzw.booksmgt.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.lzw.booksmgt.bean.BookBean;
import org.lzw.booksmgt.core.BookJson;
import org.lzw.booksmgt.exception.BooksMgtException;
import org.lzw.booksmgt.services.BooksMgtService;
import org.lzw.booksmgt.utils.BookUtil;
import org.lzw.booksmgt.utils.ReqParams;
import org.lzw.booksmgt.utils.ReqType;
import org.lzw.booksmgt.utils.SortDir;

/**
 * created by Li Zhaowei on 2013-9-29 上午8:31:59 Detailed comment *
 */
public class BooksMgtServlet extends HttpServlet {

    private static final long serialVersionUID = -1733628618348234930L;
    private static final Logger log = Logger.getLogger(BooksMgtServlet.class);

    public BooksMgtServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String queryString = request.getQueryString();
        String strReqType = request.getParameter(ReqParams.REQ_TYPE);
        // LogUtil.info("reqType: " + strReqType);
        log.info("reqType: " + strReqType);

        ReqType reqType = ReqType.getReqType(strReqType);
        switch (reqType) {
        case GET_BOOKS:
            // LogUtil.info("queryString: " + queryString);
            log.info("queryString: " + queryString);

            doGetBooks(request, response);
            break;
        case ADD_BOOK:
            doAddBook(request, response);
            break;
        }
        // LogUtil.info("------------------------------------------");
        log.info("------------------------------------------");
    }

    private void doAddBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Set<String> formParams = paramMap.keySet();
        // LogUtil.info(request.getParameterMap().toString());
        StringBuilder info = new StringBuilder("{");
        for (String param : formParams) {
            info.append("\"").append(param).append("\" : \"").append(request.getParameter(param)).append("\",");
        }
        // info.subSequence(0, info.length()-1);
        info.setLength(info.length() - 1);
        info.append("}");
        // LogUtil.info(info.toString());
        log.info(info.toString());

        BookBean bean = BookJson.jsonToBean(info.toString());
        // System.out.println(">>> " + bean);
        boolean operationSucceeded = true;
        String error = null;
        try {
            BookBean result = BooksMgtService.getInstance().addOrUpdate(bean);
            if (null == result) {
                operationSucceeded = false;
            }
        } catch (BooksMgtException e) {
            // LogUtil.error(e.getMessage());
            log.error(e.getMessage(), e);

            operationSucceeded = false;
            error = e.getMessage();
        }
        info = new StringBuilder("{");
        if (operationSucceeded) {
            info.append("\"success\":true,\"msg\":\"老婆你已经修改成功了^^\"");
        } else {
            info.append("\"success\":false,\"msg\":\"" + error + "\"");
        }
        info.append("}");
        response.getWriter().write(info.toString());
    }

    private void doGetBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String strPage = request.getParameter(ReqParams.PAGE);
        // String strStart = request.getParameter(ReqParams.START);
        String strLimit = request.getParameter(ReqParams.LIMIT);
        String strSort = request.getParameter(ReqParams.SORT);
        String strDir = request.getParameter(ReqParams.DIR);
        String callback = request.getParameter(ReqParams.CALLBACK);

        int page = BookUtil.checkStr(strPage) ? Integer.valueOf(strPage) : 1;
        int limit = BookUtil.checkStr(strLimit) ? Integer.valueOf(strLimit) : 25;
        int start = BookUtil.getStart(page, limit);
        SortDir dir = BookUtil.checkStr(strDir) ? SortDir.valueOf(strDir) : SortDir.ASC;
        String sort = BookUtil.getColumnByField(strSort);

        // LogUtil.info("sort=" + sort + ", dir=" + dir.name() + ", start=" + start + ", limit=" + limit);
        log.info("sort=" + sort + ", dir=" + dir.name() + ", start=" + start + ", limit=" + limit);

        String json;
        try {
            json = BooksMgtService.getInstance().getBooks(sort, dir, start, limit);
        } catch (BooksMgtException e) {
            // LogUtil.error(e.getMessage());
            log.error(e.getMessage(), e);
            json = "";
        }

        String toReturn = callback + "(" + json + ");";
        // LogUtil.info(toReturn);

        response.getWriter().write(toReturn);
    }
}
