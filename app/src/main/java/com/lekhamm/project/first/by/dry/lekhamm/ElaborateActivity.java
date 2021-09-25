package com.lekhamm.project.first.by.dry.lekhamm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ElaborateActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elaborate);

        title = findViewById(R.id.dummyTitleId);
        desc = findViewById(R.id.dummyDescriptionId);


        title.setText(getIntent().getStringExtra("title"));
        desc.setText(getIntent().getStringExtra("description"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            startActivity(new Intent(ElaborateActivity.this, PostListActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}