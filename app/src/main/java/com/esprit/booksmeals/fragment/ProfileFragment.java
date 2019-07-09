package com.esprit.booksmeals.fragment;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.actvity.LoginActivity;
import com.esprit.booksmeals.model.User;
import booksmeals.R;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Call<User> call;
    ApiService service;
    TokenManager tokenManager;
    String email;
    String name;
    String role;
    TextView tvName,tvLastname;
    TextView tvEmail;
    TextView tvRole;

    ImageView image;


    public ProfileFragment() {
        // Required empty public constructor
    }
    ProgressDialog progressDialog;
    FlipProgressDialog fpd  ;
    private Handler mHandler;
    private boolean isLoaded = false;
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            mHandler.postDelayed(m_Runnable, 1000);
            if (isLoaded) {
                // progressDialog.hide();
                mHandler.removeCallbacksAndMessages(null);
                fpd.dismiss();
            }
        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loading();

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,2000);

        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = view.findViewById(R.id.tvFirstName);
        tvLastname = view.findViewById(R.id.tvLastName);
        tvEmail = view.findViewById(R.id.tvEmail);
        image = view.findViewById(R.id.avatar);
        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        service = RetroFitBuilder.createServiceWithAuth(ApiService.class,tokenManager);
        call = service.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.w("Profile", "onResponse: " + response );

                if(response.isSuccessful()){
                   email = response.body().getEmail();
                   name = response.body().getName();
                   role = response.body().getRole();
                   tvName.setText(name);
                   tvEmail.setText(email);
                   isLoaded = true;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("Profile: ", "onFailure: " + t.getMessage() );
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                if(AccessToken.getCurrentAccessToken() == null){
                    System.out.println("not logged in yet");


                } else {

                    System.out.println("Logged in");
                    Log.d("imageProfile ", LoginActivity.profile_picture+"");

                   // Picasso.with(getApplicationContext()).load(profile_picture.toString()).into(image);

                    tvName.setText(LoginActivity.first_name);
                    tvLastname.setText(LoginActivity.last_name);
                    tvEmail.setText(LoginActivity.emailProfile);
                  //  Log.d("fotoo",ss);


                    Glide.with(getActivity())
                            .load(LoginActivity.profile_picture)
                            .into(image);



                    // Log.d("ahaya","strtext");
                }
            }
        });
        LinearLayout favorisBtn = (LinearLayout) view.findViewById(R.id.btnFavoris);
        LinearLayout editProfile = (LinearLayout) view.findViewById(R.id.editProfile);
        LinearLayout reservationBtn = (LinearLayout) view.findViewById(R.id.btnReservation);

        favorisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Fragment fragment = new FavorisFragment();
                loadFragment(fragment);


            }
        });

        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Fragment fragment = new ReservationCustomerFragment();
                loadFragment(fragment);


            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Fragment editProfileFragment = new EditProfileFragment();
                loadFragment(editProfileFragment);


            }
        });


        return view;
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void loading(){

        // Set imageList
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.dining_table);
        imageList.add(R.drawable.chef);
        imageList.add(R.drawable.serving_dish);
        imageList.add(R.drawable.dinner);
        imageList.add(R.drawable.menu);




        fpd = new FlipProgressDialog();

        fpd.setImageList(imageList);                              // *Set a imageList* [Have to. Transparent background png recommended]
        fpd.setOrientation("rotationY");

        fpd.setDimAmount(0.8f);
        fpd.setBackgroundColor(Color.parseColor("#FFFFFF"));                            // Set an alpha while image is flipping

        fpd.setImageSize(200);


        fpd.setCornerRadius(200);

        fpd.show(getFragmentManager(),"");                        // Show flip-progress-dialg
        //fpd.dismiss();

    }




}
