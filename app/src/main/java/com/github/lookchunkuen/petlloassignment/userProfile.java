package com.github.lookchunkuen.petlloassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.Random;

public class userProfile extends AppCompatActivity {
    // Create Custom adapter
    ListView lst;
    String[] dogName={"Dog1","Dog2","Dog3"};
    String[] dogID ={"The dog id1 is executed" , "The dog id2 is executed","The dog id3 is executed"};
    Integer[] imgID ={R.drawable.dog, R.drawable.dog, R.drawable.dog};

  //  private Button button;
    private Button AddPetButton;
    TextView PetID;
    TextView PetName;
    SessionManager sessionManager;
    TextView UserProfileUsername;
    TextView UserProfileEmail;

    private static String URL_REGISTER = "https://petllo.000webhostapp.com/PetProfileRegister.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        // For pet list
        //lst = findViewById(R.id.PetList);

        // To open next activity
     //   button = (Button)findViewById(R.id.UserProfileToDogProfile);
     //   button.setOnClickListener(new View.OnClickListener(){
        //    @Override
          //  public void onClick(View v){
              //  openDogProfileActivity();
        //    }
       // });

        // For add dog button
        PetID = findViewById(R.id.UserProfilePetID);
        PetName = findViewById(R.id.UserProfilePetName);
        AddPetButton = findViewById(R.id.UserProfileAddPetButton);
        UserProfileUsername = findViewById(R.id.UserProfileUsername);
        UserProfileEmail = findViewById(R.id.UserProfileEmail);

        sessionManager = new SessionManager(this);


        HashMap<String, String> user = sessionManager.GetUserDetail();
        String username = user.get((sessionManager.NAME));
        String email = user.get((sessionManager.EMAIL));
        UserProfileUsername.setText(username);
        UserProfileEmail.setText(email);
    }
    // Method for open new activity
   // public void  openDogProfileActivity(){
     //   Intent intent = new Intent(userProfile.this, DogProfileActivity.class);
      //  startActivity(intent);
   // }

    private String generateString(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890".toCharArray();
        // Set capacity
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i<length; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    // On Click
    public void Register(View v){
        PetID.setText(generateString(15));
        PetProfile PetP = new PetProfile();

        PetP.setPetName(PetName.getText().toString());
        PetP.setPetID(PetID.getText().toString());
            try {
                makeServiceCall(this, URL_REGISTER,PetP);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    // Volley, called by register(on click)
    public void makeServiceCall(Context context, String url, final PetProfile PetP) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        String message = jsonObject.getString("message");
                        if (success==1) {
                            Toast.makeText(userProfile.this, message, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(userProfile.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(userProfile.this, "Error" + response, Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("PetID", PetP.getPetID());
                    params.put("PetName", PetP.getPetName());

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PetListAdapter extends ArrayAdapter<String> {

        Context context;
        public PetListAdapter(Context context, String[] dogName) {
            super(context,R.layout.petlist, R.id.DogID,dogName);
            this.context =context;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.petlist,parent,false);

            ImageView myImage = row.findViewById(R.id.PetImage);
            TextView myDogID = row.findViewById(R.id.DogID);
            TextView myDogName = row.findViewById(R.id.DogName);

            return super.getView(position, convertView, parent);
        }
    }*/
}
