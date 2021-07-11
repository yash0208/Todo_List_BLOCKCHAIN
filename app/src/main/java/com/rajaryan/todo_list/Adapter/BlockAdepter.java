package com.rajaryan.todo_list.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rajaryan.todo_list.model.BlockModel;
import com.rajaryan.todo_list.viewholder.RecyclerViewHolder;

import java.util.List;

public class BlockAdepter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<BlockModel> blockModels;
    private Context mContext;
    private int lastPosition=-1;

    public BlockAdepter(@Nullable List<BlockModel> blockModels, @NonNull Context mContext) {
        this.blockModels = blockModels;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            holder.task.setText(blockModels.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
