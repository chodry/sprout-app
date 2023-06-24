package com.ug.air.sproutofinnovateapp.Fragments;

import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.COUNTY;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.DISTRICT;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.PARISH;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SUBCOUNTY;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.VILLAGE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ug.air.sproutofinnovateapp.Models.Image;
import com.ug.air.sproutofinnovateapp.Models.Location;
import com.ug.air.sproutofinnovateapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Applicant extends Fragment {

    View view;
    Button btnSave;
    EditText etPeriod, etDistrict, etSubCounty, etVillage, etCounty, etParish;
    String peroid, district, subcounty, village, parish, county, locate, geo_point;
    public static final String APP_1 = "app_1";
    public static final String LOCATION_1 = "location_1";
    int index;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_applicant, container, false);

        btnSave = view.findViewById(R.id.save);
        etPeriod = view.findViewById(R.id.period);
        etVillage = view.findViewById(R.id.village);
        etParish = view.findViewById(R.id.parish);
        etCounty = view.findViewById(R.id.county);
        etSubCounty = view.findViewById(R.id.subcounty);
        etDistrict = view.findViewById(R.id.district);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadData();
        UpdateViews();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peroid = etPeriod.getText().toString().trim();
                village = etVillage.getText().toString().trim();
                subcounty = etSubCounty.getText().toString().trim();
                district = etDistrict.getText().toString().trim();
                county = etCounty.getText().toString().trim();
                parish = etParish.getText().toString().trim();

                if (peroid.isEmpty() || village.isEmpty() || subcounty.isEmpty() || district.isEmpty() || parish.isEmpty() || county.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                }
            }
        });

        return view;
    }

    private void saveData() {

        location = new Location(village, subcounty, county, parish, district, peroid, geo_point, "applicant");
        Gson gson = new Gson();
        locate = gson.toJson(location);
        editor.putString(LOCATION_1, locate);
        editor.putString(APP_1, "yes");
        editor.apply();

        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new Home());
        fr.addToBackStack(null);
        fr.commit();

    }

    private void loadData() {
        Gson gson = new Gson();
        locate = sharedPreferences.getString(LOCATION_1, null);
        location = gson.fromJson(locate, Location.class);

    }

    private void UpdateViews() {
        geo_point = location.getGeo_point();
        etPeriod.setText(location.getPeriod_of_stay());
        etVillage.setText(location.getVillage());
        etSubCounty.setText(location.getSubcounty());
        etDistrict.setText(location.getDistrict());
        etCounty.setText(location.getCounty());
        etParish.setText(location.getParish());
    }
}