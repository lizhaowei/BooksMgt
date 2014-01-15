package org.lzw.booksmgt.utils;

public enum ReqType {
    GET_BOOKS,
    ADD_BOOK, ;

    public static ReqType getReqType(String s) {
        ReqType type = null;
        if (ReqParams.REQ_TYPE_GETBOOKS.equals(s)) {
            type = GET_BOOKS;
        } else if (ReqParams.REQ_TYPE_ADDBOOK.equals(s)) {
            type = ADD_BOOK;
        }
        return type;
    }
}
