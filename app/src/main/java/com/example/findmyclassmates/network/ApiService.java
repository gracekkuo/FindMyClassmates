package com.example.findmyclassmates.network;

import com.example.findmyclassmates.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    public Call<Void> login(@Body User user);
}
