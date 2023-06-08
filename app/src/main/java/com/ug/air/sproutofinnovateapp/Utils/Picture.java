package com.ug.air.sproutofinnovateapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

public class Picture {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    public Intent TakeImage(Context context, File file, String currentPhotoPath){
        if (!currentPhotoPath.isEmpty()){
            File f = new File(currentPhotoPath);
            f.delete();
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null){
            File photoFile = null;
            photoFile = file;
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.ug.air.soil_app",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        return takePictureIntent;
    }

}
