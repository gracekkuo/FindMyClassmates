package com.example.findmyclassmates.activities.mainFeatures;

public class ProfileValidation {

    public boolean isUserBlocked(String blockedIDs, String toBlockUID){
        return blockedIDs.contains(","+toBlockUID);
    }
    public boolean isChatting (String existingChatString,String chatUID){
        return existingChatString.contains(chatUID);
    }

    public boolean isFirstChat (String existingChatString){
        return existingChatString==null;
    }
}
