package com.example.findmyclassmates;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.findmyclassmates.activities.Checker;
import com.example.findmyclassmates.activities.mainFeatures.ProfileValidation;

public class ProfileWhiteBoxTest {

    private final ProfileValidation pv = new ProfileValidation ();

    @Test
    public void user_blocked (){
        String blockedIds = "amy,brock,monica";
        String toBlockUID = "brock";
        assertEquals(true, pv.isUserBlocked(blockedIds, toBlockUID));
        toBlockUID = "megan";
        assertEquals(false, pv.isUserBlocked(blockedIds, toBlockUID));
    }

    @Test
    public void user_chatting (){
        String existingChatString = "amy,brock,monica";
        String chatUID = "brock";
        assertEquals(true, pv.isChatting(existingChatString, chatUID));
        chatUID = "megan";
        assertEquals(false, pv.isChatting(existingChatString, chatUID));
    }

    @Test
    public void first_chat(){
        String existingChatString = null;
        assertEquals(true, pv.isFirstChat(existingChatString));
        existingChatString = "megan";
        assertEquals(false, pv.isFirstChat(existingChatString));
    }
}
