package com.chejdj.wanandroid.network.bean.knowledgesystem;

import java.util.List;
/*
{
             "children": [],
             "courseId": 13,
             "id": 402,
             "name": "跨平台应用",
             "order": 145001,
             "parentChapterId": 293,
             "userControlSetTop": false,
             "visible": 1
 }
 */

public class PrimaryArticleDirectory {
    private List<SecondaryArticleDirectory> children;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<SecondaryArticleDirectory> getChildren() {
        return children;
    }
}
