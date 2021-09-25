package com.lekhamm.project.first.by.dry.lekhamm;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Blog> blogList;


    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.next_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Blog blog = blogList.get(position);
        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDescription());


        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        holder.timestamp.setText(formattedDate);
        String imageUrl = blog.getImage();
        Glide.with(context).load(imageUrl).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ElaborateActivity.class);
                intent.putExtra("imagename", blog.getImage());
                intent.putExtra("title", blog.getTitle());
                intent.putExtra("description", blog.getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView imageView;
        String userId;


        public ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;
            title = view.findViewById(R.id.titleTextView);
            desc = view.findViewById(R.id.descriptionTextView);
            imageView = view.findViewById(R.id.imageViewList);
            timestamp = view.findViewById(R.id.timestampList);

            userId = null;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }
    }
}

