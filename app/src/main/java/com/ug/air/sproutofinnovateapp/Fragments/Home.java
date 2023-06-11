package com.ug.air.sproutofinnovateapp.Fragments;

import static com.ug.air.sproutofinnovateapp.Activities.CameraActivity.IMAGES;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.LOAN_ID;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;
import static com.ug.air.sproutofinnovateapp.Activities.MapActivity.GEO_POINT_1;
import static com.ug.air.sproutofinnovateapp.Activities.MapActivity.GEO_POINT_2;
import static com.ug.air.sproutofinnovateapp.Activities.MapActivity.GEO_POINT_3;
import static com.ug.air.sproutofinnovateapp.Fragments.Applicant.APP_1;
import static com.ug.air.sproutofinnovateapp.Fragments.Collateral.APP_2;
import static com.ug.air.sproutofinnovateapp.Fragments.Collateral.LOAN;
import static com.ug.air.sproutofinnovateapp.Fragments.Guarantor.APP_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ug.air.sproutofinnovateapp.APIs.ApiClient;
import com.ug.air.sproutofinnovateapp.APIs.JsonPlaceHolder;
import com.ug.air.sproutofinnovateapp.Activities.HomeActivity;
import com.ug.air.sproutofinnovateapp.Activities.LoansActivity;
import com.ug.air.sproutofinnovateapp.BuildConfig;
import com.ug.air.sproutofinnovateapp.Models.Image;
import com.ug.air.sproutofinnovateapp.R;
import com.ug.air.sproutofinnovateapp.Utils.Token;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {

    View view;
    Button btnApplicant, btnCollateral, btnGuarantor, btnSubmit;
    ProgressBar progressBar;
    TextView home1, home2, home3;
    ImageView imageView1, imageView2, imageView3;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String app_1, app_2, app_3, om1, om2, om3, loan_id;
    JsonPlaceHolder jsonPlaceHolder;
    List<Image> imageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        btnApplicant = view.findViewById(R.id.applicant);
        btnCollateral = view.findViewById(R.id.collateral);
        btnGuarantor = view.findViewById(R.id.guarantor);
        btnSubmit = view.findViewById(R.id.submit);
        progressBar = view.findViewById(R.id.progress_bar);
        imageView1 = view.findViewById(R.id.icon_1);
        imageView2 = view.findViewById(R.id.icon_2);
        imageView3 = view.findViewById(R.id.icon_3);

        home1 = view.findViewById(R.id.home_1);
        home2 = view.findViewById(R.id.home_2);
        home3 = view.findViewById(R.id.home_3);


        btnApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Applicant());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        btnCollateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Collateral());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        btnGuarantor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Guarantor());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        app_1 = sharedPreferences.getString(APP_1, "");
        if (app_1.equals("yes")){
            imageView1.setVisibility(View.VISIBLE);
        }

        app_2 = sharedPreferences.getString(APP_2, "");
        if (app_2.equals("yes")){
            imageView2.setVisibility(View.VISIBLE);
        }

        app_3 = sharedPreferences.getString(APP_3, "");
        if (app_3.equals("yes")){
            imageView3.setVisibility(View.VISIBLE);
        }
        
        om1 = sharedPreferences.getString(GEO_POINT_1, "");
        om2 = sharedPreferences.getString(GEO_POINT_2, "");
        om3 = sharedPreferences.getString(GEO_POINT_3, "");
        home1.setText(om1);
        home2.setText(om2);
        home3.setText(om3);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collateral = sharedPreferences.getString(LOAN, "");
                
                if (app_1.isEmpty() || app_2.isEmpty() || app_3.isEmpty() || om1.isEmpty() || om3.isEmpty()) {
                    Toast.makeText(getActivity(), "Please first provide the required information", Toast.LENGTH_SHORT).show();
                }
                else if (collateral.contains("land") && om2.isEmpty()){
                    Toast.makeText(getActivity(), "Please first provide the gps location of the collateral", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendData();
                }
            }
        });

        return view;
    }

    private void sendData() {
        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false);

        String images = sharedPreferences.getString(IMAGES, null);
        Gson gson = new Gson();
        images = sharedPreferences.getString(IMAGES, null);
        Type type = new TypeToken<ArrayList<Image>>() {}.getType();
        imageList = gson.fromJson(images, type);
        if (imageList == null){
            Toast.makeText(getActivity(), "You forgot to take pictures", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            btnSubmit.setEnabled(false);
        }
        else {
            MultipartBody.Part[] fileUpload = new MultipartBody.Part[imageList.size()];
            for(Image image: imageList){
                String url = image.getImage_url();
                File file2 = new File(url);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file2);
                fileUpload[imageList.indexOf(image)] = MultipartBody.Part.createFormData("files", file2.getPath(), fileBody);
            }

            File fileX = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/shared_prefs/" + SHARED_PREFS + ".xml");
            RequestBody filePart = RequestBody.create(MediaType.parse("*/*"), fileX);
            MultipartBody.Part fileUpload2 = MultipartBody.Part.createFormData("file", fileX.getName() ,filePart);

            Token token = new Token();
            String tokenX = token.getToken(getActivity());
//            Toast.makeText(getActivity(), tokenX, Toast.LENGTH_SHORT).show();

            Call<String> call = jsonPlaceHolder.send_data("Token " + tokenX, fileUpload, fileUpload2);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setEnabled(true);
                        Toast.makeText(getActivity(), "Something went wrong, Please contact Chodrine", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String resp = response.body();
                    Log.d("Success message", "onSuccess: " + resp);
                    progressBar.setVisibility(View.GONE);
                    btnSubmit.setEnabled(true);
                    Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();
                    loan_id = sharedPreferences.getString(LOAN_ID, "");
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(getActivity(), LoansActivity.class);
                    intent.putExtra("loan_id", loan_id);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    btnSubmit.setEnabled(true);
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


}