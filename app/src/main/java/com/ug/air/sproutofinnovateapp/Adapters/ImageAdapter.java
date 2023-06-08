package com.ug.air.sproutofinnovateapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ug.air.sproutofinnovateapp.Models.Image;
import com.ug.air.sproutofinnovateapp.R;

import java.io.File;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Image> {

    List<Image> imageList;
    int custom_layout_id;

    public ImageAdapter(@NonNull Context context, int resource, @NonNull List<Image> objects) {
        super(context, resource, objects);
        imageList = objects;
        custom_layout_id = resource;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            // getting reference to the main layout and initializing
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(custom_layout_id, null);
        }

        // initializing the imageview and textview and setting data
        ImageView imageView = v.findViewById(R.id.imageView);
        TextView textView = v.findViewById(R.id.textView);

        // get the item using the  position param
        Image image = imageList.get(position);

        File f = new File(image.getImage_url());
        imageView.setImageURI(Uri.fromFile(f));
        textView.setText(image.getImage_name());

        return v;
    }
}
