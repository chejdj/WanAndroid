package com.chejdj.wanandroid.db;

import android.content.Context;

import com.chejdj.wanandroid.db.entity.MyObjectBox;

import io.objectbox.BoxStore;

public class ObjectBox {
    private static BoxStore boxStore;
    public static void init(Context context){
        boxStore= MyObjectBox.builder().androidContext(context).build();
    }
    public static BoxStore getBoxStore(){
        return boxStore;
    }
}
