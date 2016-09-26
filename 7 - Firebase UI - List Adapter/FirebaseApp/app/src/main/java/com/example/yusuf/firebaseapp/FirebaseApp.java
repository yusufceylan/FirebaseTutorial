package com.example.yusuf.firebaseapp;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

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

        // Onceki versiyonda asagidaki sekilde kullanıyorduk ancak yeni versiyonda degismis
        // Previous version

        // Firebase.setAndroidContext(this);

        // Yeni kullanim bu sekilde (Benim uygulamanın adı da FirebaseApp olunca bu sekilde düzenledi)
        // New version
        if(!com.google.firebase.FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }
}
