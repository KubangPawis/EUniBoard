package com.example.euniboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //EVENTS
        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(e -> goToMainMenu(e));
    }

    public void goToMainMenu(View v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}