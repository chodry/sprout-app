package com.ug.air.sproutofinnovateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ug.air.sproutofinnovateapp.APIs.ApiClient;
import com.ug.air.sproutofinnovateapp.APIs.JsonPlaceHolder;
import com.ug.air.sproutofinnovateapp.Adapters.LoanAdapter;
import com.ug.air.sproutofinnovateapp.BuildConfig;
import com.ug.air.sproutofinnovateapp.Models.Application;
import com.ug.air.sproutofinnovateapp.R;
import com.ug.air.sproutofinnovateapp.Utils.SharedPreferencesUtils;
import com.ug.air.sproutofinnovateapp.Utils.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoansActivity extends AppCompatActivity {

    Dialog dialog;
    RecyclerView recyclerView;
    File[] contents;
    List<Application> applicationList;
    LoanAdapter loanAdapter;
    String tokenX, check, shared_prefs;
    JsonPlaceHolder jsonPlaceHolder;
    ImageView imageView;
    SharedPreferences sharedPreferences, sharedPreferencesX;
    SharedPreferences.Editor editor, editorX;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LOAN_ID = "loan_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String GUARANTOR_NAME = "guarantor_name";
    public static final String VILLAGE = "village";
    public static final String SUBCOUNTY = "subcounty";
    public static final String COUNTY = "county";
    public static final String PARISH = "parish";
    public static final String DISTRICT = "district";
    public static final String COLLATERAL = "collateral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

//        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.back);

        applicationList = new ArrayList<>();

        loanAdapter = new LoanAdapter(this, applicationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(loanAdapter);

        loanAdapter.setOnItemClickListener(new LoanAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                String loan_id = String.valueOf(applicationList.get(position).getId());
                String filename = "loan_" + loan_id + "_sprout";

                boolean exists = SharedPreferencesUtils.isSharedPreferencesFileExists(getApplicationContext(), filename);

                sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                if (exists){

                    sharedPreferencesX = getApplicationContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
                    editorX = sharedPreferencesX.edit();

                    Map<String, ?> all = sharedPreferencesX.getAll();
                    for (Map.Entry<String, ?> x : all.entrySet()) {
                        if (x.getValue().getClass().equals(String.class))  editor.putString(x.getKey(),  (String)x.getValue());
                    }

                    editor.commit();
                    editorX.clear();
                    editorX.commit();

                }
                else {

                    editor.putString(LOAN_ID, loan_id);
                    editor.putString(FIRST_NAME, applicationList.get(position).getFirst_name());
                    editor.putString(LAST_NAME, applicationList.get(position).getLast_name());
                    editor.putString(VILLAGE, applicationList.get(position).getVillage());
                    editor.putString(PARISH, applicationList.get(position).getParish());
                    editor.putString(COUNTY, applicationList.get(position).getCounty());
                    editor.putString(SUBCOUNTY, applicationList.get(position).getSubcounty());
                    editor.putString(DISTRICT, applicationList.get(position).getDistrict());
                    editor.putString(COLLATERAL, applicationList.get(position).getCollateral());
                    editor.putString(GUARANTOR_NAME, applicationList.get(position).getGuarantor());

                    editor.apply();

                }

                startActivity(new Intent(LoansActivity.this, LoanActivity.class));

            }
        });

        getLoanApplications();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoansActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void getLoanApplications() {
        applicationList.clear();
        Token token = new Token();
        tokenX = token.getToken(this);
        Call<List<Application>> call = jsonPlaceHolder.get_data("Token " + tokenX);
        call.enqueue(new Callback<List<Application>>() {
            @Override
            public void onResponse(Call<List<Application>> call, Response<List<Application>> response) {
                if (response.code() == 400){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        String code = jsonObject.getString("warning");
                        Toast.makeText(LoansActivity.this, code, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if (response.isSuccessful()){
                    List<Application> applications = response.body();
                    for (Application application: applications){
                        int id = 0;
                        String first_name = "";
                        String last_name = "";
                        int age = 0;
                        String gender = "";
                        String telephone_number_1 = "";
                        String telephone_number_2 = "";
                        String village = "";
                        String subcounty = "";
                        String district = "";
                        int amount= 0;
                        int duration_of_payment= 0;
                        String collateral = "";
                        String source_of_income = "";
                        String guarantor = "";
                        int interest= 0;
                        String guarantor_telephone_number = "";
                        String guarantor_relationship = "";
                        String time_line = "";
                        String parish = "";
                        String county = "";

                        first_name += application.getFirst_name();
                        last_name += application.getLast_name();
                        id += application.getId();
                        age += application.getAge();
                        gender += application.getGender();
                        telephone_number_1 += application.getTelephone_number_1();
                        telephone_number_2 += application.getTelephone_number_2();
                        village += application.getVillage();
                        subcounty += application.getSubcounty();
                        district += application.getDistrict();
                        amount += application.getAmount();
                        interest += application.getInterest();
                        duration_of_payment += application.getDuration_of_payment();
                        collateral += application.getCollateral();
                        source_of_income += application.getSource_of_income();
                        guarantor_relationship += application.getGuarantor_relationship();
                        guarantor += application.getGuarantor();
                        guarantor_telephone_number += application.getGuarantor_telephone_number();
                        time_line += application.getTime_line();
                        county += application.getCounty();
                        parish += application.getParish();


                        Application application1 = new Application(id, first_name, last_name, age,
                                gender, telephone_number_1, telephone_number_2, village, subcounty, county, parish,
                                district, amount, duration_of_payment, collateral, source_of_income,
                                guarantor, interest, guarantor_telephone_number, guarantor_relationship, time_line);

                        applicationList.add(application1);
                    }

                    loanAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(LoansActivity.this, "Please connect to the internet and try again later", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<List<Application>> call, Throwable t) {
                Toast.makeText(LoansActivity.this, "There is something wrong, Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}