package com.example.findmyclassmates.activities.mainFeatures;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmyclassmates.activities.mainFeatures.UserAdapter;
import com.example.findmyclassmates.models.User;
import com.example.findmyclassmates.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {


    RecyclerView recyclerView;
    List<User> usersList;
    UserAdapter mAdapter;
    FirebaseUser firebaseUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        displayusers();

        return view;
    }

    private void displayusers() {

        usersList = new ArrayList<>();

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                String[] blockedIds = new String[0];
                String[] chatIDs = new String[0];
                for (DataSnapshot ds: snapshot.getChildren()) {
                    User users = ds.getValue(User.class);
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if(users.getUID().equals(firebaseUser.getUid())) {
                        String temp = users.getBlockedIDs();
//                        System.out.println("FROM FIREBASE:" + temp);
                        blockedIds = temp.split(",");
//                        System.out.println("BLOCKED IDS" + blockedIds);

                        String temp2 = users.getChats();
                        chatIDs = temp2.split(",");
                    }
                }
                
                for (DataSnapshot ds: snapshot.getChildren()) {

                    User users = ds.getValue(User.class);

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    
                    // USERS ARE ADDED HERE
//                    HANDLE BLOCKED USER ID HERE
                    if (!users.getUID().equals(firebaseUser.getUid())) {

                        //if the current UID is blocked, then we break
                        boolean blocked = false;
                        for(String id : blockedIds) {
                            if(id.equals(users.getUID())) {
                                blocked = true;
                                break;
                            }
                        }

                        //we reach here if the UID is not blocked
                        if(!blocked) {

                            //if the UID is WITHIN THE VALID CHATS OF THE FIREBASE USER THEN WE ADD
                            boolean validChat = false;
                            for(String id : chatIDs) {
                                if(id.equals(users.getUID())) {
                                    validChat = true;
                                    break;
                                }
                            }

                            if(validChat){
                                usersList.add(users);
                            }
                        }

                    }


                    mAdapter  = new UserAdapter(getContext(), usersList, false);
                    recyclerView.setAdapter(mAdapter);








                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}