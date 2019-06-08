package com.chejdj.wanandroid.db.entity;


import com.chejdj.wanandroid.network.bean.article.Article;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CollectArticleDB {
    @Id
    private long id;
    private String link;
    private String title;
    private String author;
    private long time;
    private String accountName;

    public CollectArticleDB(String link, String title, long time, String author, String accountName) {
        this.link = link;
        this.title = title;
        this.time = time;
        this.author = author;
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CollectArticleDB(Article article) {
        this.link = article.getLink();
        this.title = article.getTitle();
        this.author = article.getAuthor();
        this.time = article.getPublishTime();
    }

    public CollectArticleDB() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {

        return id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public long getTime() {
        return time;
    }
}
