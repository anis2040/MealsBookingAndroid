package com.esprit.booksmeals.fragment;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.model.Meals;
import com.esprit.booksmeals.model.User;
import booksmeals.R;
import com.esprit.booksmeals.adapter.MealsDashboardAdapter;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDashboardFragment extends Fragment {

    ProgressDialog progressDialog;
    FlipProgressDialog fpd  ;
    Call<User> call;
    ApiService service;
    TokenManager tokenManager;
    String email;
    String name;
    TextView tvName;
    TextView tvEmail;
    TextView editbtn;
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


    private int[] Image = {R.drawable.italienne,R.drawable.food1,R.drawable.mexicaine,R.drawable.chinoise,R.drawable.turques,R.drawable.italienne};
    private ArrayList<Meals> mealsArrayList;
    private MealsDashboardAdapter mealsDashboardAdapter;


    public ProfileDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_profile_dashboard, container, false);


        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
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
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                R.drawable.pro2);

        ImageView imgv = (ImageView) view.findViewById(R.id.banar1);
         editbtn =  view.findViewById(R.id.edit_btn);


         editbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Fragment fragment = new EditProfileDashboardFragment();
                 loadFragment(fragment);
                 Toast.makeText(getActivity(),"Edit My profile", Toast.LENGTH_LONG).show();

             }
         });



        //  Bitmap bitmap = StringToBitMap(imgv);
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        imgv.setImageBitmap(circleBitmap);

       /* ImageView imgv = (ImageView) findViewById(R.id.banar1);
        imgv.setImageBitmap(StringToBitMap(immg));
        */




        return view;





    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerDashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
