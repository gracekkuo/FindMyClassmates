package com.example.findmyclassmates;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Intent;

import com.example.findmyclassmates.activities.Checker;
import com.example.findmyclassmates.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class LoginWhiteBoxTest {

    private final Checker checker = new Checker ();

    @Test
    public void email_test (){
        String badEmail = "helloworld";
        assertEquals(false, checker.isValidEmail(badEmail));
        String badEmailType = "helloworld@gmail.com";
        assertEquals(false, checker.isUSCValidEmail(badEmailType));
        String goodEmail = "helloworld@usc.edu";
        assertEquals(true, checker.isValidEmail(goodEmail));
    }


}
