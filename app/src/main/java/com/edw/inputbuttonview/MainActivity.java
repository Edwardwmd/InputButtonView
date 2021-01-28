package com.edw.inputbuttonview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements InputButtonView.onCountChangeListener {

    private InputButtonView inputButtonView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputButtonView = findViewById(R.id.ipt_button);
        inputButtonView.setBtnHeight(50);
        inputButtonView.setBtnWidth(250);
        inputButtonView.setMax(20);
        inputButtonView.setMin(-20);
    }

    @Override
    public void onCountChange(int count) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inputButtonView.destroyView();
        inputButtonView = null;
    }

}