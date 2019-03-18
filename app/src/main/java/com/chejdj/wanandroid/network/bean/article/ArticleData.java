package com.chejdj.wanandroid.network.bean.article;

import java.util.ArrayList;
import java.util.List;

public class ArticleData {
    private int curPage;
    private List<Article> datas;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    public ArticleData() {
        this.datas = new ArrayList<>();
    }

    public List<Article> getListData() {
        return datas;
    }

    public int getCurPage() {
        return curPage;
    }

    public int getOffset() {
        return offset;
    }

    public boolean isOver() {
        return over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getSize() {
        return size;
    }

    public int getTotal() {
        return total;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setDatas(List<Article> datas) {
        this.datas = datas;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
