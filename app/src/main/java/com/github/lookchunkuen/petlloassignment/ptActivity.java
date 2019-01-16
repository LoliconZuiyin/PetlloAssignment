package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_private_train);
        getSupportActionBar().setTitle("Private Training");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*            TextView textViewTitle = findViewById(R.id.text1);
            TextView textViewFees = findViewById(R.id.text2);
            TextView textViewDesc = findViewById(R.id.text3);
            TextView textViewFDesc = findViewById(R.id.text4);


            textViewTitle.setText(trainService.getCourseName());
            textViewFees.setText("Fees: RM " + trainService.getCourseFees());
            textViewDesc.setText("Problem: " + trainService.getCourseDescription());
            textViewFDesc.setText("");

            //Button button = (Button) view.findViewById(R.id.button1);
            //button.setText(trainService.getCourseName());

            *//*TextView testing = (TextView) view.findViewById(R.id.testing);
            testing.setText(trainService.getCourseName());*//*

            return view;*/
    }
}
