package com.github.lookchunkuen.petlloassignment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyCustomListAdapter extends ArrayAdapter<Dog> {

    Context mCtx;
    int resource;
    List<Dog> dogList;

    public MyCustomListAdapter(Context mCtx, int resource, List<Dog> dogList){
        super(mCtx, resource, dogList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.my_list_item, null);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewProfile = view.findViewById(R.id.textViewProfile);
        ImageView imageView = view.findViewById(R.id.imageViewDog);



        Dog dog = dogList.get(position);

        textViewName.setText(dog.getName());
        textViewProfile.setText(dog.getProfile());
        imageView.setImageDrawable(mCtx.getResources().getDrawable(dog.getImage()));



        return view;

    }
}
