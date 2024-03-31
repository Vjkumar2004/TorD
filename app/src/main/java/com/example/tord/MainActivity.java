package com.example.tord;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import androidx.appcompat.app.AlertDialog;
import android.graphics.Color;

import org.json.*;

public class MainActivity extends AppCompatActivity {
    TextView box;
    String url;
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
        box = findViewById(R.id.Box);
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

        // String urllink = "https://tord-server.onrender.com/truth";

        // JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        //     @Override
        //     public void onResponse(JSONObject response) {
        //         try {
        //             String status = response.getString("status");
        //             if (status.equals("ok")) {
        //                 String truth = response.getString("truth");
        //                 ShowDialog(truth);
        //             } else {
        //                 ShowDialog("Error fetching truth message");
        //             }
        //         } catch (JSONException e) {
        //             e.printStackTrace();
        //             ShowDialog("Error parsing JSON");
        //         }
        //     }
        // }, new Response.ErrorListener() {
        //     @Override
        //     public void onErrorResponse(VolleyError error) {
        //         // Handle Volley errors here
        //         String errorMessage;
        //         if (error.networkResponse != null) {
        //             errorMessage = "Error: " + error.networkResponse.statusCode;
        //         } else {
        //             errorMessage = "Error fetching data. Please check your internet connection.";
        //         }
        //         ShowDialog(errorMessage);
        //     }
        // });
        


        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetruthbutton.setVisibility(View.GONE);
                bluedarebutton.setVisibility(View.GONE);
                redtruthbutton.setVisibility(View.GONE);
                reddarebutton.setVisibility(View.GONE);
                generateRandomNumber();
            }
        });
  
        bluetruthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
                ShowDialog();
            }
        });

        redtruthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
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
    public void ShowDialog(){
        new GetTruthTask().execute();
        
    }
}

class GetTruthTask extends AsyncTask<String,String,String>{
    

    @Override
    protected String doInBackground(String... strings) {
        
        StringBuilder responseString = new StringBuilder();
        try {
            URL url = new URL("https://tord-server.onrender.com/truth");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine())!= null){
                    responseString.append(line).append("\n");
                }
                reader.close();
            }


        } catch (Exception e) {

        }
        return responseString.toString();
    }

    @Override
protected void onPostExecute(String result) {
    try {
        JSONObject json = new JSONObject(result);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setMessage(json.getString("truth"))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle button click if needed
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    } catch (JSONException e) {
        e.printStackTrace();
        // Handle JSON parsing error
    }
}

}