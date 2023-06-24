package com.ug.air.sproutofinnovateapp.Activities;

import static com.ug.air.sproutofinnovateapp.Fragments.Applicant.LOCATION_1;

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

import com.google.gson.Gson;
import com.ug.air.sproutofinnovateapp.APIs.ApiClient;
import com.ug.air.sproutofinnovateapp.APIs.JsonPlaceHolder;
import com.ug.air.sproutofinnovateapp.Adapters.LoanAdapter;
import com.ug.air.sproutofinnovateapp.BuildConfig;
import com.ug.air.sproutofinnovateapp.Models.Applicant;
import com.ug.air.sproutofinnovateapp.Models.Application;
import com.ug.air.sproutofinnovateapp.Models.Location;
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
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

//        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.back);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String loan = extras.getString("loan_id");
            File fileX = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/shared_prefs/loan_" + loan + "_sprout.xml");
            if (fileX.exists()){
                fileX.delete();
            }
        }

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
                    editor.putString(FIRST_NAME, applicationList.get(position).getApplicant().getFirst_name());
                    editor.putString(LAST_NAME, applicationList.get(position).getApplicant().getLast_name());
                    editor.putString(COLLATERAL, applicationList.get(position).getCollateral());
                    editor.putString(GUARANTOR_NAME, applicationList.get(position).getGuarantor());

                    String village = applicationList.get(position).getLocation().getVillage();
                    String parish = applicationList.get(position).getLocation().getParish();
                    String county = applicationList.get(position).getLocation().getCounty();
                    String subcounty = applicationList.get(position).getLocation().getSubcounty();
                    String district = applicationList.get(position).getLocation().getDistrict();

                    location = new Location(village, subcounty, county, parish, district, "", "", "applicant");
                    Gson gson = new Gson();
                    String locate = gson.toJson(location);
                    editor.putString(LOCATION_1, locate);

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
                        int income = 0;
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
                        String weekly = "";

                        first_name += application.getApplicant().getFirst_name();
                        last_name += application.getApplicant().getLast_name();
                        id += application.getId();
                        age += application.getApplicant().getAge();
                        gender += application.getApplicant().getGender();
                        telephone_number_1 += application.getApplicant().getTelephone_number_1();
                        telephone_number_2 += application.getApplicant().getTelephone_number_2();
                        village += application.getLocation().getVillage();
                        subcounty += application.getLocation().getSubcounty();
                        district += application.getLocation().getDistrict();
                        amount += application.getAmount();
                        interest += application.getInterest();
                        duration_of_payment += application.getDuration_of_payment();
                        collateral += application.getCollateral();
                        source_of_income += application.getSource_of_income();
                        guarantor_relationship += application.getGuarantor_relationship();
                        guarantor += application.getGuarantor();
                        guarantor_telephone_number += application.getGuarantor_telephone_number();
                        time_line += application.getTime_line();
                        county += application.getLocation().getCounty();
                        parish += application.getLocation().getParish();
                        weekly += application.getWeekly_or_monthly();
                        income += application.getIncome();

                        Location location = new Location(village, subcounty, county, parish, district, "", "", "");
                        Applicant applicant = new Applicant(first_name, last_name, age, gender, telephone_number_1, telephone_number_2);

                        Application application1 = new Application(id, amount, duration_of_payment, collateral, source_of_income,
                                income, weekly, guarantor, interest, guarantor_telephone_number, guarantor_relationship,
                                time_line, applicant, location);

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