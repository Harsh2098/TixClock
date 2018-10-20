package com.hmproductions.tixclock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class TixRecyclerView extends RecyclerView.Adapter<TixRecyclerView.TixViewHolder> {

    private Context context;
    private ArrayList<Tix> data;
    private String color;

    public TixRecyclerView(Context context, ArrayList<Tix> data, String color) {
        this.context = context;
        this.data = data;
        this.color = color;
    }

    @NonNull
    @Override
    public TixViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TixViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TixViewHolder holder, int position) {
        GradientDrawable square_box = (GradientDrawable) holder.tileTextView.getBackground();
        square_box.setColor(Color.parseColor(data.get(position).getTile() ? color : "#BDBDBD"));
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0) return 0;
        return data.size();
    }

    public void swapData(ArrayList<Tix> list) {
        data = list;
        notifyDataSetChanged();
    }

    class TixViewHolder extends RecyclerView.ViewHolder {

        TextView tileTextView;

        TixViewHolder(View itemView) {
            super(itemView);
            tileTextView = itemView.findViewById(R.id.tileTextView);
        }
    }
}
