package com.example.scr3.customlistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scr3 on 01/03/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    int layoutId;

    List<CustomItem> objectList;

    OnItemClickListener clickListener;

    public CustomAdapter(Context context, int layoutId, List<CustomItem> objectList){
        this.context = context;
        this.layoutId = layoutId;
        this.objectList = objectList;
    }



    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    public void setOnClickListener(final OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    private static class ItemHolder{
        LinearLayout placeHolder;
        ImageView icon;
        TextView title;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ItemHolder holder;

        public ViewHolder(View view){
            super(view);

            holder = new ItemHolder();
            holder.placeHolder = (LinearLayout) view.findViewById(R.id.placeholder);
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        String title = objectList.get(position).getTitle();
        int imgId = objectList.get(position).getImageId();

        holder.holder.icon.setImageResource(imgId);
        holder.holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }



}
