package com.example.covid19analizes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.covid19analizes.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginForm.class));
            }
        }, 1000);

    }
}