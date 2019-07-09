package com.esprit.booksmeals.actvity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.esprit.booksmeals.ExtraActivity.UniversalImageLoader;
import booksmeals.R;
import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.fragment.HomeFragment;
import com.esprit.booksmeals.fragment.ListCategorieFragment;
import com.esprit.booksmeals.fragment.MapFragment;
import com.esprit.booksmeals.fragment.ProfileFragment;
import com.esprit.booksmeals.model.AccessToken;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.facebook.login.LoginManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    private ActionBar toolbar;
    ApiService service;
    TokenManager tokenManager;
    Call<AccessToken> callLogout;
    ProgressBar loader;
    CoordinatorLayout container;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        loader = (ProgressBar) findViewById(R.id.loader);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        service = RetroFitBuilder.createServiceWithAuth(ApiService.class,tokenManager);
        initImageLoader();

        if(tokenManager.getToken() == null){
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }


        // Map permission

        if (Build.VERSION. SDK_INT >= Build.VERSION_CODES. M ) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED )
            {
                String[] permissions = {Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }

        loadFragment(new HomeFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_discover:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_map:
                    MapFragment  fragment1 = new MapFragment();
                    fragment = new MapFragment();
                    loadFragment(fragment1);
                    return true;
                case R.id.navigation_favorites:
                    fragment = new ListCategorieFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;

                    case R.id.navigation_logout:

                        if(com.facebook.AccessToken.getCurrentAccessToken() != null) {
                            tokenManager.deleteToken();
                            DynamicToast.makeWarning(MainActivity.this, "Logged out !").show();
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                            finish();
                        }

                        callLogout = service.logout();
                        callLogout.enqueue(new Callback<AccessToken>() {
                            @Override
                            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                if(response.isSuccessful()){
                                    tokenManager.deleteToken();
                                    DynamicToast.makeWarning(MainActivity.this, "Logged out !").show();
                                    startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<AccessToken> call, Throwable t) {
                            }
                        });

                        return true;
            }
            return false;
        }


    };



    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String [] permissions, @NonNull int [] grantResults) {
        super .onRequestPermissionsResult (requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If the permission request has been canceled the array comes empty.
                if (grantResults. length > 0
                        && grantResults [0] == PackageManager. PERMISSION_GRANTED ) {
                    // Permission ceded, recreates activity to load navigation_map, will only be executed once
                    this .recreate ();
                }
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack("container");
        transaction.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callLogout != null) {
            callLogout.cancel();
            callLogout = null;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showForm(){
        TransitionManager.beginDelayedTransition(container);
        container.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }






    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }




    /**
     * Compress a bitmap by the @param "quality"
     * Quality can be anywhere from 1-100 : 100 being the highest quality.
     * @param bitmap
     * @param quality
     * @return
     */
    public Bitmap compressBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return bitmap;
    }




    /**
     * Checks to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAYT BE CHECKED AT A TIME
     * @param permission
     * @return
     */
    public boolean checkPermission(String[] permission){
        Log.d(TAG, "checkPermission: checking permissions for:" + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(
                MainActivity.this,
                permission[0]);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: \n Permissions was not granted for: " + permission[0]);
            return false;
        }else{
            return true;
        }
    }

    /**
     * Generalized method for asking permission. Can pass any array of permissions
     * @param permissions
     */
    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(
                MainActivity.this,
                permissions,
                REQUEST_CODE
        );
    }

}
