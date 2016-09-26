package com.example.yusuf.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Don't forget add firebase dependencies

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database baglantimizi olusturuyoruz
        // Setting connection to Database (Type your custom url)

        mRef = new Firebase("https://fir-app-ce89f.firebaseio.com/");

		// Retrive multiple data
        // Çoklu datamıza erisiyoruz

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Key ve Value ikisi de String o yüzden String, String kullanıyoruz
                //Key and Value, both String
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                //Key'imizi vererek o Key'e ait Value'yi alıyoruz
                //Keys of our Data
                String name = map.get("Name");
                String age = map.get("Age");
                String job = map.get("Job");

                //Log olarak bastıyoruz
                //Print data
                Log.v("Value","Name: " + name);
                Log.v("Value","Age: " + age);
                Log.v("Value","Job: " + job);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
