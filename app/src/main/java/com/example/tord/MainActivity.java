package com.example.tord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import androidx.appcompat.app.AlertDialog;
import android.graphics.Color;
public class MainActivity extends AppCompatActivity {

    Button StartButton;
    TextView BluePageNum;
    TextView RedPageNum;
    Button bluetruthbutton;

    Button bluedarebutton;
    Button redtruthbutton;
    Button reddarebutton;

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartButton = findViewById(R.id.startButton);
        BluePageNum = findViewById(R.id.BluePageNum);
        RedPageNum = findViewById(R.id.RedPageNum);
        bluetruthbutton = findViewById(R.id.blueTruthButton);
        bluetruthbutton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        bluedarebutton = findViewById(R.id.blueDareButton);
        bluedarebutton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        redtruthbutton = findViewById(R.id.redTruthButton);
        redtruthbutton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        reddarebutton = findViewById(R.id.redDareButton);
        reddarebutton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        bluetruthbutton.setVisibility(View.GONE);
        bluedarebutton.setVisibility(View.GONE);
        redtruthbutton.setVisibility(View.GONE);
        reddarebutton.setVisibility(View.GONE);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumber();
            }

        });
        bluetruthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog("hi i am cyrus");
            }
        });
        redtruthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog("hi i am vijay");
            }
        });
    }


    private void generateRandomNumber() {
        Random random = new Random();
        final int Bluepagenum = random.nextInt(101);
        final int Redpagenum = random.nextInt(101);


        animateNumberIncrease(BluePageNum, Bluepagenum);

        animateNumberIncrease(RedPageNum, Redpagenum);
    }

    private void animateNumberIncrease(final TextView textView, final int targetNumber) {
        final int duration = 2000;
        final int framesPerSecond = 60;

        final long frameDelay = duration / framesPerSecond;
        final float increment = (float) targetNumber / (duration / frameDelay);

        runnable = new Runnable() {
            float currentValue = 0;

            @Override
            public void run() {
                currentValue += increment;
                textView.setText(String.valueOf((int) currentValue));

                if (currentValue < targetNumber) {
                    handler.postDelayed(this, frameDelay);
                } else {
                    textView.setText(String.valueOf(targetNumber));
                    showTruthDareButtons(textView, targetNumber);
                }
            }
        };

        handler.post(runnable);
    }

    private void showTruthDareButtons(TextView textView, int targetNumber) {
        if (textView == BluePageNum && targetNumber < Integer.parseInt(RedPageNum.getText().toString())) {
            bluetruthbutton.setVisibility(View.VISIBLE);
            bluedarebutton.setVisibility(View.VISIBLE);
            redtruthbutton.setVisibility(View.GONE);
            reddarebutton.setVisibility(View.GONE);
        } else if (textView == RedPageNum && targetNumber < Integer.parseInt(BluePageNum.getText().toString())) {
            redtruthbutton.setVisibility(View.VISIBLE);
            reddarebutton.setVisibility(View.VISIBLE);
            bluetruthbutton.setVisibility(View.GONE);
            bluedarebutton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    private void ShowDialog(String message){
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
