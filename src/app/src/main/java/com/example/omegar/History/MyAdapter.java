package com.example.omegar.History;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omegar.R;

import java.util.List;

class MyAdapter extends ArrayAdapter<com.example.omegar.History.ContentItem> {

    private final Typeface mTypeFaceLight;
    private final Typeface mTypeFaceRegular;


    MyAdapter(Context context, List<com.example.omegar.History.ContentItem> objects) {
        super(context, 0, objects);

        mTypeFaceLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
        mTypeFaceRegular = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        com.example.omegar.History.ContentItem c = getItem(position);

        ViewHolder holder;

        holder = new ViewHolder();

        if (c != null && c.isSection) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_section, null);
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }

        holder.tvName = convertView.findViewById(R.id.tvName);
        holder.tvDesc = convertView.findViewById(R.id.tvDesc);

        convertView.setTag(holder);


        if (c != null && c.isSection)
            holder.tvName.setTypeface(mTypeFaceRegular);
        else
            holder.tvName.setTypeface(mTypeFaceLight);
        holder.tvDesc.setTypeface(mTypeFaceLight);

        holder.tvName.setText(c != null ? c.name : null);
        holder.tvDesc.setText(c != null ? c.desc : null);

        return convertView;
    }

    private class ViewHolder {

        TextView tvName, tvDesc;
    }
}
