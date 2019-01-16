package com.github.lookchunkuen.petlloassignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrainServiceFragment extends Fragment {

    public static final String TAG = "my.edu.tarc.gg";
    private ListView listView;
    private ProgressDialog pDialog;
    private static String GET_URL = "https://petllo.000webhostapp.com/Training%20service.php";

    private List<TrainService> TrainServiceList;
    private TrainServiceFragment.TrainServiceAdapter adapter;
    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displayservice, container, false);
        listView = (ListView) view.findViewById(R.id.trainserviceid);
        pDialog = new ProgressDialog(getContext());
        TrainServiceList = new ArrayList<>();
        downloadEvent(getContext(), GET_URL);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), ptActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CourseName", TrainServiceList.get(position).getCourseName());
                bundle.putString("CourseFees", TrainServiceList.get(position).getCourseFees());
                bundle.putString("CourseDescription", TrainServiceList.get(position).getCourseDescription());
                bundle.putString("FullDescription", TrainServiceList.get(position).getFullDescription());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        return view;
    }

    private void downloadEvent(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Syncing...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            TrainServiceList.clear();
                            for (int i = 0; i <response.length(); i++) {
                                JSONObject ServiceResponse = (JSONObject) response.get(i);
                                String coursename = ServiceResponse.getString("CourseName");
                                String coursefees = ServiceResponse.getString("CourseFees");
                                String coursedescription = ServiceResponse.getString("CourseDescription");
                                String fulldescription = ServiceResponse.getString("FullDescription");

                                TrainService trainService = new TrainService(coursename,coursefees,coursedescription,fulldescription);
                                TrainServiceList.add(trainService);
                            }
                            loadEvent();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                });

        // Set the tag on the request.
       // jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadEvent() {
        adapter = new TrainServiceAdapter(getActivity(), R.layout.abc, TrainServiceList);
        listView.setAdapter(adapter);
        if (TrainServiceList != null) {
            int size = TrainServiceList.size();
            if (size > 0)
                Toast.makeText(getContext(), "No. of record : " + size + ".", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "No record found.", Toast.LENGTH_SHORT).show();
        }
    }

    class TrainServiceAdapter extends ArrayAdapter<TrainService> {

        private TrainServiceAdapter(Activity context, int resource, List<TrainService> list) {
            super(context, resource, list);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TrainService trainService = getItem(i);
            view = getLayoutInflater().inflate(R.layout.abc, viewGroup, false);

          TextView textViewTitle = (TextView) view.findViewById(R.id.text1);
          TextView textViewFees = (TextView) view.findViewById(R.id.text2);
          TextView textViewDesc = (TextView) view.findViewById(R.id.text3);
          TextView textViewFDesc = (TextView) view.findViewById(R.id.text4);


           textViewTitle.setText(trainService.getCourseName());
           textViewFees.setText("Fees: RM " + trainService.getCourseFees());
           textViewDesc.setText("Problem: " + trainService.getCourseDescription());
           textViewFDesc.setText("");

            //Button button = (Button) view.findViewById(R.id.button1);
            //button.setText(trainService.getCourseName());

            /*TextView testing = (TextView) view.findViewById(R.id.testing);
            testing.setText(trainService.getCourseName());*/

            return view;
        }
    }
}

