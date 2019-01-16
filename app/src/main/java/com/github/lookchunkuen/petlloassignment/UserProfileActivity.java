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
import com.jgabrielfreitas.core.BlurImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

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

    private Button btn_photo_upload;
    private Bitmap bitmap;
    CircleImageView ProfileImage;
    BlurImageView myBlurImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Set up title name and enable back button
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set blur for blur image
        myBlurImage = findViewById(R.id.BlurbgImage);
        myBlurImage.setBlur(2);


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
                            Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }else{
                            Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(UserProfileActivity.this, "Error" + response, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(UserProfileActivity.this, "Error" + response, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(UserProfileActivity.this, "Error" + response, Toast.LENGTH_LONG).show();
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
    public void Logout(View v){
        sessionManager.Logout();
    }

}
