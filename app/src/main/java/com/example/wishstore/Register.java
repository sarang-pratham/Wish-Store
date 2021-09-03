package com.example.wishstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText)findViewById(R.id.signup_email);
        password = (EditText)findViewById(R.id.signup_password);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }


    public void signupToLogin_handler(View view){
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void register_handler(View view){
        String email_txt;
        String password_txt;
        if(!email.getText().toString().equals("") || !password.getText().toString().equals("")) {
            email_txt = email.getText().toString().trim();
            password_txt = password.getText().toString().trim();

        fAuth.createUserWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(Register.this,"Sign Up success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Register.this, Info.class);
                Bundle extras = new Bundle();
                extras.putString("UUID", Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid());
                extras.putString("emailId", email_txt);
                intent.putExtras(extras);
                startActivity(intent);
                email.setText("");
                password.setText("");
            }else {
                Toast.makeText(Register.this,"Error "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        }else{
            Toast.makeText(Register.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

}