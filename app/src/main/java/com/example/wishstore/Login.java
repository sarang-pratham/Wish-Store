package com.example.wishstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.login_password);

        fAuth = FirebaseAuth.getInstance();
    }
    public void loginToSignup_handler(View view){
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }

    public void login_handler(View view){
        String email_txt = email.getText().toString().trim();
        String password_txt = password.getText().toString().trim();

        fAuth.signInWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(Login.this, "Login success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Login.this, MainActivity.class));
                email.setText("");
                password.setText("");
                finish();
            }else{
                Toast.makeText(Login.this,
                        "Error "+ Objects.requireNonNull(task.getException()).getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}