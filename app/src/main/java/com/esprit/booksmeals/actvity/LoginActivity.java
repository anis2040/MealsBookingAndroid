package com.esprit.booksmeals.actvity;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.esprit.booksmeals.ApiError;
import booksmeals.R;
import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.model.AccessToken;
import com.esprit.booksmeals.model.UserResponse;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.esprit.booksmeals.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public static  URL profile_picture;
    public static  String ss;
    public  String accesstoken;

    TextView txtEmail,txtBirthdy,txtFriends;
    ProgressDialog mDialog;
    ImageView imgAvatar;
    TextView signupBtn;
    TextView signinBtn;
    ApiService service;
    TokenManager tokenManager;
    Call<AccessToken> call;
    Call<UserResponse> userCall;
    AwesomeValidation validator;
    TextInputLayout edtUsername;
    TextInputLayout edtPassword;
    ProgressBar loader;
    RelativeLayout container;
    String accessToken;

    CallbackManager callbackManager;
    LoginButton loginButton;

    private static final String TAG = "LoginActivity";
    public static String emailProfile,first_name,last_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsername = (TextInputLayout) findViewById(R.id.user);
        edtPassword = (TextInputLayout) findViewById(R.id.password);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        service = RetroFitBuilder.createService(ApiService.class);

        loader = (ProgressBar) findViewById(R.id.loader);
        container = (RelativeLayout) findViewById(R.id.login_f);

        validator = new AwesomeValidation(ValidationStyle.BASIC);
        setupRules();

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions("email","public_profile");
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();
                 accesstoken = loginResult.getAccessToken().getToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        getData(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name, last_name, email, id,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                DynamicToast.makeSuccess(LoginActivity.this, "Login successfully !",4000).show();
                tokenManager.saveToken(tokenManager.getToken());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        signupBtn = (TextView) findViewById(R.id.create);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        final SharedPreferences sh = getSharedPreferences("login_and_password",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sh.edit();

        if(tokenManager.getToken().getAccessToken() != null){
            tokenManager.saveToken(tokenManager.getToken());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        signinBtn = (TextView) findViewById(R.id.signinBtn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                String name = edtUsername.getEditText().getText().toString();
                String password = edtPassword.getEditText().getText().toString();

                editor.putString("login",edtUsername.toString());
                editor.commit();
                edtUsername.setError(null);
                edtPassword.setError(null);
                validator.clear();
                if (validator.validate()) {
                    showLoading();

                    call = service.login(name, password);
                    call.enqueue(new Callback<AccessToken>() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            if (response.isSuccessful()) {
                                DynamicToast.makeSuccess(LoginActivity.this, "Login successfully !",4000).show();
                                tokenManager.saveToken(response.body());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                if (response.code() == 422) {
                                    handleErrors(response.errorBody());
                                }
                                if (response.code() == 401) {
                                    ApiError apiError = Utils.convertErrors(response.errorBody());
                                    DynamicToast.makeError(LoginActivity.this, apiError.getMessage()).show();
                                }
                                showForm();
                            }
                        }
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            showForm();
                        }
                    });
                }
            }
        });

    }

    public void getData(JSONObject object) {

        try {
            profile_picture = new URL("https://graph.facebook.com/me/picture?type=large&method=GET&access_token="+ accesstoken );
            emailProfile =object.getString("email");
            first_name =object.getString("first_name");
            last_name =object.getString("last_name");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showForm(){
        TransitionManager.beginDelayedTransition(container);
        container.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }
    private void handleErrors(ResponseBody response) {
        ApiError apiError = Utils.convertErrors(response);
        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("username")) {
                edtUsername.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                edtPassword.setError(error.getValue().get(0));
            }
        }

    }
    public void setupRules() {
        validator.addValidation(this, R.id.user, Patterns.EMAIL_ADDRESS, R.string.err_email);
        validator.addValidation(this, R.id.password, RegexTemplate.NOT_EMPTY, R.string.err_password);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showLoading(){
        TransitionManager.beginDelayedTransition(container);
        container.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }


public void displayUserInfo(JSONObject object){
        String first_name, last_name, email, id;

    try {
        first_name = object.getString("first_name");
        last_name = object.getString("last_name");
        email = object.getString("email");
        id = object.getString("id");
    } catch (JSONException e) {
        e.printStackTrace();
    }




}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void printKeyHash(){
        try{
            PackageInfo info= getPackageManager().getPackageInfo("com.example.anis.booksmeals", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}