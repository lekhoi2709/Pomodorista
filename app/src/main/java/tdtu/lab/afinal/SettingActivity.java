package tdtu.lab.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity {
    Button btn_goBack;
    SwitchMaterial sw_mode;
    SeekBar seekBar;
    TextView tv_setTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_goBack = findViewById(R.id.btn_goBack);
        sw_mode = findViewById(R.id.sw_mode);
        seekBar = findViewById(R.id.seekBar);
        tv_setTime = findViewById(R.id.tv_setTime);
        SharedPreferences pref = getSharedPreferences("StartTime", Context.MODE_PRIVATE);

        int progress =(int)(pref.getLong("start_time", 10)/1000/60);
        seekBar.setMax(99);
        seekBar.setMin(5);
        seekBar.setProgress(progress);
        tv_setTime.setText(progress + " mins");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int time, boolean b) {
                long start_time = (long) seekBar.getProgress()*60*1000;
                SharedPreferences.Editor editor = pref.edit();
                editor.putLong("start_time", start_time);
                editor.commit();
                update(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        boolean isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        String dark = "Dark Mode";
        String light = "Light Mode";

        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SettingActivity.this, MainActivity.class);
                SettingActivity.this.startActivity(homeIntent);
            }
        });

        if(isDark){
            sw_mode.setText(light);
        } else {
            sw_mode.setText(dark);
        }

        sw_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sw_mode.setText(light);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sw_mode.setText(dark);
                }
            }
        });
    }

    public void update(int time){
        String set_time = time + " mins";
        tv_setTime.setText(set_time);
    }
}
