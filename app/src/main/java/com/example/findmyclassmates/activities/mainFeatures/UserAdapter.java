package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyclassmates.activities.mainFeatures.MessageActivity;
import com.example.findmyclassmates.models.Chats;
import com.example.findmyclassmates.models.User;
import com.example.findmyclassmates.R;
//import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {

    Context context;
    List<User> userlist;
    boolean isChat;

    String friendid;
    String thelastmessage;
    FirebaseUser firebaseUser;

    public UserAdapter(Context context, List<User> userlist, boolean isChat) {
        this.context = context;
        this.userlist = userlist;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layoutofusers, parent, false);
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        User user = userlist.get(position);

        friendid = user.getUID();

//        System.out.println("FRIENDID #1: " + friendid);


        holder.username.setText(user.getFirstName());

        if (user.getProfilePicture() == null) {

            holder.imageView.setImageResource(R.drawable.user);


        } else {

            Glide.with(context).load(user.getProfilePicture()).into(holder.imageView);
        }


        if (isChat) {

            if (user.getStatus().equals("online")) {

                holder.image_on.setVisibility(View.VISIBLE);
                holder.image_off.setVisibility(View.GONE);


            } else {

                holder.image_on.setVisibility(View.GONE);
                holder.image_off.setVisibility(View.VISIBLE);


            }


        } else {

            holder.image_on.setVisibility(View.GONE);
            holder.image_off.setVisibility(View.GONE);


        }

        if (isChat) {

            LastMessage(user.getUID(), holder.last_msg);

        } else {

            holder.last_msg.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView username, last_msg;
        CircleImageView imageView, image_on, image_off;

        public MyHolder(@NonNull View itemView) {
            super(itemView);


            username = itemView.findViewById(R.id.username_userfrag);
            imageView = itemView.findViewById(R.id.image_user_userfrag);
            image_on = itemView.findViewById(R.id.image_online);
            image_off = itemView.findViewById(R.id.image_offline);
            last_msg = itemView.findViewById(R.id.lastMessage);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            User users = userlist.get(getAdapterPosition());

            friendid = users.getUID();

//            System.out.println("FRIENDID #2: " + friendid);

            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("friendid", friendid);
            context.startActivity(intent);



        }
    }

    private void LastMessage(final String friendid, final TextView last_msg) {

        thelastmessage = "default";

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()) {

                    Chats chats = ds.getValue(Chats.class);

                    if (firebaseUser!=null &&  chats!=null) {

//                        System.out.println("FRIENDID: " + friendid);
//                        System.out.println("SENDER: " + chats.getSender());
//                        System.out.println("YOUR UID: " + firebaseUser.getUid());
//                        System.out.println("SENDER: " + chats.getReciever());

                        if (chats.getSender().equals(friendid) && chats.getReciever().equals(firebaseUser.getUid()) ||
                                chats.getSender().equals(firebaseUser.getUid()) && chats.getReciever().equals(friendid)) {


                            thelastmessage = chats.getMessage();
                        }




                    }

                }


                switch (thelastmessage) {


                    case "default":
                        last_msg.setText("No message");
                        break;

                    default:
                        last_msg.setText(thelastmessage);

                }


                thelastmessage = "default";


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
}