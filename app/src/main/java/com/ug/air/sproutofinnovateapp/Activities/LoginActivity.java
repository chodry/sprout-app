package com.ug.air.sproutofinnovateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ug.air.sproutofinnovateapp.APIs.ApiClient;
import com.ug.air.sproutofinnovateapp.APIs.JsonPlaceHolder;
import com.ug.air.sproutofinnovateapp.Models.Token;
import com.ug.air.sproutofinnovateapp.Models.User;
import com.ug.air.sproutofinnovateapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button loinBtn;
    CardView userCard, passCard;
    EditText etUser, etPass;
    String username, password;
    ProgressBar progressBar;
    JsonPlaceHolder jsonPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_login);

        jsonPlaceHolder = ApiClient.getClient().create(JsonPlaceHolder.class);

        loinBtn = findViewById(R.id.login);
        userCard = findViewById(R.id.user);
        passCard = findViewById(R.id.pass);
        etPass = findViewById(R.id.password);
        etUser = findViewById(R.id.username);
        progressBar = findViewById(R.id.login_progress_bar);

        etUser.addTextChangedListener(textWatcher1);
        etPass.addTextChangedListener(textWatcher2);

        loinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUser.getText().toString().trim();
                password = etPass.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please provide both the username and password", Toast.LENGTH_SHORT).show();
                }else {
                    sendToServer();
                }
//
            }
        });

    }

    TextWatcher textWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            username = etUser.getText().toString();
            if (!username.isEmpty()){
                userCard.setCardElevation(5);
                etUser.setCompoundDrawablesWithIntrinsicBounds(R.drawable.username, 0, 0, 0);
            }else {
                userCard.setCardElevation(0);
                etUser.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher textWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            password = etPass.getText().toString();
            if (!password.isEmpty()){
                passCard.setCardElevation(5);
                etPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock2, 0, 0, 0);
            }else{
                passCard.setCardElevation(0);
                etPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, 0, 0);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void sendToServer() {
        progressBar.setVisibility(View.VISIBLE);
        loinBtn.setEnabled(false);
        User user = new User(username, password);
        Call<Token> call = jsonPlaceHolder.login(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 400){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        String code = jsonObject.getString("non_field_errors");
                        Toast.makeText(LoginActivity.this, code, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        loinBtn.setEnabled(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    loinBtn.setEnabled(true);
                    String token1 = response.body().getToken();
                    String name = response.body().getName();
                    com.ug.air.sproutofinnovateapp.Utils.Token token = new com.ug.air.sproutofinnovateapp.Utils.Token();
                    token.createToken(LoginActivity.this, token1, username, name);
                    startActivity(new Intent(LoginActivity.this, PermissionsActivity.class));
                    finish();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    loinBtn.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loinBtn.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Something went wrong, Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}