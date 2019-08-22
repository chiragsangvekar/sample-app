package com.example.sampleapplication.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.sampleapplication.Constants;
import com.example.sampleapplication.Model.Items;
import com.example.sampleapplication.R;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView name = findViewById(R.id.name);
        Items item = getIntent().getParcelableExtra(Constants.DATA);
        name.setText(item.getTitle());

        TextView description = findViewById(R.id.description);
        description.setText(item.getSnippet());

        ImageView imageView = findViewById(R.id.image);
        Glide.with(this).load(item.getPageMaps().getImage().get(0))
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop().into(imageView);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
