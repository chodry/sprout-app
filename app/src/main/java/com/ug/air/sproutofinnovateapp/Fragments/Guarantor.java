package com.ug.air.sproutofinnovateapp.Fragments;

import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;

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

import com.ug.air.sproutofinnovateapp.R;


public class Guarantor extends Fragment {

   View view;
    Button btnSave;
    EditText etPeriod, etDistrict, etSubCounty, etVillage, etCounty, etParish;
    String peroid, district, subcounty, village, parish, county, app_3;
    public static final String DISTRICT_3 = "district_3";
    public static final String PERIOD_3 = "period_of_stay_3";
    public static final String SUBCOUNTY_3 = "subcounty_3";
    public static final String VILLAGE_3 = "village_3";
    public static final String PARISH_3 = "parish_3";
    public static final String COUNTY_3 = "county_3";
    public static final String APP_3 = "app_3";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_guarantor, container, false);

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
        editor.putString(PERIOD_3, peroid);
        editor.putString(VILLAGE_3, village);
        editor.putString(SUBCOUNTY_3, subcounty);
        editor.putString(DISTRICT_3, district);
        editor.putString(COUNTY_3, county);
        editor.putString(PARISH_3, parish);
        editor.putString(APP_3, "yes");
        editor.apply();

        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new Home());
        fr.addToBackStack(null);
        fr.commit();
    }

    private void loadData() {
        peroid = sharedPreferences.getString(PERIOD_3, "");
        village = sharedPreferences.getString(VILLAGE_3, "");
        subcounty = sharedPreferences.getString(SUBCOUNTY_3, "");
        district = sharedPreferences.getString(DISTRICT_3, "");
        county = sharedPreferences.getString(COUNTY_3, "");
        parish = sharedPreferences.getString(PARISH_3, "");
    }

    private void UpdateViews() {
        etPeriod.setText(peroid);
        etVillage.setText(village);
        etSubCounty.setText(subcounty);
        etDistrict.setText(district);
        etCounty.setText(county);
        etParish.setText(parish);
    }

}