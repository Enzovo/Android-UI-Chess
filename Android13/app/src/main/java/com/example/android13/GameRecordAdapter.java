package com.example.android13;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class GameRecordAdapter extends RecyclerView.Adapter<GameRecordAdapter.ViewHolder> {

    private List<GameRecord> gameRecorList;

    public GameRecordAdapter(List<GameRecord> gameRecorList) {
        this.gameRecorList = gameRecorList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameRecord gameRecord = gameRecorList.get(position);
        holder.titleTextView.setText(gameRecord.getTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(gameRecord.getDate());
        holder.dateTextView.setText(dateString);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameRecorList.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView dateTextView;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);
            linearLayout = itemView.findViewById(R.id.item);
        }
    }
}

