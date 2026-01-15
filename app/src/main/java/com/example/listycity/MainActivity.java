package com.example.listycity;

import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    int selected = -1; // -1 for not selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnRmv = findViewById(R.id.btn_rmv);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};


        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // get pos for city select
        cityList.setOnItemClickListener((p, v, position, id) -> selected = position);

        // use addCity method if add btn clicked
        btnAdd.setOnClickListener(v -> addCity());

        // run removal if remove btn clicked
        btnRmv.setOnClickListener(v -> {
            // handle invalid selection
            if (selected < 0 || selected >= dataList.size()) {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
                return;
            }
            dataList.remove(selected);
            cityAdapter.notifyDataSetChanged();
            selected = -1; // clear selection
        });
    }

    /**
     * Add city to dataList
     */
    protected void addCity() {
        EditText input = new EditText(this); // input field control
        input.setHint("Enter city name"); // placeholder

        // dialog for which city to add
        new AlertDialog.Builder(this)
                .setTitle("Add city")
                .setView(input)
                .setPositiveButton("Confirm", (d, pos) -> {
                    String city = input.getText().toString().trim(); // get city
                    if (city.isEmpty()) {
                        Toast.makeText(this, "City cannot be empty.", Toast.LENGTH_SHORT).show(); // error message
                        return;
                    }
                    dataList.add(city);
                    cityAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}