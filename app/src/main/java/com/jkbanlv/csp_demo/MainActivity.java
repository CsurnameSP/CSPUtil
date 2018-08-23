package com.jkbanlv.csp_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jkbanlv.library.AbstractCheckPermission;
import com.jkbanlv.library.CspUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CspUtil.dpToPx(this,150);
    }
}
