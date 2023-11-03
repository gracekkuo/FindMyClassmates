package com.example.findmyclassmates.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmyclassmates.models.User;
import com.example.findmyclassmates.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    UserRepository userRepository;

    public UserViewModel(){
        userRepository = new UserRepository();
    }

    public LiveData<Boolean> login (User user){
        return userRepository.login(user);
    }
}
