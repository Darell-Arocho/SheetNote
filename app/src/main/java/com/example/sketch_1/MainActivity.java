package com.example.sketch_1;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color; // imported using Alt + Enter for the color pallet
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter; // imported using Alt + Enter for the spinner
import android.widget.Button;
import android.widget.Spinner;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity {

    CanvasView canvasView; // declaring an instance of CanvasView
    private List<CanvasView> canvasList = new ArrayList<>();

    private Spinner colorSpinner; // declaring a spinner object
    Button btn1;
//    Button closeBtn;

    // Initializes the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the CanvasView object with id "mycanvas"
        canvasView = (CanvasView) findViewById(R.id.mycanvas);
        canvasList.add(canvasView);
        // Creating an array of strings to be used for the spinner
        String[] colors = {"Black", "Brown", "Blue", "Cyan", "Red", "Orange", "Yellow", "Dark Green",
                "Light Green", "Pink", "Magenta", "Purple", "Eraser"};

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

        btn1 = (Button) findViewById(R.id.page2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Page2.class);
                startActivity(intent1);
            }
        });

//        closeBtn = (Button) findViewById(R.id.clsBtn);
//
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 finish();
//                 android.os.Process.killProcess(android.os.Process.myPid());
//                 System.exit(1);
//            }
//        });

        Button printButton = findViewById(R.id.print_button);
        Dexter.withActivity(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                printButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doPrint();
//                        // Handle print button click
//                        // Create a print manager object
//                        PrintManager printManager = (PrintManager) getActivity()
//                                .getSystemService(Context.PRINT_SERVICE);
//
//                        // Get the name of the app
//                        String jobName = getString(R.string.app_name) + " Document";
//
//                        // Create a print job
//                        PdfDocumentAdapter printAdapter = new PdfDocumentAdapter(MainActivity.this, jobName, canvasView);
//                        PrintJob printJob = printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
                    }
                });

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();


    }
    private void doPrint() {
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) MainActivity.this.getSystemService(Context.PRINT_SERVICE);

        // Set job name, which will be displayed in the print queue
        String jobName = MainActivity.this.getString(R.string.app_name) + " Document";

        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, new PdfDocumentAdapter(canvasList), null); //
    }
    // View was added through Alt + Enter
    public void clearCanvas(View v)
    {
//        // Initializing the CanvasView object with id "mycanvas"
        canvasView = findViewById(R.id.mycanvas);
        canvasView.clearCanvas();
        // Iterating over the list of CanvasView objects and clearing each one
//        for (CanvasView canvasView : canvasList) {
//            canvasView.clearCanvas();
//        }
    }

    public void increasePenSize(View v) {

//        canvasView.increasePenSize();
        for (CanvasView canvasView : canvasList) {
            canvasView.increasePenSize();
        }
    }

    public void decreasePenSize(View v) {
//        canvasView.decreasePenSize();
        for (CanvasView canvasView : canvasList) {
            canvasView.decreasePenSize();
        }
    }

//    public void showTextBox(View v){ canvasView.showTextBox();}

}