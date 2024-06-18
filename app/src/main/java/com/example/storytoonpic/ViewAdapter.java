package com.example.storytoonpic;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>{
    public ArrayList<Item> items = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView viewTitle, viewDate;
        public ImageView viewImage;

        public ViewHolder(@NonNull View view) {
            super(view);
            viewTitle = view.findViewById(R.id.view_title);
            viewDate = view.findViewById(R.id.view_date);
            viewImage = view.findViewById(R.id.view_img);
        }
        public void setItem(Item item){
            viewTitle.setText(item.title);
            viewDate.setText(item.date);
            viewImage.setImageBitmap(item.image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        Item item = items.get(position);
        viewholder.setItem(item);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(Item item){
        items.add(item);
    }
    public static class Item{
        String title,date;
        Bitmap image;
        public Item(Item item){
            this(item.title, item.date, item.image);
        }
        public Item(String title, String date, Bitmap image){
            this.title = title;
            this.date = date;
            this.image = image;
        }
    }
}
