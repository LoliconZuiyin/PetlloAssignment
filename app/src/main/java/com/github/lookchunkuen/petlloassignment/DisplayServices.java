package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DisplayServices extends Fragment {

   public Button button1;
   public Button button2;
   public Button button3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_services,container,false);

        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DogTraining.fragmentManager.beginTransaction().replace(R.id.TrainingContainer,new PrivateTrain(),null).commit();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DogTraining.fragmentManager.beginTransaction().replace(R.id.TrainingContainer,new GroupTrain(),null).commit();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DogTraining.fragmentManager.beginTransaction().replace(R.id.TrainingContainer,new DogWalking(),null).commit();
            }
        });

        return view;
    }
}
