package com.ug.air.sproutofinnovateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ug.air.sproutofinnovateapp.APIs.ApiClient;
import com.ug.air.sproutofinnovateapp.APIs.JsonPlaceHolder;
import com.ug.air.sproutofinnovateapp.Adapters.AppraisalAdapter;
import com.ug.air.sproutofinnovateapp.BuildConfig;
import com.ug.air.sproutofinnovateapp.Models.Appraisal;
import com.ug.air.sproutofinnovateapp.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button button;
    ProgressBar progressBar;
    List<Appraisal> appraisalList;
    AppraisalAdapter appraisalAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView;
    ArrayList<String> files;
    File fileX;
    JsonPlaceHolder jsonPlaceHolder;
    String tokenX, res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);

        recyclerView = findViewById(R.id.recyclerView4);
        button = findViewById(R.id.submit_btn);
        progressBar = findViewById(R.id.progress_bar);
        swipeRefreshLayout = findViewById(R.id.swipe);
        imageView = findViewById(R.id.back);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FormsActivity.this, HomeActivity.class));
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                accessSharedFile();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        appraisalList = new ArrayList<>();

//        accessSharedFile();

        appraisalAdapter = new AppraisalAdapter(this, appraisalList);
        recyclerView.setAdapter(appraisalAdapter);
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            button.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
//            String result = sendData();
//            return result;
            return "good";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            button.setEnabled(true);
            appraisalAdapter.notifyDataSetChanged();
        }
    }

//    private void accessSharedFile() {
//        File src = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/shared_prefs");
//        if (src.exists()) {
//            File[] contents = src.listFiles();
//            if (contents.length != 0) {
//                for (File f : contents) {
//                    if (f.isFile()) {
//                        String name = f.getName().toString();
//                        if (!name.equals("sharedPrefs.xml") && !name.equals("token_file.xml")){
//                            String names = name.replace(".xml", "");
//                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(names, MODE_PRIVATE);
//                            String applicant = sharedPreferences.getString(NAME, "");
//                            String daa = sharedPreferences.getString(DATE, "");
//                            String complete = sharedPreferences.getString(COMPLETE, "");
//                            if (complete.equals("complete")) {
//
//                                SimpleDateFormat df = new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//                                try {
//                                    Date date = df.parse(daa);
//                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//                                    String formattedDate = df1.format(date);
//
//                                    Appraisal appraisal = new Appraisal(applicant, formattedDate);
//                                    appraisalList.add(appraisal);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private String sendData() {
//        File src = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/shared_prefs");
//        if (src.exists()) {
//            File[] contents = src.listFiles();
//            if (contents.length != 0) {
//                for (File f : contents) {
//                    if (f.isFile()) {
//                        String name = f.getName().toString();
//                        if (!name.equals("sharedPrefs.xml") && !name.equals("token_file.xml")) {
//                            String names = name.replace(".xml", "");
//                            SharedPreferences sharedPreferences2 = getSharedPreferences(names, Context.MODE_PRIVATE);
//                            String applicant = sharedPreferences2.getString(APPLICANT, "");
//                            String home1 = sharedPreferences2.getString(HOME_IMAGE_1, "");
//                            String home2 = sharedPreferences2.getString(HOME_IMAGE_2, "");
//                            String guarantor = sharedPreferences2.getString(GUARANTOR, "");
//                            String business1 = sharedPreferences2.getString(BUSINESS_IMAGE_1, "");
//                            String guarantor1 = sharedPreferences2.getString(GUARANTOR_IMAGE_1, "");
//                            String business2 = sharedPreferences2.getString(BUSINESS_IMAGE_2, "");
//
//                            if (!applicant.isEmpty()){
//                                ArrayList<String> imageUrls = new ArrayList<>();
//                                imageUrls.add(applicant);
//                                imageUrls.add(home1);
//                                imageUrls.add(home2);
//                                imageUrls.add(business1);
//                                imageUrls.add(guarantor);
//                                imageUrls.add(guarantor1);
//                                if (!business2.isEmpty()){
//                                    imageUrls.add(business2);
//                                }
//
//                                handleRetrofit(imageUrls, name);
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//        return res;
//    }
//
//    private void handleRetrofit(ArrayList<String> imageUrls, String name) {
//        MultipartBody.Part[] fileUpload = new MultipartBody.Part[imageUrls.size()];
//        for(String url: imageUrls){
//            Log.d("SOIL", "" + url);
//            File file2 = new File(url);
//            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file2);
//            fileUpload[imageUrls.indexOf(url)] = MultipartBody.Part.createFormData("images", file2.getPath(), fileBody);
//        }
//
//        File fileX = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/shared_prefs/" + name);
//        RequestBody filePart = RequestBody.create(MediaType.parse("*/*"), fileX);
//        MultipartBody.Part fileUpload2 = MultipartBody.Part.createFormData("file", fileX.getName() ,filePart);
//
//        Token token = new Token();
//        tokenX = token.getToken(this);
//        Call<String> call = jsonPlaceHolder.send_data("Token " + tokenX, fileUpload, fileUpload2);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(FormsActivity.this, "Connection issue", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String msg = response.body();
//                Log.d("SOIL APP", "onSuccess: data was sent");
//                Toast.makeText(FormsActivity.this, msg, Toast.LENGTH_SHORT).show();
//
//                for(String url: imageUrls){
//                    File file2 = new File(url);
//                    file2.delete();
//                }
//                fileX.delete();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(FormsActivity.this, "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
//                Log.d("SOIL APP", "onFailure: " + t.getMessage());
//            }
//        });
//
//    }

}