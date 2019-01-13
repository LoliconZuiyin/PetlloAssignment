package com.github.lookchunkuen.petlloassignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
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
    private static String URL_EDIT = "https://petllo.000webhostapp.com/UpdateUserProfile.php";
    private static String URL_UPLOAD = "https://petllo.000webhostapp.com/UploadPicture.php";

    private BottomNavigationView navigationView;

    private Button btn_photo_upload;
    private Bitmap bitmap;
    ImageView ProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        // Set up title name and enable back button
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        navigationView = findViewById(R.id.navigationView);
        //handle the bottom
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_adoption:
                        Toast.makeText(userProfile.this, "Action Adoption Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_training:
                        //Toast.makeText(HomeActivity.this, "Action Training Clicked", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), DogTraining.class);
                        startActivity(intent);
                        break;
                    case R.id.action_profile:
                        //Toast.makeText(HomeActivity.this, "Action Profile Clicked", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), userProfile.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

       // For upload button
        btn_photo_upload = findViewById(R.id.UserProfileUploadButton);

        btn_photo_upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               chooseFile();
            }
        });

        ProfileImage = findViewById(R.id.ProfileImage);

    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ProfileImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

            UploadPicture(this,URL_UPLOAD,getStringImage(bitmap)); //getId --parameter
        }
    }

    private void UploadPicture(Context context, String url, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

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
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(userProfile.this, message, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(userProfile.this, "Error" + response, Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("photo",photo);

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

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);

        return encodedImage;
    }



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

    // On Click Edit Pet Button
    public void EditPetName(View v){
        PetProfile PetP = new PetProfile();

        PetP.setPetName(PetName.getText().toString());
        PetP.setPetID(PetID.getText().toString());
        try {
            makeServiceCallEdit(this, URL_EDIT,PetP);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Volley for edit pet name(on click)
    public void makeServiceCallEdit(Context context, String url, final PetProfile PetP) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

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
                            progressDialog.dismiss();
                            Toast.makeText(userProfile.this, message, Toast.LENGTH_LONG).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(userProfile.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(userProfile.this, "Error" + response, Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
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

    // On Click Register Pet Button
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding...");
        progressDialog.show();

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
                            progressDialog.dismiss();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(userProfile.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(userProfile.this, "Error" + response, Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
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
