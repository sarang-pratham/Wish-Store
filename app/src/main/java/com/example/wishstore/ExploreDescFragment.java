package com.example.wishstore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ExploreDescFragment extends Fragment {
    String title, desc, budget, userId, postId, bids;
    ArrayList appliedList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ExploreDescFragment() {
    }

    public ExploreDescFragment(String title, String desc, String budget, String userId, String postId, String bids) {
        this.title = title;
        this.desc = desc;
        this.budget = budget;
        this.userId = userId;
        this.postId = postId;
        this.bids = bids;
    }

    public static ExploreDescFragment newInstance(String param1, String param2) {
        ExploreDescFragment fragment = new ExploreDescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appliedList = new ArrayList();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_desc, container, false);
        TextView proj_title = view.findViewById(R.id.Edesc_proj_title);
        TextView proj_desc = view.findViewById(R.id.Edesc_proj_desc);
        TextView proj_budget = view.findViewById(R.id.Edesc_proj_budget);

        Button handle_submit = view.findViewById(R.id.btn_submit);

        handle_submit.setOnClickListener(view1 -> {
            DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("posts").child(postId).child("bids");

            if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userId)) {
                mref.getRoot().child("applied").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> users = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            users.add(data.getValue().toString());
                        }
                        if (!users.contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                            Log.d("no child", Boolean.toString(users.contains(userId)));
                            int val = Integer.parseInt(bids) + 1;
                            mref.setValue(Integer.toString(val)).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    appliedList.clear();
                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();


                                    mref.getRoot().child("applied").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                            if (snapshot1.hasChildren()) {
                                                for (DataSnapshot data : snapshot1.getChildren()) {
                                                    appliedList.add(data.getValue());
                                                }
                                            }

                                            appliedList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            mref.getRoot().child("applied").child(postId).setValue(appliedList);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Log.d("canceled", "its canceled!" + error.getMessage());
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Already Applied!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("canceled", "its cancled!" + error.getMessage());
                    }
                });
            } else {
                Toast.makeText(getContext(), "This is Your Post! Cannot Apply!!", Toast.LENGTH_SHORT).show();
            }
        });

        proj_title.setText(title);
        proj_desc.setText(desc);
        proj_budget.setText(budget);

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        assert activity != null;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).addToBackStack(null).commit();
    }
}