package com.example.myapplication2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView content_tv;
    Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        content_tv = findViewById(R.id.main_tv);
        if (null == timer) {
            timer = new Timer();
        }

        final Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content_tv.setText("获取数据失败！！！，请检查网络");
                        Toast.makeText(MainActivity.this, "get failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content_tv.setText(res);
                    }
                });
            }
        };


        timer.schedule(new TimerTask() {
            private OkHttpClient client = new OkHttpClient();

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .get()
                        .url("http:baidu.com")
                        .build();
                Call call = client.newCall(request);
                Log.i("call", String.valueOf(call.isExecuted()));
                Log.i("call", String.valueOf(call.isCanceled()));
                call.enqueue(callback);
                Log.i("ttt", "ttt");
            }
        }, 5, 5 * 10);


//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//            }
//        };
//        runnable.run();

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .get()
//                        .url("http:127.0.0.1:5000")
//                        .build();
//                Call call = client.newCall(request);
//
//
//                try {
//                    Response response = call.execute();
//
//                    Log.i("http-", response.toString());
//                    assert response.body() != null;
//                    String body = response.body().string();
////
//                    Log.i("http-", body);
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };

//        thread.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
