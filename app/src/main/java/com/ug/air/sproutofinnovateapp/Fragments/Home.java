package com.ug.air.sproutofinnovateapp.Fragments;

import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;
import static com.ug.air.sproutofinnovateapp.Fragments.Applicant.APP_1;
import static com.ug.air.sproutofinnovateapp.Fragments.Collateral.APP_2;
import static com.ug.air.sproutofinnovateapp.Fragments.Guarantor.APP_3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ug.air.sproutofinnovateapp.R;


public class Home extends Fragment {

    View view;
    Button btnApplicant, btnCollateral, btnGuarantor;
    ImageView imageView1, imageView2, imageView3;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String app_1, app_2, app_3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnApplicant = view.findViewById(R.id.applicant);
        btnCollateral = view.findViewById(R.id.collateral);
        btnGuarantor = view.findViewById(R.id.guarantor);
        imageView1 = view.findViewById(R.id.icon_1);
        imageView2 = view.findViewById(R.id.icon_2);
        imageView3 = view.findViewById(R.id.icon_3);

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

        return view;
    }
}