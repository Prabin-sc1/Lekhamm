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


public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> implements Filterable{
    private Context context;
    private List<Blog> blogList;
    private List<Blog> backup;



    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
        backup = new ArrayList<>(blogList);
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

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Blog> filteredData = new ArrayList<>();
            if (constraint.toString().isEmpty())
                filteredData.addAll(backup);
            else {
                for (Blog obj : backup) {
                    if (obj.getTitle().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredData.add(obj);
                    }
                }
            }
                FilterResults results = new FilterResults();
                results.values = filteredData;
                return results;
            }



            @Override
            protected void publishResults (CharSequence constraint, FilterResults results){
                blogList.clear();
                blogList.addAll((ArrayList<Blog>) results.values);
                notifyDataSetChanged();
        }
    };

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

