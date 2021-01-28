package com.edw.inputbuttonview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements InputButtonView.onCountChangeListener {

    private InputButtonView inputButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputButtonView = findViewById(R.id.ipt_button);
        inputButtonView.setOnCountChangeListener(this);
    }

    @Override
    public void onCountChange(int count) {
        Toast.makeText(this, "单价:2元一个------>总价为" + 2 * count + "元", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inputButtonView.destroyView();
        inputButtonView = null;
    }
}