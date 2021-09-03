package com.example.wishstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class DescFragment extends Fragment {
    String title, desc, budget, bids, postId;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DescFragment() {

    }
    public DescFragment(String title, String desc, String budget, String bids, String postId) {
        this.title = title;
        this.desc = desc;
        this.budget = budget;
        this.bids = bids;
        this.postId = postId;
    }


    public static DescFragment newInstance(String param1, String param2) {
        DescFragment fragment = new DescFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_desc, container, false);
        TextView proj_title = view.findViewById(R.id.desc_proj_title);
        TextView proj_desc = view.findViewById(R.id.desc_proj_desc);
        TextView proj_budget  = view.findViewById(R.id.desc_proj_budget);
        TextView proj_bids = view.findViewById(R.id.bids_count);
        TextView show_proposals = view.findViewById(R.id.desc_proj_bids);
        Button delete_handler = view.findViewById(R.id.delete_proj_btn);

        show_proposals.setOnClickListener(view1 -> {
            AppCompatActivity activity = (AppCompatActivity) view1.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PropFragment(postId)).addToBackStack(null).commit();
        });
        delete_handler.setOnClickListener(view12 -> FirebaseDatabase.getInstance().getReference().child("posts").child(postId).removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseDatabase.getInstance().getReference().child("applied").child(postId).removeValue();
                Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) view12.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        }));

        proj_title.setText(title);
        proj_desc.setText(desc);
        proj_budget.setText(budget);
        proj_bids.setText(bids);

        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        assert activity != null;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).addToBackStack(null).commit();
    }
}