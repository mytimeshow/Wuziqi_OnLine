package com.example.administrator.wuziqi;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class Match extends BmobObject{
    private String userA;
    private String userB;
    private List<Point> blackArray=new ArrayList<>();
    private List<Point> whiteArray=new ArrayList<>();
    private String result;
    private int room;
    private String phone;

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserA() {
        return userA;
    }

    public void setUserA(String userA) {
        this.userA = userA;
    }

    public String getUserB() {
        return userB;
    }

    public void setUserB(String userB) {
        this.userB = userB;
    }

    public List<Point> getBlackArray() {
        return blackArray;
    }

    public void setBlackArray(List<Point> blackArray) {
        this.blackArray = blackArray;
    }

    public List<Point> getWhiteArray() {
        return whiteArray;
    }

    public void setWhiteArray(List<Point> whiteArray) {
        this.whiteArray = whiteArray;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }





}
