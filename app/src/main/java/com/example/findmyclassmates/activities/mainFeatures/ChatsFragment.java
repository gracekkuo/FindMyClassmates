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
import com.example.findmyclassmates.models.Chats;
import com.example.findmyclassmates.models.Chatslist;
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

public class ChatsFragment extends Fragment {


    List<Chatslist> userlist;
    List<User> mUsers;
    RecyclerView recyclerView;
    UserAdapter mAdapter;
    FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);


        userlist = new ArrayList<>();

        recyclerView = view.findViewById(R.id.chat_recyclerview_chatfrag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Chatslist")
                .child(firebaseUser.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userlist.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Chatslist chatslist = ds.getValue(Chatslist.class);


                    userlist.add(chatslist);



                }

                ChatsListings();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }

    private void ChatsListings() {

        mUsers = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");




    }
}