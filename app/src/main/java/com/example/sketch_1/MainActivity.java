package com.example.sketch_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color; // imported using Alt + Enter for the color pallet
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter; // imported using Alt + Enter for the spinner
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    CanvasView canvasView; // declaring an instance of CanvasView

    private Spinner colorSpinner; // declaring a spinner object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the CanvasView object with id "mycanvas"
        canvasView = (CanvasView) findViewById(R.id.mycanvas);

        // Creating an array of strings to be used for the spinner
        String[] colors = {"Black", "Blue", "Cyan", "Red", "Orange", "Yellow", "Dark Green",
                "Light Green", "Pink", "Purple", "Eraser"};

        // Initializing the spinner object with id "color_spinner"
        colorSpinner = findViewById(R.id.color_spinner);

        // Creating an ArrayAdapter to set the colors array to the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        colorSpinner.setAdapter(adapter);

        // Setting the CanvasView object to id "mycanvas"
        canvasView = findViewById(R.id.mycanvas);

        // Setting an item selected listener for the spinner to change the pen color
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = parent.getItemAtPosition(position).toString();
                canvasView.changePenColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // View was added through Alt + Enter
    public void clearCanvas(View v)
    {
        canvasView.clearCanvas();
    }

    public void increasePenSize(View v) {
        canvasView.increasePenSize();
    }

    public void decreasePenSize(View v) {
        canvasView.decreasePenSize();
    }
}