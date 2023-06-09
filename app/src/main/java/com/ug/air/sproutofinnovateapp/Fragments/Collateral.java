package com.ug.air.sproutofinnovateapp.Fragments;

import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.COLLATERAL;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ug.air.sproutofinnovateapp.R;

public class Collateral extends Fragment {

    View view;
    Button btnSave;
    EditText etPeriod, etDistrict, etSubCounty, etVillage, etCounty, etParish, etCollateral;
    String peroid, district, subcounty, village, parish, county, app_3, collateral, loan, type;
    Spinner spinner1, spinner2;
    LinearLayout linearLayout, linearLayout2;
    public static final String DISTRICT_2 = "district_2";
    public static final String PERIOD_2 = "period_of_stay_2";
    public static final String SUBCOUNTY_2 = "subcounty_2";
    public static final String VILLAGE_2 = "village_2";
    public static final String PARISH_2 = "parish_2";
    public static final String COUNTY_2 = "county_2";
    public static final String APP_2 = "app_2";
    public static final String TYPE = "type";
    public static final String LOAN = "loan";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayAdapter<CharSequence> adapter1, adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_collateral, container, false);

        btnSave = view.findViewById(R.id.save);
        etPeriod = view.findViewById(R.id.period);
        etVillage = view.findViewById(R.id.village);
        etParish = view.findViewById(R.id.parish);
        etCounty = view.findViewById(R.id.county);
        etSubCounty = view.findViewById(R.id.subcounty);
        etDistrict = view.findViewById(R.id.district);
        etCollateral = view.findViewById(R.id.collateral);
        spinner1 = view.findViewById(R.id.time);
        spinner2 = view.findViewById(R.id.timeX);
        linearLayout = view.findViewById(R.id.location);
        linearLayout2 = view.findViewById(R.id.spinner_layout);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.collateral, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loan = (String) parent.getItemAtPosition(position);
                if (loan.contains("land")){
                    linearLayout2.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    etPeriod.setText("");
                    etVillage.setText("");
                    etSubCounty.setText("");
                    etDistrict.setText("");
                    etCounty.setText("");
                    etParish.setText("");
                    type = "Select one";
                    spinner2.setSelection(0);
                }

                if (loan.equals("Select one")) {
                    btnSave.setVisibility(View.GONE);
                }
                else {
                    btnSave.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
                if (type.equals("Select one")) {
                    btnSave.setVisibility(View.GONE);
                }
                else {
                    btnSave.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                collateral = etCollateral.getText().toString().trim();

                if (loan.contains("land")){
                    if (peroid.isEmpty() || village.isEmpty() || subcounty.isEmpty() || district.isEmpty()
                            || parish.isEmpty() || county.isEmpty() || collateral.isEmpty()
                            || type.equals("Select one"))
                    {
                        Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                }else {
                    type = "";
                    saveData();
                }

            }
        });

        return view;
    }

    private void saveData() {
        editor.putString(PERIOD_2, peroid);
        editor.putString(VILLAGE_2, village);
        editor.putString(SUBCOUNTY_2, subcounty);
        editor.putString(DISTRICT_2, district);
        editor.putString(COUNTY_2, county);
        editor.putString(PARISH_2, parish);
        editor.putString(LOAN, loan);
        editor.putString(TYPE, type);
        editor.putString(APP_2, "yes");
        editor.apply();

        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new Home());
        fr.addToBackStack(null);
        fr.commit();
    }

    private void loadData() {

        collateral = sharedPreferences.getString(COLLATERAL, "");

        peroid = sharedPreferences.getString(PERIOD_2, "");
        village = sharedPreferences.getString(VILLAGE_2, "");
        subcounty = sharedPreferences.getString(SUBCOUNTY_2, "");
        district = sharedPreferences.getString(DISTRICT_2, "");
        county = sharedPreferences.getString(COUNTY_2, "");
        parish = sharedPreferences.getString(PARISH_2, "");
        loan = sharedPreferences.getString(LOAN, "");
        type = sharedPreferences.getString(TYPE, "");

    }

    private void UpdateViews() {

        etCollateral.setText(collateral);
        spinner1.setSelection(adapter1.getPosition(loan));

        if (loan.contains("land")){
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            spinner2.setSelection(adapter2.getPosition(type));

            etPeriod.setText(peroid);
            etVillage.setText(village);
            etSubCounty.setText(subcounty);
            etDistrict.setText(district);
            etCounty.setText(county);
            etParish.setText(parish);
        }else {
            linearLayout2.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }

    }

}