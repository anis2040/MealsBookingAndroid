package com.esprit.booksmeals.fragment;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.booksmeals.ApiError;
import com.esprit.booksmeals.actvity.GridSpacingItemDecoration;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.model.Menu;
import com.esprit.booksmeals.model.Restaurant;
import com.esprit.booksmeals.model.Review;
import com.esprit.booksmeals.model.User;
import com.esprit.booksmeals.utils.Utils;
import booksmeals.R;
import com.esprit.booksmeals.adapter.RestaurantDetailAdapter2;
import com.esprit.booksmeals.adapter.RestaurantPagerAdapter;
import com.esprit.booksmeals.adapter.ReviewAdapter;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ReservationFragment extends Fragment {

    private int id;
    private static final String DESCRIBABLE_KEY = "describable_key";
    Call<Review> callReview;
    ApiService service;
    public String name;
    private  ArrayList<Restaurant> restaurantList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    public TextView nameUserReview ,Reviews_Id, rating,to,description_detail,name_detail_bg,
            address_detail_bg,nameResto,addressResto,tvTel,tvPosition,from,tvCategory;
    private RestaurantDetailAdapter2 food5_detail_adapter2;
    private ReviewAdapter reviewAdapter;
    //viewpager code

    private RestaurantPagerAdapter loginPagerAdapter;
    private ViewPager viewPager;
    private CircleIndicator indicator;
    JSONArray jsonRatings;
    JSONArray jsonMenu;
    Button submit;
    RatingBar RatingBar_Id,ratingReview;
    EditText reviewEdt;
    public ArrayList<Review> reviewArrayList = new ArrayList<Review>();

    @SuppressLint("ValidFragment")
    public ReservationFragment(int id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_reservation, container, false);

//        Log.e("the user is: ", user.toString());
        name_detail_bg = view.findViewById(R.id.tvNameRestoBG);
        nameResto = view.findViewById(R.id.tvNameReso);

        Reviews_Id = view.findViewById(R.id.Reviews_Id);

        address_detail_bg = view.findViewById(R.id.tvPositionRestoBG);
        addressResto = view.findViewById(R.id.tvAddress);
        description_detail = view.findViewById(R.id.description_detail);

        tvTel = view.findViewById(R.id.tvTel);
        tvPosition = view.findViewById(R.id.tvPosition);
        tvCategory = view.findViewById(R.id.tvCategory);
        rating = view.findViewById(R.id.rating);


        from = view.findViewById(R.id.from);
        to = view.findViewById(R.id.to);


        RatingBar_Id = view.findViewById(R.id.RatingBarById);

        nameUserReview = view.findViewById(R.id.nameUserReview);

        ratingReview = view.findViewById(R.id.ratingReview);

        reviewEdt = view.findViewById(R.id.reviewEdt);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("back","back");
                getFragmentManager().popBackStackImmediate();
            }
        });

     nameUserReview.setText(HomeFragment.user.getName());


     ImageButton btn = view.findViewById(R.id.tel);
     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
     });

//        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
//            @Override
//            public void onInitialized() {
//                if(AccessToken.getCurrentAccessToken() == null){
//
//                } else {
//                    System.out.println("Logged in");
//                    nameUserReview.setText(first_name);
//                }
//            }
//        });

        // Post Review
        service = RetroFitBuilder.createService(ApiService.class);
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ratings = String.valueOf(ratingReview.getRating());
                String review = String.valueOf(reviewEdt.getText());
                int user_id = HomeFragment.user.getId();
//                for (Review r:reviewArrayList) {
//                    if(r.getUser_id()== user_id && r.getRestaurant_id() == id){
//                        submit.setOnClickListener(null);
//                        DynamicToast.makeError(getActivity(), "You can't review the same restaurant more than once!",4000).show();
//
//                    }
//                }


                callReview = service.addReview(review,ratings,user_id,id);





                callReview.enqueue(new Callback<Review>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<Review> call, retrofit2.Response<Review> response) {

                        if (response.isSuccessful()) {


                            DynamicToast.makeSuccess(getActivity(), "Review submited successfully !",4000).show();
                            Fragment Reservation2 = new ReservationFragment(id);
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
                    public void onFailure(Call<Review> call, Throwable t) {
                        Log.e( "onFailure: " , t.getMessage());

                    }
                });
            }
        });



