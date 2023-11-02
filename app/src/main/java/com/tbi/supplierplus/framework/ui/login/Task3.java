package com.tbi.supplierplus.framework.ui.login;

import java.util.ArrayList;

public class Task3<t> {
    public int State;
    public String Message;
    public String msg;
    ArrayList<t> Data;
    t Item;
    t UserInfo;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public t getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(t userInfo) {
        UserInfo = userInfo;
    }

    public t getItem() {
        return Item;
    }

    public void setItem(t item) {
        Item = item;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<t> getData() {
        return Data;
    }

    public void setData(ArrayList<t> data) {
        Data = data;
    }
}
