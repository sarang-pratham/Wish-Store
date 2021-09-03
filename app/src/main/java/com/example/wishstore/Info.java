package com.example.wishstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Info extends AppCompatActivity {
    EditText username, location, ph_no;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        username = findViewById(R.id.username);
        location = findViewById(R.id.location);
        ph_no = findViewById(R.id.contact);
    }
    public void continue_handler(View view){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String username_txt = username.getText().toString().trim();
        String location_txt = location.getText().toString().trim();
        String ph_no_txt = ph_no.getText().toString().trim();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String emailId = extras.getString("emailId");
        String uuid = extras.getString("UUID");

        UserHelper helperClass = new UserHelper(username_txt,location_txt,ph_no_txt,emailId);

        reference.child(uuid).setValue(helperClass).addOnSuccessListener(unused -> {
            startActivity(new Intent(Info.this, MainActivity.class));
            finish();
        }).addOnFailureListener(e -> Toast.makeText(Info.this, "something went wrong "+ e.getMessage(), Toast.LENGTH_LONG).show());


    }

}