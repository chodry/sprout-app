package com.ug.air.sproutofinnovateapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.air.sproutofinnovateapp.Adapters.ImageAdapter;
import com.ug.air.sproutofinnovateapp.Models.Image;
import com.ug.air.sproutofinnovateapp.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String camera;
    Button btnCamera;
    GridView gridView;
    String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 102;
    List<Image> imagesList;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        spinner = findViewById(R.id.time);
        btnCamera = findViewById(R.id.camera);
        gridView = findViewById(R.id.grid);

        adapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        imagesList = new ArrayList<>();
//        imagesList.add(new Image("Applicant", "/storage/emulated/0/Android/data/com.ug.air.sproutofinnovateapp/files/Pictures/Sprout/Sprout_20230608_144901.jpg"));
//        imagesList.add(new Image("Applicant's Home", "/storage/emulated/0/Android/data/com.ug.air.sproutofinnovateapp/files/Pictures/Sprout/Sprout_20230608_144913.jpg"));
//        imagesList.add(new Image("National ID (back)", "/storage/emulated/0/Android/data/com.ug.air.sproutofinnovateapp/files/Pictures/Sprout/Sprout_20230608_144934.jpg"));

        imageAdapter = new ImageAdapter(this, R.layout.grid_layout, imagesList);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image image = imagesList.get(position);
                showDialogBox(image);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        camera = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, camera, Toast.LENGTH_SHORT).show();
        if (camera.equals("Select one")) {
            btnCamera.setVisibility(View.GONE);
        }
        else {
            btnCamera.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDialogBox(Image image) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        TextView imageName = dialog.findViewById(R.id.image_name);
        ImageView imageView = dialog.findViewById(R.id.image);
        Button btnFull = dialog.findViewById(R.id.full_view);
        Button btnClose = dialog.findViewById(R.id.close);

        imageName.setText(image.getImage_name());

        File f = new File(image.getImage_url());
        imageView.setImageURI(Uri.fromFile(f));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, FullViewActivity.class);
                intent.putExtra("image", image.getImage_url());
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void dispatchTakePictureIntent() {
//        if (!currentPhotoPath.isEmpty()){
//            File f = new File(currentPhotoPath);
//            f.delete();
//        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ug.air.sproutofinnovateapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }

    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Sprout_" + timeStamp + ".jpg";
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Sprout");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d("Sprout", "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + imageFileName);
        currentPhotoPath = file.getAbsolutePath();
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setImageURI(Uri.fromFile(f));
//                linearLayout.setVisibility(View.GONE);
                Log.d("tag", "Absolute URL is " + Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);

                Image image = new Image(camera, currentPhotoPath);
                imagesList.add(image);
                btnCamera.setVisibility(View.GONE);

                imageAdapter.notifyDataSetChanged();
                gridView.setAdapter(imageAdapter);

            }
        }
    }
}