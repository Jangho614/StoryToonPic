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
        public ImageView im1,im2,im3,im4;

        public ViewHolder(@NonNull View view) {
            super(view);
            viewTitle = view.findViewById(R.id.view_title);
            viewDate = view.findViewById(R.id.view_date);
            im1 = view.findViewById(R.id.view_img1);
            im2 = view.findViewById(R.id.view_img2);
            im3 = view.findViewById(R.id.view_img3);
            im4 = view.findViewById(R.id.view_img4);
        }
        public void setItem(Item item){
            viewTitle.setText(item.title);
            viewDate.setText(item.date);
            im1.setImageBitmap(item.im1);
            im2.setImageBitmap(item.im2);
            im3.setImageBitmap(item.im3);
            im4.setImageBitmap(item.im4);
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
        Bitmap im1,im2,im3,im4;
        public Item(Item item){
            this(item.title, item.date, item.im1, item.im2, item.im3, item.im4);
        }
        public Item(String title, String date, Bitmap im1, Bitmap im2, Bitmap im3, Bitmap im4){
            this.title = title;
            this.date = date;
            this.im1 = im1;
            this.im2 = im2;
            this.im3 = im3;
            this.im4 = im4;
        }
    }
}
