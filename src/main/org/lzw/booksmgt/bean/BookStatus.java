package org.lzw.booksmgt.bean;

/**
 * created by zwli on Sep 12, 2013 Detailed comment
 */
public enum BookStatus {

    NOT_STAETED("还未开始"),
    DONE("已读完"),
    READING("阅读中"),
    NOT_FINISHED("未读完"),
    ABANDONED("已放弃");

    private String label;

    private BookStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static BookStatus getStatus(String label) {
        BookStatus s = null;
        if ("还未开始".equals(label)) {
            s = NOT_STAETED;
        } else if ("已读完".equals(label)) {
            s = DONE;
        } else if ("阅读中".equals(label)) {
            s = READING;
        } else if ("未读完".equals(label)) {
            s = NOT_FINISHED;
        } else if ("已放弃".equals(label)) {
            s = ABANDONED;
        }
        return s;
    }
}
