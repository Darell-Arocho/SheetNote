package com.example.sketch_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View
{
    Context context;
    private int width, height;
    private Bitmap bitmap;
    private Paint paint;
    private Path path;
    private Canvas canvas;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;


    // Add variables to keep track of previous paths and their colors
    private List<Path> paths = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private int currentColor = Color.BLACK;

//    private float currentPenSize = 4f;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(4f);
    }

    // Colors for all the drop down menus
    public void changePenColor(String color) {
        switch (color) {
            case "Black":
                currentColor = Color.BLACK;
                break;
            case "Brown":
                currentColor = Color.rgb(79, 30, 14);
                break;
            case "Blue":
                currentColor = Color.BLUE;
                break;
            case "Cyan":
                currentColor = Color.CYAN;
                break;
            case "Red":
                currentColor = Color.RED;
                break;
            case "Orange":
                currentColor = Color.rgb(255, 165, 0);
                break;
            case "Yellow":
                currentColor = Color.YELLOW;
                break;
            case "Dark Green":
                currentColor = Color.rgb(0, 100, 0);
                break;
            case "Light Green":
                currentColor = Color.rgb(57, 252, 3);
                break;
            case "Pink":
                currentColor = Color.rgb(250, 10, 174);
                break;
            case "Magenta":
                currentColor = Color.rgb(212, 0, 255);
                break;
            case "Purple":
                currentColor = Color.rgb(128, 0, 128);
                break;
            case "Eraser":
                currentColor = Color.WHITE;
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    // The startTouch method is called when the user first touches the screen.
    // It sets the starting coordinates of the path.
    public void startTouch(float x, float y)
    {
//        path.reset();
        // ________________________________________________
//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(currentColor);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeWidth(4f);
        // ________________________________________________
        path = new Path();
        path.moveTo(x, y);
        mX = x;
        mY = y;

        // Add new path to list of paths and their colors
        paths.add(path);
        colors.add(currentColor);
    }

    // The moveTouch method is called when the user moves their finger on the screen.
    // It adds a new point to the path.
    public void moveTouch(float x, float y)
    {
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
        {
            path.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;

        }
    }




    // Function to clear the canvas

    public void clearCanvas()
    {
        paths.clear();
        colors.clear(); // Clears all the colors from the array
        bitmap.eraseColor(Color.TRANSPARENT);
        invalidate();
    }


    // Functions for increasing and decreasing pen size
    public void increasePenSize() {
        float currentStrokeWidth = paint.getStrokeWidth();
        float newStrokeWidth = currentStrokeWidth + 2f;
        paint.setStrokeWidth(newStrokeWidth);
//        invalidate();
    }

    public void decreasePenSize() {
        float currentStrokeWidth = paint.getStrokeWidth();
        float newStrokeWidth = currentStrokeWidth - 2f;
        paint.setStrokeWidth(newStrokeWidth);
//        invalidate();
    }


    // Text box function
//    public void showTextBox() {
//        // Create a new EditText object
//        EditText textBox = new EditText(getContext());
//
//        // Set its layout parameters
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        textBox.setLayoutParams(params);
//
//        // Add the text box to the layout
//        LinearLayout layout = findViewById(R.id.btn_text_layout); // Replace with the ID of your layout
//        layout.addView(textBox);
//    }

    // Handles all color changes from this single method function
    // Not being used right now
//    public void setPenColor(int color) {
//        paint.setColor(color);
//    }

    // This method adds a new point to the path when the user lifts their finger off the screen.
    public void upTouch()
    {
        path.lineTo(mX, mY);
    }

    // The onDraw method is called whenever the view is invalidated or needs to be redrawn.
    // It loops through all the paths and colors and draws them on the canvas.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw all paths with their respective colors
        for (int i = 0; i < paths.size(); i++) {
            paint.setColor(colors.get(i));
            canvas.drawPath(paths.get(i), paint);
        }
    }

    // The onTouchEvent method is called whenever a touch event occurs on the view.
    // It checks the type of touch event and calls the appropriate method to handle the event.
    // It also returns true to indicate that the event was handled.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the x and y coordinates of the touch event
        float x = event.getX();
        float y = event.getY();
        // Check the action of the touch event
        switch(event.getAction())
        {
            // If the touch event is a down action
            case MotionEvent.ACTION_DOWN:
                // Call the startTouch method and pass in the x and y coordinates
                startTouch(x, y);
                // Invalidate the view to trigger a redraw
                invalidate();
                break;
            // If the touch event is a move action
            case MotionEvent.ACTION_MOVE:
                // Call the moveTouch method and pass in the x and y coordinates
                moveTouch(x, y);
                // Invalidate the view to trigger a redraw
                invalidate();
                break;
            // If the touch event is an up action
            case MotionEvent.ACTION_UP:
                // Call the upTouch method
                upTouch();
                // Invalidate the view to trigger a redraw
                invalidate();
                break;
        }
        // Return true to indicate that the event was handled
        return true;
    }


}