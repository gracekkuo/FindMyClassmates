package com.example.findmyclassmates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    private ArrayList<DepartmentItem> categoryList;

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    public CourseViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CourseViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseViewFragment newInstance(/*String param1, String param2*/) {
        CourseViewFragment fragment = new CourseViewFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_view, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the list of categories
        categoryList = new ArrayList<>();
        categoryList.add(new DepartmentItem("Department 1", new String[]{"Item 1", "Item 2", "Item 3"}));
        categoryList.add(new DepartmentItem("Department 2", new String[]{"Item 4", "Item 5", "Item 6"}));
        categoryList.add(new DepartmentItem("Department 3", new String[]{"Item 7", "Item 8", "Item 9"}));
        categoryList.add(new DepartmentItem("Department 4", new String[]{"Item 10", "Item 11", "Item 12"}));
        categoryList.add(new DepartmentItem("Department 5", new String[]{"Item 13", "Item 14", "Item 15"}));

        DepartmentAdapter adapter = new DepartmentAdapter(categoryList);
        recyclerView.setAdapter(adapter);

        return view;

        //return inflater.inflate(R.layout.fragment_course_view, container, false);
    }
}