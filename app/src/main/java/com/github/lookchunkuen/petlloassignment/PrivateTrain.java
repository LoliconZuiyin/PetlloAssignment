package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PrivateTrain extends Fragment {

    private Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_private_train,container,false);
        button = view.findViewById(R.id.ptButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DogTraining.fragmentManager.beginTransaction().replace(R.id.TrainingContainer,new GroupTrain(),null).commit();
            }
        });

        return view;
    }
}
