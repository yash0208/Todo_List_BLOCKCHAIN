package com.rajaryan.todo_list.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajaryan.todo_list.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView task;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        task=itemView.findViewById(R.id.task);
    }
}
