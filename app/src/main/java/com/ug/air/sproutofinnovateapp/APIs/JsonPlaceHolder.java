package com.ug.air.sproutofinnovateapp.APIs;

import com.ug.air.sproutofinnovateapp.Models.Application;
import com.ug.air.sproutofinnovateapp.Models.Token;
import com.ug.air.sproutofinnovateapp.Models.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface JsonPlaceHolder {

    @POST("login/")
    Call<Token> login(@Body User user);

    @GET("get_data/")
    Call<List<Application>> get_data(@Header("Authorization") String header);

    @Multipart
    @POST("send_data/")
    Call<String> send_data(@Header("Authorization") String header,
                           @Part MultipartBody.Part[] files,
                           @Part MultipartBody.Part file);
}
