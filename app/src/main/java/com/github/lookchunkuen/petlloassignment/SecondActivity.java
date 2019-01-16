package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {



   // public static final String TAG_REPLY = "my.edu.tarc.lab2intent.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Owner Details");

    }
/*
        //Create an instance of Intent
        Intent intent = getIntent();    //Who called me?
        if(intent.hasExtra(DogDetail1.TAG_MESSAGE)){
            TextView textViewMessage;
            textViewMessage = findViewById(R.id.textViewMessage);
            String stringMsg;
            stringMsg = intent.getStringExtra(DogDetail1.TAG_MESSAGE);
            textViewMessage.setText(stringMsg);
        }

    }

    public void sendReply(View view){
        //TODO: LINK UI to program, get the text from EditText
        EditText editTextReply;
        editTextReply = findViewById(R.id.editTextReply);
        if(TextUtils.isEmpty(editTextReply.getText())){
            editTextReply.setError(getString(R.string.error_reply));
            return;
        }
        String stringReply;
        stringReply = editTextReply.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(TAG_REPLY, stringReply);
        setResult(RESULT_OK);
        finish();

    }
    */
}
