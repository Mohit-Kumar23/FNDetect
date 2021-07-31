package com.mohit.fndetect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    TextView tv_result;
    EditText et_news;
    Button checkBtn, refreshBtn;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = (TextView) findViewById(R.id.tv_ma_result);
        et_news = (EditText) findViewById(R.id.et_ma_newsTxt);
        checkBtn = (Button) findViewById(R.id.btn_ma_check);
        refreshBtn = (Button) findViewById(R.id.btn_ma_refresh);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnim_ma);


        if (!Python.isStarted())
            Python.start(new AndroidPlatform(getApplicationContext()));

        Python py = Python.getInstance();

        final PyObject pyObject = py.getModule("fake_news_detect");


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newsInfo = et_news.getText().toString();
                if (!newsInfo.equals("")) {
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    PyObject obj = pyObject.callAttr("detecting_fake_news", newsInfo);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            tv_result.setVisibility(View.VISIBLE);
                            tv_result.setText(tv_result.getText().toString().substring(0, tv_result.getText().toString().indexOf(":") + 2) + obj.toString());
                        }
                    }, 5000);

                } else
                    Toast.makeText(getApplicationContext(), "Please Enter Some News", Toast.LENGTH_LONG).show();


            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottieAnimationView.setVisibility(View.INVISIBLE);
                et_news.setText("");
                tv_result.setVisibility(View.INVISIBLE);
            }
        });

    }
}