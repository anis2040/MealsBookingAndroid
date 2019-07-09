package com.esprit.booksmeals.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.esprit.booksmeals.ApiError;
import booksmeals.R;
import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.model.AccessToken;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.esprit.booksmeals.utils.Utils;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView loginTv;
    TextInputLayout nameInput;
    TextInputLayout emailInput;
    TextInputLayout passwordInput;
    CheckBox roleChbx;
    TextView registerBtn;
    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validation;
    TokenManager tokenManager;
    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = (TextView)  findViewById(R.id.registerBtn);
        loginTv = (TextView)  findViewById(R.id.loginTv);
        nameInput = (TextInputLayout)  findViewById(R.id.nameInput);
        emailInput = (TextInputLayout)  findViewById(R.id.emailInput);
        passwordInput = (TextInputLayout)  findViewById(R.id.passwordInput);
        roleChbx = (CheckBox)  findViewById(R.id.roleChbx);

        validation = new AwesomeValidation(ValidationStyle.BASIC);


        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });




        service = RetroFitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        setupRules();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getEditText().getText().toString();
                String email = emailInput.getEditText().getText().toString();
                String password = passwordInput.getEditText().getText().toString();
                String role = roleChbx.getText().toString();

                if (roleChbx.isChecked()) {
                    role = "restaurant";
                } else {
                    role = "user";
                }
                nameInput.setError(null);
                emailInput.setError(null);
                passwordInput.setError(null);
                roleChbx.setError(null);
                validation.clear();

                if (validation.validate()) {
                    call = service.register(name, email, password, role);
                    call.enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {


                            if (response.isSuccessful()) {
                               // Log.w(TAG, "onResponse: " + response);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                                Toast.makeText(RegisterActivity.this, "Register successfully !", Toast.LENGTH_LONG).show();
                                tokenManager.saveToken(response.body());
                            } else {

                                handleErrors(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                        }
                    });
                }
            }
        });

    }

    public void setupRules(){
        validation.addValidation(this, R.id.nameInput, RegexTemplate.NOT_EMPTY, R.string.err_name);
        validation.addValidation(this, R.id.emailInput, Patterns.EMAIL_ADDRESS, R.string.err_email);
        validation.addValidation(this, R.id.passwordInput, "[a-zA-Z0-9]{6,}", R.string.err_password);
    }

    private void handleErrors(ResponseBody responseBody) {
        ApiError apiError = Utils.convertErrors(responseBody);
        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("name")) {
                nameInput.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email")) {
                emailInput.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                passwordInput.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("role")) {
                roleChbx.setError(error.getValue().get(0));
            }
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }
}
