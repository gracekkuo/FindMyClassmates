package com.example.findmyclassmates.activities.mainFeatures;

import android.text.TextUtils;
import android.widget.EditText;

public class ReviewValidation {
    public boolean isEmpty(String one, String two, String three, String four, String five) {
        return one.isEmpty() || two.isEmpty() || three.isEmpty() || four.isEmpty() || five.isEmpty();
    }

    public boolean isScoreInvalid (int score){
        return (score < 1 || score > 5);
    }

    public boolean isFirstUpvote (String tag){
        return tag == null;
    }

    public boolean isFirstDownvote (String tag){
        return tag == null;
    }

    public boolean isAuthorizedUser (String reviewUser, String user){
        return user.equals(reviewUser);
    }
}
