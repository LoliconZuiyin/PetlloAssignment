package com.github.lookchunkuen.petlloassignment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class DogTraining extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_training);
        getSupportActionBar().setTitle("Dog Training");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //not extends fragment gua, thats y need this supp
        fragmentManager = getSupportFragmentManager();

        // wheter container is available or not
        if(findViewById(R.id.TrainingContainer)!=null){

            //activity is resumed,fragment is added
            // will overlapped fragment if forget this
            if(savedInstanceState!=null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //create fragment from where u wan lo
            /*DisplayServices displayServices = new DisplayServices();
            fragmentTransaction.add(R.id.TrainingContainer,displayServices,null);
            fragmentTransaction.commit();*/

            TrainServiceFragment trainServiceFragment = new TrainServiceFragment();
            fragmentTransaction.add(R.id.TrainingContainer,trainServiceFragment,null);
            fragmentTransaction.commit();
        }
    }
}
