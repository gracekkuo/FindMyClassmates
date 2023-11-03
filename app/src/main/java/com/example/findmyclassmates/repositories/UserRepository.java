package com.example.findmyclassmates.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.findmyclassmates.models.User;
import com.example.findmyclassmates.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private MutableLiveData<Boolean> liveData = new MutableLiveData<>();
    public LiveData<Boolean> login (User user){
        new ApiClient().getRetrofit().login(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                liveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                liveData.postValue(false);
            }
        });
        return liveData;
    }
}
