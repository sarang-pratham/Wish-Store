package com.example.wishstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

  public void handle_click(View v){
      AddFragment addFragment = new AddFragment();
      addFragment.handle_click(v);
  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
      bottomNavigationView.setOnItemSelectedListener(item -> {
          Fragment selectedFragment = null;

          switch (item.getItemId()){
              case R.id.nav_home:
                  selectedFragment = new HomeFragment();
                  break;
              case R.id.nav_explore:
                  selectedFragment = new ExploreFragment();
                  break;
              case R.id.nav_post:
                  selectedFragment = new AddFragment();

                  break;
              case R.id.nav_profile:
                  selectedFragment = new ProfileFragment();
                  break;
          }
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
          return true;
      });
    }


}