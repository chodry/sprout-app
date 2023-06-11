package com.ug.air.sproutofinnovateapp.Activities;

import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.FIRST_NAME;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.LAST_NAME;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.LOAN_ID;
import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ug.air.sproutofinnovateapp.Fragments.Home;
import com.ug.air.sproutofinnovateapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class LoanActivity extends AppCompatActivity {

    public static final String DATE = "appraisal_date";
    public static final String COMPLETE = "complete";
    SharedPreferences sharedPreferences, sharedPreferencesX;
    SharedPreferences.Editor editor, editorX;
    String filename, loan_id, name;
    TextView nameTxt;
    ImageView btnCamera, btnMap, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnBack = findViewById(R.id.back);
        btnMap = findViewById(R.id.mapX);
        btnCamera = findViewById(R.id.cameraX);
        nameTxt = findViewById(R.id.name);

        name = sharedPreferences.getString(FIRST_NAME, "") + " " + sharedPreferences.getString(LAST_NAME, "");
        nameTxt.setText(name);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new Home());
        fragmentTransaction.commit();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoanActivity.this, CameraActivity.class));
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoanActivity.this, MapActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_layout);
        dialog.setCancelable(true);

        Button btnYes = dialog.findViewById(R.id.yes);
        Button btnNo = dialog.findViewById(R.id.no);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                saveFile();
            }
        });

        dialog.show();
    }

    private void saveFile() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(currentTime);

        editor.putString(DATE, formattedDate);
        editor.apply();

        loan_id = sharedPreferences.getString(LOAN_ID, "");

        filename = "loan_" + loan_id + "_sprout";

        sharedPreferencesX = getSharedPreferences(filename, Context.MODE_PRIVATE);
        editorX = sharedPreferencesX.edit();

        Map<String, ?> all = sharedPreferences.getAll();
        for (Map.Entry<String, ?> x : all.entrySet()) {
            if (x.getValue().getClass().equals(String.class))  editorX.putString(x.getKey(),  (String)x.getValue());
        }

        editorX.commit();
        editor.clear();
        editor.commit();

        startActivity(new Intent(LoanActivity.this, LoansActivity.class));
    }
}