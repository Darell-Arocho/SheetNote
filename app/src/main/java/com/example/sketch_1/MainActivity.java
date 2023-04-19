package com.example.sketch_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasView = (CanvasView)findViewById(R.id.mycanvas);

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

