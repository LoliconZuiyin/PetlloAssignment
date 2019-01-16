package com.github.lookchunkuen.petlloassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {


    public static List<Dog> dogList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dogs Listing");



        dogList = new ArrayList<>();

        dogList.add(new Dog(R.drawable.dog1, "Kahler", "Female, 5 Years 2 Months"));
        dogList.add(new Dog(R.drawable.dog2, "Jacky", "Male, 2 Months"));
        dogList.add(new Dog(R.drawable.dog3, "Athenaz", "Female, 2 Months"));
        dogList.add(new Dog(R.drawable.dog4, "Keke", "Female, 2 Months"));
        dogList.add(new Dog(R.drawable.dog5, "Coco", "Female, 5 Months"));


        listView = (ListView) findViewById(R.id.listView);

        MyCustomListAdapter adapter = new MyCustomListAdapter(this, R.layout.my_list_item, dogList);

        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DogDetail1.class);
        intent.putExtra("DogPosition", position);
        startActivity(intent);

    }
}
