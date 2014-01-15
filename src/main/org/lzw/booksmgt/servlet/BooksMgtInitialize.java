package org.lzw.booksmgt.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.lzw.booksmgt.core.Log4jInit;

public class BooksMgtInitialize extends HttpServlet {

    private static final long serialVersionUID = -9169796207202263187L;

    @Override
    public void init() throws ServletException {
        Log4jInit.initLog4j();
    }
}
