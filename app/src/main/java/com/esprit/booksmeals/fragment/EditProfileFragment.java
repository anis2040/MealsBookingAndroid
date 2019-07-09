package com.esprit.booksmeals.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.booksmeals.ApiError;
import com.esprit.booksmeals.ExtraActivity.ChangePhotoDialog;
import com.esprit.booksmeals.ExtraActivity.Init;
import com.esprit.booksmeals.ExtraActivity.UniversalImageLoader;
import com.esprit.booksmeals.actvity.MainActivity;
import com.esprit.booksmeals.model.User;
import com.esprit.booksmeals.utils.Utils;
import booksmeals.R;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment  implements ChangePhotoDialog.OnPhotoReceivedListener {


    private static final String TAG = "EditProfileFragment";

    Call<User> call;
    ApiService service;
    EditText name ,email, password;
    TextView lastname;
    Button btnEdit;


    private static final int SELECT_PICTURE = 0;
    public ImageView restoImage;
    public String mSelectedImagePath;
    public View view;



    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        email = view.findViewById(R.id.editEmail);
        name= view.findViewById(R.id.editNom);
        password= view.findViewById(R.id.editPassword);
        lastname= view.findViewById(R.id.lastname);
        btnEdit= view.findViewById(R.id.btnEdit);
        restoImage = (ImageView) view.findViewById(R.id.profile);

        lastname.setText(HomeFragment.user.getName());

        service = RetroFitBuilder.createService(ApiService.class);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(name.getText());
                String useremail = String.valueOf(email.getText());
                call = service.editUser(HomeFragment.user.getId(),username,useremail);
                call.enqueue(new Callback<User>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if (response.isSuccessful()) {

                            DynamicToast.makeSuccess(getActivity(), "Profile edited !",4000).show();
                            Fragment Reservation2 = new ProfileFragment();
                            loadFragment(Reservation2);

                        } else {
                            if (response.code() == 422) {
                            }
                            if (response.code() == 401) {
                                ApiError apiError = Utils.convertErrors(response.errorBody());
                            }

                        }

                    }
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
            }
        });


        camera();
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }


    public void camera(){


        mSelectedImagePath = null;

        //load the default images by causing an error
        UniversalImageLoader.setImage(null, restoImage, null, "");

        // initiate the dialog box for choosing an image
        ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);


        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Make sure all permissions have been verified before opening the dialog
                 */
                for(int i = 0; i < Init.PERMISSIONS.length; i++){
                    String[] permission = {Init.PERMISSIONS[i]};
                    if(((MainActivity)getActivity()).checkPermission(permission)){
                        if(i == Init.PERMISSIONS.length - 1){
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(EditProfileFragment.this, 0);
                        }
                    }else{
                        ((MainActivity)getActivity()).verifyPermissions(permission);
                    }
                }
            }
        });



    }






    /**
     * Retrieves the selected image from the bundle (coming from ChangePhotoDialog)
     * @param bitmap
     */
    @Override
    public void getBitmapImage(Bitmap bitmap) {
        //get the bitmap from 'ChangePhotoDialog'
        if(bitmap != null) {
            //compress the image (if you like)
            ((MainActivity)getActivity()).compressBitmap(bitmap, 70);
            restoImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void getImagePath(String imagePath) {
        if( !imagePath.equals("")){
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            UniversalImageLoader.setImage(imagePath, restoImage, null, "");
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
