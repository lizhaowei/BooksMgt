package org.lzw.booksmgt.bean;

import java.util.Date;

/**
 * created by zwli on Sep 11, 2013 Detailed comment
 */
public class BookBean {

    private Integer id;
    private String isbn;
    private String title;
    private String subtitle;
    private String bookDetail;
    private String author;
    private String authorDetail;
    private String specification;
    private String pages;
    private String publisher;
    private Date publicationDate;
    private String bookStatus;
    private String evaluation;
    private String bookRemark;
    private String availableToLend;

    @Override
    public String toString() {
        return "BookBean [id=" + this.id + ", isbn=" + this.isbn + ", title=" + this.title + ", subtitle=" + this.subtitle
                + ", bookDetail=" + this.bookDetail + ", author=" + this.author + ", authorDetail=" + this.authorDetail
                + ", specification=" + this.specification + ", pages=" + this.pages + ", publisher=" + this.publisher
                + ", publicationDate=" + this.publicationDate + ", bookStatus=" + this.bookStatus + ", evaluation="
                + this.evaluation + ", bookRemark=" + this.bookRemark + ", availableToLend=" + this.availableToLend + "]";
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSpecification() {
        return this.specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPages() {
        return this.pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getBookStatus() {
        return this.bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getBookRemark() {
        return this.bookRemark;
    }

    public void setBookRemark(String bookRemark) {
        this.bookRemark = bookRemark;
    }

    public String getBookDetail() {
        return this.bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

    public String getAuthorDetail() {
        return this.authorDetail;
    }

    public void setAuthorDetail(String authorDetail) {
        this.authorDetail = authorDetail;
    }

    public String getAvailableToLend() {
        return this.availableToLend;
    }

    public void setAvailableToLend(String availableToLend) {
        this.availableToLend = availableToLend;
    }

}
