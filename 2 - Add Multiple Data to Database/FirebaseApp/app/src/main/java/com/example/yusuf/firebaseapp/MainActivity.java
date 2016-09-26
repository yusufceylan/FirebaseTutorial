package com.example.yusuf.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    // Don't forget add firebase dependencies

    private EditText mDataValue;
    private EditText mDataKey;
    private Button mButtonAdd;

    private Firebase mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataValue = (EditText) findViewById(R.id.et_value);
        mDataKey = (EditText) findViewById(R.id.et_key);
        mButtonAdd = (Button) findViewById(R.id.add_btn);

        /*
        Database baglantimizi olusturuyoruz
        "Users" adında bir cocuk referansımız oldugundan ona baglanmak icin
        baglantinin sonuna ekliyoruz, Dashboardda üstüne tıklarsanız aynı linki goreceksiniz
         */

        //Setting connection to Database (Type your custom url)
        // We have a child name "Users"
        mRootRef = new Firebase("https://fir-app-ce89f.firebaseio.com/Users");

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dataKey = mDataKey.getText().toString();
                String dataValue = mDataValue.getText().toString();

                Firebase childRef = mRootRef.child(dataKey);
                childRef.setValue(dataValue);

                /*
                Kullanıcıdan bir key bir de value alıyoruz
                Key'i kullanarak bir child olusturuyoruz
                Deger olarak da aldıgımız Value'yi veriyoruz
                 */

                /*
                Getting key and value from user
                Create a child using key and setValue by using value
                 */

            }
        });

    }
}
