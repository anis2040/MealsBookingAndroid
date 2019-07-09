package com.esprit.booksmeals.actvity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.esprit.booksmeals.ExtraActivity.UniversalImageLoader;
import booksmeals.R;
import com.esprit.booksmeals.fragment.MainDashboardFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;

public class DashboardActivity extends AppCompatActivity {
    Fragment fragment;
    private static final String TAG = "DashboardActivity";

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fragment = new MainDashboardFragment();
        loadFragment(fragment);


        initImageLoader();




    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerDashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(DashboardActivity.this);
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
                DashboardActivity.this,
                permission[0]);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: \n Permissions was not granted for: " + permission[0]);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch(requestCode){
            case REQUEST_CODE:
                for(int i = 0; i < permissions.length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[i]);
                    }else{
                        break;
                    }
                }
                break;
        }
    }



    /**
     * Generalized method for asking permission. Can pass any array of permissions
     * @param permissions
     */
    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(
                DashboardActivity.this,
                permissions,
                REQUEST_CODE
        );
    }






}
