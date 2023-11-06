package com.example.findmyclassmates.activities.mainFeatures;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowUserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowUserListFragment extends Fragment {
    RecyclerView showUserList;
    private static final String ARG_PARAM_USER_LIST = "userList";
    private List<User> userList;
    public ShowUserListFragment() {
        // Required empty public constructor
    }


    public static ShowUserListFragment newInstance(List<User> userList) {
        ShowUserListFragment fragment = new ShowUserListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER_LIST, (Serializable) userList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userList = (List<User>) getArguments().getSerializable(ARG_PARAM_USER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.show_user_list_fragment, container, false);
        defineView(view);
        return view;
    }

//    private List<User> setUpUserList(){
//        List<User> userList=new ArrayList<>();
//        userList.add(new User("ram@gmail.com","1",""));
//        userList.add(new User("shyam@gmail.com","1",""));
//        userList.add(new User("hari@gmail.com","1",""));
//        return userList;
//    }

    private void defineView(View view) {
        showUserList = view.findViewById(R.id.show_user_List);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        showUserList.setLayoutManager(layoutManager);

        UserListAdapter listAdapter=new UserListAdapter(userList);
        showUserList.setAdapter(listAdapter);
    }

}