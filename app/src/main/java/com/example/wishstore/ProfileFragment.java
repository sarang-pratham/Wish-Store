package com.example.wishstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    TextView username, location, email, phone;
    Button logout_handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.profile_name);
        location = view.findViewById(R.id.p_location);
        email = view.findViewById(R.id.p_email);
        phone = view.findViewById(R.id.p_phone);
        logout_handler = view.findViewById(R.id.logout_handler);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        logout_handler.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(view1.getContext(), Register.class));
            requireActivity().finish();
        });



        assert user != null;
        String userId = user.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(Objects.requireNonNull(snapshot.child("username").getValue()).toString());
                location.setText(Objects.requireNonNull(snapshot.child("location").getValue()).toString());
                email.setText(Objects.requireNonNull(snapshot.child("email").getValue()).toString());
                phone.setText(Objects.requireNonNull(snapshot.child("phone").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view.getContext(), "failed " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
