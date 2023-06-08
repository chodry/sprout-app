package com.ug.air.sproutofinnovateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.ug.air.sproutofinnovateapp.R;

import java.io.File;

public class FullViewActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        imageView = findViewById(R.id.img_full);
        String image = getIntent().getExtras().getString("image");
        File f = new File(image);
        imageView.setImageURI(Uri.fromFile(f));
    }
}