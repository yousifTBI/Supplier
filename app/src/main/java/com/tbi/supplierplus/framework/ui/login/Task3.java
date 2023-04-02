package com.tbi.supplierplus.framework.ui.login;

import java.util.ArrayList;

public class Task3<t> {
    public int State;
    public String Message;
    ArrayList<t> Data;
    t Item;
    t UserInfo;

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
