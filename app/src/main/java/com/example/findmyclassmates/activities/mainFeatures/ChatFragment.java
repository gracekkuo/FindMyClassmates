package com.example.findmyclassmates.activities.mainFeatures;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.User;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Initialize TabLayout and ViewPager views
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.main_viewpager);

        // Set up the ViewPager and TabLayout
        setupViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        //  adapter.addFragment(new CuriosityModeFeatured(),"Featured");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpUserList()), "Users");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpChatListUsers()),"Chats");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpOnlineUsers()),"Online");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private List<User> setUpUserList(){
        List<User> userList=new ArrayList<>();
        userList.add(new User("ram@gmail.com","1",""));
        userList.add(new User("shyam@gmail.com","2",""));
        userList.add(new User("hari@gmail.com","3",""));
        return userList;
    }
    private List<User> setUpChatListUsers(){
        List<User> userList=new ArrayList<>();
        userList.add(new User("ram@gmail.com","1",""));
        return userList;
    }
    private List<User> setUpOnlineUsers(){
        List<User> userList=new ArrayList<>();
        userList.add(new User("ram@gmail.com","1",""));
        userList.add(new User("hari@gmail.com","3",""));
        return userList;
    }
}