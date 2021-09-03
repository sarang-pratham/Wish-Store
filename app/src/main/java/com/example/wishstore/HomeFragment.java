package com.example.wishstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
//    cardAdapter adapter;
    ArrayList<PostsHelper> postData;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = v.findViewById(R.id.card_holder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userId = user.getUid();

//        FirebaseRecyclerOptions<PostsHelper> options =
//                new FirebaseRecyclerOptions.Builder<PostsHelper>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("posts"), PostsHelper.class)
//                        .build();

        postData = new ArrayList<>();
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("posts");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    PostsHelper post = data.getValue(PostsHelper.class);
                    assert post != null;
                    if(post.getUserId().equals(userId)){
                       postData.add(post);
                    }
                }
                recyclerView.setAdapter(new cardAdapter(postData));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        adapter = new cardAdapter(options);
//        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}

