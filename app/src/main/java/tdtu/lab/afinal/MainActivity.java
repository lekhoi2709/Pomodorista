package tdtu.lab.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public long startTime;
    private boolean isRunning = false;
    private long timeLeft;
    TextView tv_CountDown;
    Button btn_play, btn_refresh, btn_setting;
    CountDownTimer timer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("StartTime", Context.MODE_PRIVATE);
        startTime = pref.getLong("start_time", 600000*2);
        timeLeft = startTime;

        tv_CountDown = findViewById(R.id.tv_Countdown);
        btn_play = findViewById(R.id.btn_play);
        btn_refresh = findViewById(R.id.btn_refresh);
        btn_setting = findViewById(R.id.btn_setting);
        progressBar = findViewById(R.id.progressBar);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                MainActivity.this.startActivity(settingIntent);
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRunning){
                    startTimer(progressBar, startTime);
                    btn_play.setBackgroundResource(R.drawable.ic_btn_pause);
                } else {
                    pauseTimer();
                    btn_play.setBackgroundResource(R.drawable.ic_btn_start);
                }
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer(progressBar, startTime);
            }
        });
        countDownText();
    }

    public void startTimer(ProgressBar progressBar, long startTime){
        timer = new CountDownTimer(timeLeft, 1000){
            @Override
            public void onTick(long timeLength) {
                timeLeft = timeLength;
                progressBar.setProgress((int)(timeLeft * 100 / startTime));
                countDownText();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                progressBar.setProgress(0);
                Toast.makeText(getApplicationContext(), "Count down timer has ended.", Toast.LENGTH_LONG).show();
            }
        }.start();
        isRunning = true;
    }

    public void pauseTimer(){
        timer.cancel();
        isRunning = false;
    }

    public void resetTimer(ProgressBar progressBar, long startTime){
        if(timer != null){
            timeLeft = startTime;
            progressBar.setProgress((int)(timeLeft * 100 / startTime));
            pauseTimer();
            countDownText();
            isRunning = false;
            btn_play.setBackgroundResource(R.drawable.ic_btn_start);
        }
    }

    public void countDownText(){
        int min = (int)(timeLeft / 1000) / 60;
        int sec = (int)(timeLeft / 1000) % 60;

        String stringTimer = String.format(Locale.ENGLISH, "%02d : %02d", min, sec);
        tv_CountDown.setText(stringTimer);
    }
}