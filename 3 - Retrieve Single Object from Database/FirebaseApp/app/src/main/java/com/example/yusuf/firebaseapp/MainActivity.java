package com.example.yusuf.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Don't forget add firebase dependencies

    private TextView mDataValue;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataValue = (TextView) findViewById(R.id.dataValue);

        /*
        Database baglantimizi olusturuyoruz
        "name" adında bir cocuk referans olusturuyoruz
         */

        //Setting connection to Database (Type your custom url)
        mRef = new Firebase("https://fir-app-ce89f.firebaseio.com/");
        //Create a child ref name "name"
        Firebase childRef = mRef.child("name");

        // when "name" child updated show the changes
        // "name" degistigi zaman degisikligi ekrandaki TextViewda gosteriyoruz
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String dataValue = dataSnapshot.getValue().toString();
                mDataValue.setText(dataValue);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
