package com.example.wishstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddFragment extends Fragment {
    EditText title, desc, budget;
    Button post_handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.post_title);
        desc = view.findViewById(R.id.description);
        budget = view.findViewById(R.id.budget);
        post_handler = view.findViewById(R.id.post_btn);

        post_handler.setOnClickListener(v -> handle_click(v));
    }

    public void handle_click(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String userId = user.getUid();

//        DatabaseReference mRef = database.getReference().child("posts").child(userId).push();

        DatabaseReference mRef = database.getReference().child("posts").push();
        String key = mRef.getKey();


        String title_txt = title.getText().toString().trim();
        String description = desc.getText().toString().trim();
        String budget_txt = budget.getText().toString().trim();
        PostsHelper post = new PostsHelper(title_txt, description, budget_txt,"0",userId, key);

        mRef.setValue(post).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(v.getContext(), "success", Toast.LENGTH_LONG).show();
                title.setText("");
                desc.setText("");
                budget.setText("");
            } else {
                Toast.makeText(v.getContext(), "Failed " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
