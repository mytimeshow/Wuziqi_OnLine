package com.example.administrator.wuziqi;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class Myaplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"b8e2bad46c30cd6c0293392219c4d372");

    }
}
