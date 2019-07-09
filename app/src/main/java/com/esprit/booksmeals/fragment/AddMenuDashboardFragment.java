package com.esprit.booksmeals.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.esprit.booksmeals.ExtraActivity.ChangePhotoDialog;
import com.esprit.booksmeals.ExtraActivity.Init;
import com.esprit.booksmeals.ExtraActivity.UniversalImageLoader;
import com.esprit.booksmeals.actvity.DashboardActivity;
import booksmeals.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMenuDashboardFragment extends Fragment implements ChangePhotoDialog.OnPhotoReceivedListener {
    private static final String TAG = "AddMenuDashboardFragment";
    TextInputLayout name,price;
    private static final int SELECT_PICTURE = 0;
    private ImageView restoImage;
    private String mSelectedImagePath;
    public View view;
    public AddMenuDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_add_menu, container, false);
        name = (TextInputLayout) view.findViewById(R.id.name);
        price = (TextInputLayout) view.findViewById(R.id.price);
        restoImage = (ImageView) view.findViewById(R.id.logo_img);
        camera();

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
                    if(((DashboardActivity)getActivity()).checkPermission(permission)){
                        if(i == Init.PERMISSIONS.length - 1){
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(AddMenuDashboardFragment.this, 0);
                        }
                    }else{
                        ((DashboardActivity)getActivity()).verifyPermissions(permission);
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
            ((DashboardActivity)getActivity()).compressBitmap(bitmap, 70);
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


}