//Recycle 2

        recyclerView2 = view.findViewById(R.id.RecyclerView2_Food5_Detail_Id);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        loginPagerAdapter = new RestaurantPagerAdapter(getFragmentManager(),id);
        viewPager.setAdapter(loginPagerAdapter);
        indicator.setViewPager(viewPager);
        loginPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());


        //Recycle 3


      //  notifyDataSetChanged();
        recyclerView = view.findViewById(R.id.recyclerViewReviw);
        RestaurantById();


        LinearLayout btnR = (LinearLayout) view.findViewById(R.id.btnR);

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Reservation2 = new Reservation2Fragment();
                loadFragment(Reservation2);
            }
        });
        return view;
    }

    private ArrayList<Restaurant> RestaurantById() {

        String url ="http://mealsbooking.online/api/restaurant/" + id;
        Log.e("Urlll",url+"");
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // List<Restaurant> items = null;
                        try {
                            JSONObject jsonArray = response.getJSONObject("data");
                            JSONObject jsonImage = response.getJSONObject("data").getJSONObject("image");
                            jsonRatings = response.getJSONObject("data").getJSONArray("ratings");
                            jsonMenu = response.getJSONObject("data").getJSONArray("menu");
                            String name =  (String) jsonArray.get("name");
                            String address =  (String) jsonArray.get("address");
                            String description =  (String) jsonArray.get("description");
                            int phone =  (int) jsonArray.get("phone");
                            int priceMin =  (int) jsonArray.get("priceMin");
                            int priceMax =  (int) jsonArray.get("priceMax");
                            String category =  (String) jsonArray.get("category");
                            nameResto.setText(name);
                            address_detail_bg.setText(address);
                            addressResto.setText(address);
                            description_detail.setText(description);
                            tvTel.setText(String.valueOf(phone));
                            tvPosition.setText(address);
                            from.setText(String.valueOf(priceMin));
                            to.setText(String.valueOf(priceMax));
                            tvCategory.setText(category);
                           // JSONObject photoo = jsonArrayy.getJSONObject("photo1");
                            String photo1 = (String) jsonImage.get("photo1");
                            String photo2 = (String) jsonImage.get("photo2");
                            String photo3 = (String) jsonImage.get("photo3");

                             ArrayList<Menu> menuArrayList = new ArrayList<Menu>();
                            for (int i = 0; i < jsonMenu.length(); i++) {
                                JSONObject jsonobject = jsonMenu.getJSONObject(i);
                                String  food_name = (String) jsonobject.get("food_name");
                                int  price = (int) jsonobject.get("price");
                                String  photoMenu = (String) jsonobject.get("photo");
                                Menu newMenu = new Menu( food_name,price,id,photoMenu);
                                menuArrayList.add(newMenu);
                            }
                            Log.e("list menu",menuArrayList.toString());
                            food5_detail_adapter2 = new RestaurantDetailAdapter2(getActivity(),menuArrayList);


                            RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
                            recyclerView2.setLayoutManager(mLayoutManager1);
                            recyclerView2.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
                            recyclerView2.setItemAnimator(new DefaultItemAnimator());
                            recyclerView2.setAdapter(food5_detail_adapter2);
                            //recyclerView.setNestedScrollingEnabled(false);
                            food5_detail_adapter2.notifyDataSetChanged();


                            double ratingMoy = 0.0;

                            for (int i = 0; i < jsonRatings.length(); i++) {
                                JSONObject jsonobject = jsonRatings.getJSONObject(i);
                                //JSONObject ss = (JSONObject) jsonArrayyy.getJSONObject(i);
                                String  rating = (String) jsonobject.get("rating");
                                String  review = (String) jsonobject.get("review");
                                int  user_id = (int) jsonobject.get("user_id");
                                String username = "Anis";
                                Log.e("username:" , HomeFragment.UserArrayList.toString());
                                for (User u: HomeFragment.UserArrayList) {
                                    if (u.getId() == user_id)
                                        username = u.getName();
                                }
                                Log.e("username:" , username);
                                ratingMoy = ratingMoy+Float.valueOf(rating)/jsonRatings.length();
                                Review newReview = new Review( rating, review,id,username);
                                reviewArrayList.add(newReview);
                            }
                            String moy=   String.valueOf(ratingMoy).substring(0 ,3);
                            rating.setText(moy);


                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

                            reviewAdapter = new ReviewAdapter(getActivity(),reviewArrayList);

                            recyclerView.setLayoutManager(layoutManager2);
                            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(reviewAdapter);
                            reviewAdapter.notifyDataSetChanged();


                          Reviews_Id.setText(String.valueOf(jsonRatings.length()) + " Reviews");

                          RatingBar_Id.setRating((float) ratingMoy);

                          RatingBar_Id.setRating(Float.parseFloat(ratingMoy+""));
                            Drawable drawable = RatingBar_Id.getProgressDrawable();
                            drawable.setColorFilter(Color.parseColor("#FFD700"), PorterDuff.Mode.SRC_ATOP);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        MyApplication.getInstance().addToRequestQueue(request);
        return restaurantList;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
