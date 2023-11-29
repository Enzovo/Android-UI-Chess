package com.example.android13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Record extends AppCompatActivity implements GameRecordAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private GameRecordAdapter adapter;
    private List<GameRecord> gameRecorList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameRecorList = LitePal.findAll(GameRecord.class);
        adapter = new GameRecordAdapter(gameRecorList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, Board.class);
        intent.putExtra("id", gameRecorList.get(position).getId());
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }

    public void titleSort(View view) {
        Collections.sort(gameRecorList, new Comparator<GameRecord>() {
            @Override
            public int compare(GameRecord data1, GameRecord data2) {
                return data1.getTitle().compareTo(data2.getTitle());
            }
        });
        adapter.notifyDataSetChanged();
    }


    public void dateSort(View view) {
        Collections.sort(gameRecorList, new Comparator<GameRecord>() {
            @Override
            public int compare(GameRecord data1, GameRecord data2) {
                return data1.getDate().compareTo(data2.getDate());
            }
        });
        adapter.notifyDataSetChanged();
    }

}
