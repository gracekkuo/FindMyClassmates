package com.example.findmyclassmates;
import static org.junit.Assert.assertEquals;

import com.example.findmyclassmates.activities.mainFeatures.ReviewValidation;

import org.junit.Test;
public class LeaveReviewWhiteBoxTest {

    ReviewValidation reviewValidation = new ReviewValidation();

    @Test
    public void review_empty (){
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        assertEquals(true, reviewValidation.isEmpty(one, two, three, four, five));
        one = "a";
        two = "b";
        three = "c";
        four = "d";
        five = "e";
        assertEquals(false, reviewValidation.isEmpty(one, two, three, four, five));
    }
    @Test
    public void score_check (){
        int score = 3;
        assertEquals(false, reviewValidation.isScoreInvalid(score));
        score = 10;
        assertEquals(true, reviewValidation.isScoreInvalid(score));
        score = -4;
        assertEquals(true, reviewValidation.isScoreInvalid(score));
    }

    @Test
    public void first_upvote (){
        String tag = null;
        assertEquals(true, reviewValidation.isFirstUpvote(tag));
        tag = "CSCI104";
        assertEquals(false, reviewValidation.isFirstUpvote(tag));
    }

    @Test
    public void first_downvote (){
        String tag = null;
        assertEquals(true, reviewValidation.isFirstDownvote(tag));
        tag = "CSCI104";
        assertEquals(false, reviewValidation.isFirstDownvote(tag));
    }

    @Test
    public void authorized_user_to_vote (){
        String user = "lily";
        String reviewUser = "lily";
        assertEquals(true, reviewValidation.isAuthorizedUser(reviewUser, user));
        reviewUser = "sarah";
        assertEquals(false, reviewValidation.isAuthorizedUser(reviewUser, user));
    }




}
