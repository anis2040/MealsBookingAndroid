package com.esprit.booksmeals.fragment;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.adapter.CategorieSelectedAdapter;
import com.esprit.booksmeals.model.Restaurant;
import booksmeals.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCategorieFragment extends Fragment {

//https://mealsbooking.000webhostapp.com/api/restaurant-category/

    private static final String URL = "http://mealsbooking.online/api/restaurants";

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
                fpd.dismiss();
                mHandler.removeCallbacksAndMessages(null);

            }
        }

    };



    private RecyclerView rv;
    public ArrayList<Restaurant> restaurantArrayList;

    public  View view;

    private CategorieSelectedAdapter categorieSelectedAdapter;


    Spinner spinner_sort_by, spinner_category;
    LinearLayout linear_filter;
    public DrawerLayout drawer;
    NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar toolbar;


    private String[] text_name = {"The Secret Kitchen", "Work On Fire", "Vibes - The Bistro", "Nosh Farmaiye"};
    private String[] text_location = {"MG Road", "Diwalipura", "sayajiganj", "Alkapuri"};
    private String[] text_menu2 = {"Mexican", "Tunisian", "French", "Italien"};
    ArrayList<String> newList = new ArrayList<String>();
   // private String[] image = {R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img};

public double minPrice,maxPrice;
public String category;
public boolean sortedBy;

public Button all;
    ImageView btnBack;

    public ListCategorieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view = inflater.inflate(R.layout.fragment_list_categorie, container, false);


        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,1000);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        restaurantArrayList = new ArrayList<>();
        for (int i = 0; i < text_name.length; i++) {
            Restaurant restaurant = new Restaurant(text_name[i],text_location[i],text_menu2[i]);
            restaurantArrayList.add(restaurant);
        }



        categorieSelectedAdapter = new CategorieSelectedAdapter(getActivity(), restaurantArrayList);
        RecyclerView.LayoutManager mLayoutManager = new  LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(categorieSelectedAdapter);


      EditText seachEditText = view.findViewById(R.id.seachEditText);
      seachEditText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
              search(s.toString());
          }
      });


        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("back","back");
                getFragmentManager().popBackStackImmediate();
            }
        });


      all = view.findViewById(R.id.all);
      all.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (sortedBy ==true) {

                  Collections.sort(restaurantArrayList, (r1, r2) -> r1.getMinPrice().compareTo(r2.getMinPrice()));
  }
              else
                  Collections.sort(restaurantArrayList, (r1, r2) -> r2.getMinPrice().compareTo(r1.getMinPrice()));

              categorieSelectedAdapter.filterList(restaurantArrayList);
          }
      });


        fetchCategorieItems();
        loading();
        filterSideBar();





        return view;


    }


    private void search(String text) {
        ArrayList<Restaurant> filteredList = new ArrayList<>();

        for (Restaurant item : restaurantArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        categorieSelectedAdapter.filterList(filteredList);

    }



    private void filter(double minPrice, double maxPrice, String categorie, boolean sortedBy) {

        ArrayList<Restaurant> filteredList = new ArrayList<Restaurant>();

        if (sortedBy ==true) {

            Collections.sort(restaurantArrayList, (r1, r2) -> r1.getMinPrice().compareTo(r2.getMinPrice()));
            Collections.sort(filteredList, (r1, r2) -> r1.getMinPrice().compareTo(r2.getMinPrice()));

        }
        else {

            Collections.sort(restaurantArrayList, (r1, r2) -> r2.getMinPrice().compareTo(r1.getMinPrice()));
            Collections.sort(filteredList, (r1, r2) -> r2.getMinPrice().compareTo(r1.getMinPrice()));

//            for (Restaurant item : restaurantArrayList) {
//                System.out.println("price after sort dec" +    item.getMinPrice());
//
//
//            }

        }


        for (Restaurant item : restaurantArrayList)
        {


            if  ( (  item.getMinPrice()  <=  maxPrice)
                    &&
                    (( item.getMinPrice() >=  minPrice ))
                    &&
                    (item.getCategory().toLowerCase().contains(categorie.toLowerCase().trim()))  )
            {

                Log.d("minPrice",minPrice+"");
                Log.d("maxPrice",maxPrice+"");
                Log.d("restoMinPrice",item.getMinPrice() +"");
                Log.d("-----------------","---------------");


                      Log.d("d5al",""+item.getCategory());

                    filteredList.add(item);


            }
        }


        Log.d("restaurantArrayList",restaurantArrayList.size()+"");
        Log.d("filteredList",filteredList.size()+"");


        if (sortedBy ==true)
            Collections.sort(filteredList, (r1, r2) -> r1.getMinPrice().compareTo(r2.getMinPrice()));
            Collections.sort(filteredList, (r1, r2) -> r2.getMinPrice().compareTo(r1.getMinPrice()));

        categorieSelectedAdapter.filterList(filteredList);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();



        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categorieSelectedAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }


    private void fetchCategorieItems() {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        List<Restaurant> items = null;
                        try {
                            Log.e("response",response.get("data").toString());
                            items = new Gson().fromJson(response.get("data").toString(), new TypeToken<List<Restaurant>>() {
                            }.getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        restaurantArrayList.clear();
                        restaurantArrayList.addAll(items);

                        // refreshing recycler view
                        categorieSelectedAdapter.notifyDataSetChanged();
                        isLoaded = true;

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        MyApplication.getInstance().addToRequestQueue(request);


    }




//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.END)) {
//
//            drawer.closeDrawer(Gravity.RIGHT); //OPEN Nav Drawer!
//        } else {
//            finish();
//        }
//    }




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







    private void setLinear_filter() {
        linear_filter = (LinearLayout) view.findViewById(R.id.linear_filter);
        //setActionBar(linear_filter);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setTitle("");

        linear_filter.findViewById(R.id.linear_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Click", "keryu");

                if (drawer.isDrawerOpen(navigationView)) {
                    drawer.closeDrawer(navigationView);
                } else {
                    drawer.openDrawer(navigationView);

                    Log.e("abc", "abc");
                }
            }
        });


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

                minPrice =  minValue.doubleValue();
                maxPrice =  maxValue.doubleValue();
            }
        });



// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {

                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));



            }
        });


        spinner_sort_by = (Spinner) view.findViewById(R.id.spinner_sort_by);


        //        Spinner for Sending Id

        List<String> listFilterPrice = new ArrayList<String>();


        listFilterPrice.add("Order by highest price");
        listFilterPrice.add("Order by lowest  price");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_text, listFilterPrice);
        spinner_sort_by.setAdapter(dataAdapter);


        spinner_sort_by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);


                if (item.toString().equals("Order by highest price"))
                    sortedBy = true;
                else
                    sortedBy = false;


            Log.d("sortedBy",sortedBy+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




            spinner_category = (Spinner) view.findViewById(R.id.spinner_category);


        //        Spinner for Sending Id

        List<String> list1 = new ArrayList<String>();
        for (Restaurant item:restaurantArrayList) {
            list1.add(item.getCategory());
        }
        for (String element : list1) {

            if (!newList.contains(element)) {

                newList.add(element);
                Log.e("list cat: ",newList.toString() );
            }
        }

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_text, newList);

        spinner_category.setAdapter(dataAdapter1);


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
//                if (item != null) {
//                    Toast.makeText(getActivity(), item.toString(),
//                            Toast.LENGTH_SHORT).show();
//                }

                 category = item.toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button filterButton = view.findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter(minPrice,maxPrice,category,sortedBy);
            }
        });

        //Log.d("spinner_category",st+"");


        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        // openDrawer();

        setLinear_filter();



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



}
