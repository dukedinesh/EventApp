package com.example.dinesh.eventapp;

import android.app.Application;

public class Event extends Application {

    private String someVariable, userName;

    @Override
    public void onCreate() {
        super.onCreate();



    }
    public String getSomeVariable()
    {
        return someVariable;
    }

    public String setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
        return someVariable;
    }

    public String getUserName() {
        return userName;
    }

    public String setUserName(String userName) {
        this.userName = userName;
        return userName;
    }
}
