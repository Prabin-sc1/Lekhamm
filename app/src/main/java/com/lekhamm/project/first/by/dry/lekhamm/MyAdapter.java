package com.lekhamm.project.first.by.dry.lekhamm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyAdapter extends FirebaseRecyclerAdapter<Blog, MyAdapter.PostViewHolder> {

    public MyAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Blog> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull PostViewHolder holder, int position, @NonNull @NotNull Blog blog) {
        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDescription());


        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        holder.timestamp.setText(formattedDate);
        String imageUrl = blog.getImage();
        Glide.with(holder.imageView.getContext()).load(imageUrl).into(holder.imageView);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.title.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_content))
                        .setExpanded(true, 1500).create();

                View myView = dialogPlus.getHolderView();

                EditText iurl = myView.findViewById(R.id.uimgurl);
                EditText btitle = myView.findViewById(R.id.utitle);
                EditText bdescription = myView.findViewById(R.id.udescription);
                Button bsubmit = myView.findViewById(R.id.usubmit);

                iurl.setText(blog.getImage());
                btitle.setText(blog.getTitle());
                bdescription.setText(blog.getDescription());
                dialogPlus.show();

                bsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("image", iurl.getText().toString());
                        map.put("title", btitle.getText().toString());
                        map.put("description", bdescription.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("PBlog")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Delete Blog");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("PBlog")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }


    @NonNull
    @NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_row, parent, false);

        return new PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView imageView;
        String userId;

        ImageView edit, delete;

        public PostViewHolder(@NonNull @NotNull View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            desc = view.findViewById(R.id.descriptionTextView);
            imageView = view.findViewById(R.id.imageViewList);
            timestamp = view.findViewById(R.id.timestampList);

            edit = view.findViewById(R.id.editId);
            delete = view.findViewById(R.id.deleteId);
            userId = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }


}


