package com.example.dimple.fastpath;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
