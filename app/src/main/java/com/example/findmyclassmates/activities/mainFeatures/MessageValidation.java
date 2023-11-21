package com.example.findmyclassmates.activities.mainFeatures;

public class MessageValidation {

    public boolean isFirstNameNull (String firstName){
        return firstName == null;
    }
    public boolean isProfilePictureNull (String pfp){
        return pfp == null;
    }
    public boolean isChatCorrect (String reciever, String sender, String myid, String friendid){
        return reciever.equals(myid) && sender.equals(friendid);
    }
}
