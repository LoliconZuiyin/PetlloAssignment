package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class dwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dog_walking);
        getSupportActionBar().setTitle("Dog Walking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
