package com.esprit.booksmeals.actvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import booksmeals.R;
import com.esprit.booksmeals.TokenManager;

public class WelcomeActivity extends AppCompatActivity {

    TextView signIn,signUp;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));


        if(tokenManager.getToken().getAccessToken() != null){
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }

        if(com.facebook.AccessToken.getCurrentAccessToken() != null) {

            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
        signIn = findViewById(R.id.signin);
        signUp = findViewById(R.id.Signup);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);

            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }
}
