package com.github.lookchunkuen.petlloassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DogDetail1 extends AppCompatActivity {
    int position;

    private TextView textViewName, textViewProfile, textViewCondition, textViewBody, textViewColor, textViewLocation, textViewPosted, textViewAdoptionFee;
    private String dogeName = "Steven";

    private Button button;
    private static String URL_GETDOG= "https://petllo.000webhostapp.com/select_pet.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail1);


        textViewName = findViewById(R.id.textViewName);
        textViewProfile = findViewById(R.id.textViewProfile);
        textViewCondition = findViewById(R.id.textViewCondition);
        textViewBody = findViewById(R.id.textViewBody);
        textViewColor = findViewById(R.id.textViewColor);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewPosted = findViewById(R.id.textViewPosted);
        textViewAdoptionFee = findViewById(R.id.textViewAdoptionFee);


        Intent intent = getIntent();
        position = intent.getIntExtra("DogPosition", 0);

        getSupportActionBar().setTitle("Dog Details "+ MainActivity.dogList.get(position).getName());

        button = findViewById(R.id.buttonContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity();

            }
        });

        GetDetail();
    }

    public void SecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

    }


    public void GetDetail(){
        try {
            makeServiceCall(this, URL_GETDOG, position);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final int position){
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try{
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if (success==1) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    textViewName.setText(jsonObject.getString("Name"));
                                    textViewProfile.setText(jsonObject.getString("Profile"));
                                    textViewCondition.setText(jsonObject.getString("Condition"));
                                    textViewBody.setText(jsonObject.getString("Body"));
                                    textViewColor.setText(jsonObject.getString("Color"));
                                    textViewLocation.setText(jsonObject.getString("Location"));
                                    textViewPosted.setText(jsonObject.getString("Posted"));
                                    textViewAdoptionFee.setText(jsonObject.getString("AdoptionFee"));


                                }else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error1: " + response, Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error2: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("dogeName", dogeName);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }






    public void sendMessage(View view){

            return;

        }


/*
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_REPLY_CODE){
            if(resultCode == RESULT_OK){
                TextView textViewReply;
                textViewReply = findViewById(R.id.textViewReply);
                String stringReply = data.getStringExtra(SecondActivity.TAG_REPLY);
                textViewReply.setText(stringReply);
            }
        }
    }
    */
}
