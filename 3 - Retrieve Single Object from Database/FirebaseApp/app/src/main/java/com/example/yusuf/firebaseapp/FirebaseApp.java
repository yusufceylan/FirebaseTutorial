package com.example.yusuf.firebaseapp;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Yusuf on 22.09.2016.
 */
public class FirebaseApp extends Application {

    /*
    Her sayfa icin tek tek context almaktansa
    Yeni bir class olusturarak her sayfada otomatik almasını saglıyoruz
     */

    /*
     Set the android Context for firebase,
     Previous example we used Firebase.setAndroidContext(this);
     But we don't want to do it in every page,
     So we created a application file to get context for every page
    */



    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        /*
        Manifest dosyasında application'ın altına
        android:name=".yourAppName" seklinde app adını girerek baglıyoruz
         */

        /*
         Wire up to manifest file
         Type android:name=".yourAppName" inside <application> in manifest
         */
    }
}
