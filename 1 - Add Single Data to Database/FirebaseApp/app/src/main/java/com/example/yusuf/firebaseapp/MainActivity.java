package com.example.yusuf.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private Button mSendData;
    private Firebase mRef;

//    Dont forget to add firebase dependencies

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Contexti veriyoruz
        // Set the context
        Firebase.setAndroidContext(this);

        // Database baglantimizi olusturuyoruz, (kendi urlinizi vermeyi unutmayın)
        // Setting connection to Database (Type your custom url)
        mRef = new Firebase("https://fir-app-ce89f.firebaseio.com/");


        mSendData = (Button) findViewById(R.id.send_data);

        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase mRefChild = mRef.child("Name");
                mRefChild.setValue("Yusuf");

                /*
                Bir Child yani cocuk referans olusturup "Name" ismini veriyoruz
                Olusturdugumuz referansa deger olarak da "Yusuf" dedik
                 */

                /*
                We created a child ref name "Name", then set value "Yusuf"
                 */


                /*
                Firebase dashboard'unda Rules kısmında degisikleri yapmayı unutmayın
                        ".read": "auth == null",
                        ".write": "auth == null"
                Aksi takdirde giris yapmadan veri ekleyemezsiniz
                 */

                /*
                Dont forget to change database rules from firebase dashboard
                For this time, rules should be;

                    ".read": "auth == null",
                    ".write": "auth == null"

                Otherwise you can't add data without auth
                */

            }
        });
    }
}
