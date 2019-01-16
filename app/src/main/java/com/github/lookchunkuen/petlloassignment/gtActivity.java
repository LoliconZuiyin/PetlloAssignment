package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class gtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_train);
        getSupportActionBar().setTitle("Group Training");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
