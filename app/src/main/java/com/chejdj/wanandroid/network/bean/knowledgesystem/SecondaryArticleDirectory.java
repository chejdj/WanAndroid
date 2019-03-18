package com.chejdj.wanandroid.network.bean.knowledgesystem;

import android.os.Parcel;
import android.os.Parcelable;

import com.chejdj.wanandroid.network.bean.article.Article;

import java.util.List;

/*
{
                    "children": [],
                    "courseId": 13,
                    "id": 60,
                    "name": "Android Studio相关",
                    "order": 1000,
                    "parentChapterId": 150,
                    "userControlSetTop": false,
                    "visible": 1
                }
 */
public class SecondaryArticleDirectory implements Parcelable {
    private List<Article> children;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;

    protected SecondaryArticleDirectory(Parcel in) {
        children = in.createTypedArrayList(Article.CREATOR);
        courseId = in.readInt();
        id = in.readInt();
        name = in.readString();
        order = in.readInt();
        parentChapterId = in.readInt();
        userControlSetTop = in.readByte() != 0;
        visible = in.readInt();
    }

    public SecondaryArticleDirectory() {

    }

    public static final Creator<SecondaryArticleDirectory> CREATOR = new Creator<SecondaryArticleDirectory>() {
        @Override
        public SecondaryArticleDirectory createFromParcel(Parcel in) {
            return new SecondaryArticleDirectory(in);
        }

        @Override
        public SecondaryArticleDirectory[] newArray(int size) {
            return new SecondaryArticleDirectory[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(children);
        parcel.writeInt(courseId);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(order);
        parcel.writeInt(parentChapterId);
        parcel.writeByte((byte) (userControlSetTop ? 1 : 0));
        parcel.writeInt(visible);
    }
}
