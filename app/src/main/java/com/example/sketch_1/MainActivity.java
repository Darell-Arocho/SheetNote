package com.example.sketch_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color; // imported using Alt + Enter for the color pallet
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

    // All pen color changes below
    public void changePenColorBlack(View v) {
        canvasView.setPenColor(Color.BLACK);
    }

    public void changePenColorBlue(View v) {
        canvasView.setPenColor(Color.BLUE);
    }

    public void changePenColorRed(View v) {
        canvasView.setPenColor(Color.RED);
    }

    public void changePenColorOrange(View v) {
        canvasView.setPenColor(Color.rgb(255, 165, 0));
    }

    public void changePenColorYellow(View v) {
        canvasView.setPenColor(Color.YELLOW);
    }

    public void changePenColorDarkGreen(View v) {
        canvasView.setPenColor(Color.rgb(0, 100, 0));
    }

    public void changePenColorPink(View v) {
        canvasView.setPenColor(Color.rgb(255, 192, 203));
    }

    public void changePenColorCyan(View v) { canvasView.setPenColor(Color.rgb(89, 193, 189));}

}

