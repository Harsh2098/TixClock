package com.hmproductions.tixclock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Harsh Mahajan on 14/7/2017.
 */

public class TixAdapter extends ArrayAdapter<Tix> {

    private String tileColor;
    private Context mContext;
    private List<Tix> mData;
    private int mSize;

    TixAdapter(@NonNull Context context, String color, int size) {
        super(context, 0);
        mContext = context;
        mSize = size;
        tileColor = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);

        GradientDrawable square_box = (GradientDrawable)convertView.findViewById(R.id.tile_textView).getBackground();

        if(mData != null) {
            if (mData.get(position).getTile())
                square_box.setColor(Color.parseColor(tileColor));
            else
                square_box.setColor(Color.parseColor("#BDBDBD"));
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    void swapData(List<Tix> data)
    {
        mData = data;
        notifyDataSetChanged();
    }
}
