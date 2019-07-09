package com.esprit.booksmeals.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.actvity.GridSpacingItemDecoration;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.adapter.RestaurantAdapter;
import com.esprit.booksmeals.model.Categorie;
import com.esprit.booksmeals.model.Reservation;
import com.esprit.booksmeals.model.Restaurant;
import com.esprit.booksmeals.model.User;
import booksmeals.R;
import com.esprit.booksmeals.adapter.CategorieAdapter;
import com.esprit.booksmeals.adapter.PagerAdapterForBanner;
import com.esprit.booksmeals.adapter.TopTenAdapter;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    ApiService service;
    TokenManager tokenManager;
    Call<User> call;
    FlipProgressDialog fpd  ;
    private Handler mHandler;
    private boolean isLoaded = false;
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            mHandler.postDelayed(m_Runnable, 1000);
            if (isLoaded) {
                fpd.dismiss();
                mHandler.removeCallbacksAndMessages(null);

            }
        }

    };

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String URL = "http://mealsbooking.online/api/restaurants";
    private static final String URLTopTen = "http://mealsbooking.online/api/topten";
    private RecyclerView recyclerView,recyclerViewTopTen;
    private List<Restaurant> restaurantList;
    private RestaurantAdapter mAdapter;
    private ArrayList<Restaurant> topTenList;
    private TopTenAdapter topTenAdapter;
    private ArrayList<Categorie> categorieList;
    private CategorieAdapter categorieAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    public    View view;


    LinearLayout linear_filter,nearBy;

    public DrawerLayout drawer;
    NavigationView navigationView;

    public PagerAdapterForBanner pagerAdapterForBanner;

    public static User user;

    Integer resimage[]={R.drawable.italienne,R.drawable.libanaise,R.drawable.tunisienne,R.drawable.chinoise,R.drawable.turques,R.drawable.mexicaine,R.drawable.marroc};
    String  txtregulargift[]={"Italien","French","Tunisien","Chinoise","Turkish","Mexican","Marrocaine"};
    Spinner spinner_sort_by, spinner_category;

    private ArrayList<Reservation> reservationList;
    public static ArrayList<User> UserArrayList = new ArrayList<User>();
    ImageView btnBack;
    public ImageButton favorisButtom;

    public HomeFragment() {
        // Required empty public constructor
    }

    private ArrayList<Reservation> Reservations() {

        String url ="http://mealsbooking.online/api/users";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // List<Restaurant> items = null;
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                int id = (int) jsonobject.get("id");
                                String  name = (String) jsonobject.get("name");
                                String  email = (String) jsonobject.get("email");
                                User newUser = new User( id, name,email);
                                UserArrayList.add(newUser);
                            }
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
        return reservationList;
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));

        service = RetroFitBuilder.createServiceWithAuth(ApiService.class,tokenManager);
        loading();
        Reservations();

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,1000);

        call = service.getUser();
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                user = response.body();
                if(response.isSuccessful()){
//                    if (response.body().getRole().equals("restaurant") ) {
//                        startActivity(new Intent(getActivity(), DashboardActivity.class));
                       isLoaded = true;
//                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

        view =  inflater.inflate(R.layout.fragment_home, container, false);

        nearBy = (LinearLayout) view.findViewById(R.id.nearBy);
        nearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFragment(new MapFragment());
            }
        });

        filterSideBar();

        // Inflate the layout for this fragment
//recycler view all
        recyclerView = view.findViewById(R.id.recycler_view);

        restaurantList = new ArrayList<>();

        mAdapter = new RestaurantAdapter(getActivity(), restaurantList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        fetchRestaurantsItems();

//recyclerview top ten
        recyclerViewTopTen = view.findViewById(R.id.recyclerview_top_ten);
        topTenList = new ArrayList<>();

        topTenAdapter = new TopTenAdapter(getActivity(),topTenList);

        RecyclerView.LayoutManager mmLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTopTen.setLayoutManager(mmLayoutManager);
        recyclerViewTopTen.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTopTen.setAdapter(topTenAdapter);

        fetchRestaurantsTopTenItems();



 //recyclerview Categorie

        recyclerView = view.findViewById(R.id.recyclerview4);
        categorieList = new ArrayList<>();

        for (int i = 0; i < resimage.length; i++) {
            Categorie categorie = new Categorie(resimage[i],txtregulargift[i]);
            categorieList.add(categorie);
        }

        categorieAdapter = new CategorieAdapter(getActivity(),categorieList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categorieAdapter = new CategorieAdapter(getActivity(),categorieList);
        recyclerView.setAdapter(categorieAdapter);
        /*Banner ViewPager Code*/
        ViewPager  viewPager = (ViewPager) view.findViewById(R.id.viewpagerr);
        pagerAdapterForBanner = new PagerAdapterForBanner(getFragmentManager());
        viewPager.setAdapter(pagerAdapterForBanner);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int fragments = getFragmentManager().getBackStackEntryCount();
                if (fragments == 1) {
                    // make layout invisible since last fragment will be removed
                    getActivity().finish();
                }
            }
        });

        return view;
    }
    private void fetchRestaurantsItems() {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        List<Restaurant> items = null;
                        try {
                            items = new Gson().fromJson(response.get("data").toString(), new TypeToken<List<Restaurant>>() {
                            }.getType());
                            isLoaded = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        restaurantList.clear();
                        restaurantList.addAll(items);
                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        MyApplication.getInstance().addToRequestQueue(request);


    }



    private void fetchRestaurantsTopTenItems() {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, URLTopTen, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        List<Restaurant> items = null;
                        try {
                            items = new Gson().fromJson(response.get("data").toString(), new TypeToken<List<Restaurant>>() {
                            }.getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        topTenList.clear();
                        topTenList.addAll(items);
                        topTenList.size();
                        // refreshing recycler view
                        topTenAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        MyApplication.getInstance().addToRequestQueue(request);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void filterSideBar(){

        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar1);
        final TextView tvMin = (TextView) view.findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) view.findViewById(R.id.textMin2);


// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("$ " + String.valueOf(minValue));
                tvMax.setText("$ " + String.valueOf(maxValue));
            }
        });



// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
            }
        });


        spinner_sort_by = (Spinner) view.findViewById(R.id.spinner_sort_by);
        //        Spinner for Sending Id
        List<String> list = new ArrayList<String>();
        list.add("Order by highest price");
        list.add("Order by lowest  price");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_text, list);
        spinner_sort_by.setAdapter(dataAdapter);
        spinner_category = (Spinner) view.findViewById(R.id.spinner_category);
        //        Spinner for Sending Id
        List<String> list1 = new ArrayList<String>();
        list1.add("Italien");
        list1.add("Lebanese");
        list1.add("Tunisien");
        list1.add("chinese");
        list1.add("Turkish");
        list1.add("Mexican");
        list1.add("Morocan");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_text, list1);
        spinner_category.setAdapter(dataAdapter1);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };


        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        getActivity().invalidateOptionsMenu();



    }



    public void openDrawer(){
        drawer.openDrawer(navigationView);
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
    }


    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item){


       DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;


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
        transaction.addToBackStack("container");
        transaction.commit();
    }
}
