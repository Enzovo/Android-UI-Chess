package com.example.android13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;


public class MainActivity extends AppCompatActivity {

//    private final Button btn_start = findViewById(R.id.button_play);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_main);

        Button btn_start = findViewById(R.id.button_play);

        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Board.class);
            startActivity(intent);
        });

        Button btn_record = findViewById(R.id.button_record);

        btn_record.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Record.class);
            startActivity(intent);
        });
    }
}