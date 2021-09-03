package com.example.wishstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PropFragment extends Fragment {
//    String name, email, phone, userId;
    String postId;
    RecyclerView recyclerView;
    ArrayList usersList;
    ArrayList<UserHelper>userListFinal;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PropFragment() {

    }

//    public PropFragment(String name, String email, String phone, String postId, String userId) {
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.postId = postId;
//        this.userId = userId;
//
//    }
    public PropFragment(String postId){
        this.postId = postId;
    }

    public static PropFragment newInstance(String param1, String param2) {
        PropFragment fragment = new PropFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersList = new ArrayList();
        userListFinal = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_prop, container, false);
        recyclerView = v.findViewById(R.id.pcard_holder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("applied").child(postId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    usersList.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        usersList.add(data.getValue());
                    }
                }

                userRef.getRoot().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data: snapshot.getChildren()){
                            if(usersList.contains(data.getKey())){
                                UserHelper user = data.getValue(UserHelper.class);
                                userListFinal.add(user);
                            }
                        }
                        recyclerView.setAdapter(new PropCardAdapter(userListFinal));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}