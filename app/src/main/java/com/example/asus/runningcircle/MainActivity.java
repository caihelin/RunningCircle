package com.example.asus.runningcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RunningCircle runningCircle;
    private boolean imgSrcIsChanged = false;
    private boolean directionIsChanged = false;
    private boolean hasBorder = false;
    private boolean borderColorIsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runningCircle = (RunningCircle) findViewById(R.id.circleView);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningCircle.start();

            }
        });

        findViewById(R.id.btnBorderColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!borderColorIsChanged) {
                    runningCircle.setBorderColor(R.color.colorPrimaryDark);
                    borderColorIsChanged=true;
                }else{
                    runningCircle.setBorderColor(R.color.colorAccent);
                    borderColorIsChanged=false;
                }

            }
        });

        findViewById(R.id.btnBorderWidth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasBorder) {
                    runningCircle.setBorderWidth(20);
                    boolean borderVisible = runningCircle.getBorderVisible();
                    Log.d("kklog", String.valueOf(borderVisible));
                    Toast.makeText(MainActivity.this, "borderVisible"+borderVisible, Toast.LENGTH_SHORT).show();
                    hasBorder=true;
                }else{
                    runningCircle.setBorderWidth(0);
                    boolean borderVisible = runningCircle.getBorderVisible();
                    Log.d("kklog", String.valueOf(borderVisible));
                    Toast.makeText(MainActivity.this, "borderVisible"+borderVisible, Toast.LENGTH_SHORT).show();
                    hasBorder=false;
                }
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningCircle.stop();
            }
        });

        findViewById(R.id.btnImgSrc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imgSrcIsChanged) {
                    runningCircle.setImgSrc(R.drawable.jay_fantexi);
                    imgSrcIsChanged=true;
                }else{
                    runningCircle.setImgSrc(R.drawable.jay_jay);
                    imgSrcIsChanged=false;
                }
            }
        });


        findViewById(R.id.btnDirection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!directionIsChanged) {
                    runningCircle.setDirections(RunningCircle.ACW);
                    directionIsChanged=true;
                }else{
                    runningCircle.setDirections(RunningCircle.CW);
                    directionIsChanged=false;
                }
            }
        });

        runningCircle.setImgSrc(R.drawable.jay_jay);
        runningCircle.setRunningListener(new RunningCircle.OnRunningListener() {
            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this, "Start Running!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStop() {
                Toast.makeText(MainActivity.this, "Stop Running!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
