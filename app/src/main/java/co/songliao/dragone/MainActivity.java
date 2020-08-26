package co.songliao.dragone;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout backgroundView;
    View dragView;
    int backgroundHeight = 0;
    int adjustedBackgroundY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundView = findViewById(R.id.drag_background);
        dragView = findViewById(R.id.drag_view);
        dragView.setOnTouchListener(new View.OnTouchListener() {
            float dy = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dy = backgroundView.getY() - event.getRawY();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    backgroundView.animate()
                            .y(event.getRawY() + dy)
                            .setDuration(0)
                            .start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int threshold = backgroundHeight / 3;

                    float diff = backgroundView.getY() - adjustedBackgroundY;
                    if (diff >  threshold) {
                        Log.d("SongTest", "go down");
                        backgroundView.animate()
                                .y(0)
                                .setDuration(2)
                                .start();
                    } else {
                        Log.d("SongTest", "go back up");
                        backgroundView.animate()
                                .y(adjustedBackgroundY)
                                .setDuration(2)
                                .start();
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //-height + height of drag view
        backgroundView.post(new Runnable() {
            @Override
            public void run() {
                backgroundHeight = backgroundView.getHeight();
                int dragHeight = dragView.getHeight();
                adjustedBackgroundY = -backgroundHeight + dragHeight;
                backgroundView.setY(adjustedBackgroundY);
            }
        });
    }
}