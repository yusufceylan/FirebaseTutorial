package com.example.yusuf.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Don't forget add firebase dependencies

    private ListView mListView;
    private ArrayList<String> mUserNames;

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database baglantimizi olusturuyoruz
        // Setting connection to Database (Type your custom url)
        mRef = new Firebase("https://fir-app-ce89f.firebaseio.com/");

        mUserNames = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, mUserNames);
        mListView.setAdapter(mArrayAdapter);

        // We have a child name "Users" and it has 3 user
        // "Users" isminde bir child'ımız var icerisinde 3 tane user var, onları ListView da gösterecegiz
        Firebase mRefChild = mRef.child("Users");

        // Show them in the listView
        mRefChild.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue().toString();
                mUserNames.add(value);
                mArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
