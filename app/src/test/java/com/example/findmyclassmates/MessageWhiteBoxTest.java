package com.example.findmyclassmates;

import static org.junit.Assert.assertEquals;
import com.example.findmyclassmates.activities.mainFeatures.MessageValidation;


import org.junit.Test;

public class MessageWhiteBoxTest {

    MessageValidation mv = new MessageValidation();
    @Test
    public void firstName_empty (){
        String firstName = null;
        assertEquals(true, mv.isFirstNameNull(firstName));
        firstName = "Lily";
        assertEquals(false, mv.isFirstNameNull(firstName));

    }
    @Test
    public void pfp_empty(){
        String pfp = null;
        assertEquals(true, mv.isProfilePictureNull(pfp));
        pfp = "lily.jpg";
        assertEquals(false, mv.isProfilePictureNull(pfp));
    }

    @Test
    public void chatCorrect(){
        String sender = "john";
        String myid = "lily";
        String receiver = "lily";
        String friendid = "john";
        assertEquals(true, mv.isChatCorrect(receiver, sender, myid,friendid));
        myid = "lala";
        assertEquals(false, mv.isChatCorrect(receiver, sender, myid,friendid));
        friendid = "jojo";
        assertEquals(false, mv.isChatCorrect(receiver, sender, myid,friendid));


    }
}
