package com.esprit.booksmeals.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.model.Restaurant;
import booksmeals.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    private static final String URL = "http://mealsbooking.online/api/restaurants";
    private  ArrayList<Restaurant> restaurantList = new ArrayList<>();
    final String TAG = "BookMealsDataSource";
    MapView map;
    FlipProgressDialog fpd;
    ProgressDialog progressDialog;
    private Handler mHandler;
    private boolean isLoaded = false;

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            mHandler.postDelayed(m_Runnable, 3000);
            if (isLoaded) {
              //  progressDialog.hide();
                mHandler.removeCallbacksAndMessages(null);
                fpd.dismiss();
            }
        }

    };

    public MapFragment() {
    }
    private ArrayList<Restaurant> fetchStoreItems() {
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
                        restaurantList.addAll(items);
                        isLoaded = true;
                        Drawable icon = getResources().getDrawable(R.drawable.fastfood);
                        Drawable image = getResources().getDrawable(R.drawable.logo);
                        IMapController mapController = map.getController();


                        for (Restaurant item : restaurantList) {
                            GeoPoint endPoint = new GeoPoint(item.getLatitude(), item.getLongitude());
                            Marker endMarker = new Marker(map);
                            endMarker.setPosition(endPoint);
                            endMarker.setIcon(icon);
                            mapController.setZoom (15);
                            mapController.setCenter (endPoint);
                            String name = item.getName();
                            String address = item.getAddress();
                            endMarker.setTitle(name);
                            endMarker.setIcon(icon);
                            endMarker.setSnippet(item.getCategory());
                            endMarker.setSubDescription(item.getAddress());
                            //endMarker.setSubDescription("Category: " + item.getCategory());
                            endMarker.setImage(image);
                            endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            map.getOverlays().add(endMarker);
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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        loading();

//
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,1000);

//
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();

        Drawable clusterIconD = getResources().getDrawable(R.drawable.logo);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        // Get the navigation_map added in the activity_main.xml file
        map = (MapView) root.findViewById (R.id.mapId);

        fetchStoreItems();
        if(isLoaded) {
            Log.e("outside", restaurantList.toString());
        }
//        // Image source
//        navigation_map.setTileSource(TileSourceFactory.MAPNIK );
//
//        // Create a reference point based on latitude and longitude
//        GeoPoint startpoint = new GeoPoint (36.8407490, 7.2519804);
//
//        IMapController mapController = navigation_map.getController();
//        // Zoom
//        mapController.setZoom (15);
//        // Center the navigation_map at the reference point
//        mapController.setCenter (startpoint);
//
//        // Create a marker on the navigation_map
//        Marker startMarker = new Marker(navigation_map);
//        startMarker.setPosition (startpoint);
//        startMarker.setTitle ( "Baguette" );
//        startMarker.setImage(clusterIconD);
//        //startMarker.setIcon(clusterIconD);
//        startMarker.setSnippet("Restaurant");
//
//        Marker endMarker = new Marker(navigation_map);
//        navigation_map.getOverlays().add(endMarker);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        navigation_map.getOverlays().add(startMarker);



        return root;
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

            fpd.setImageSize(250);
            fpd.setImageMargin(20);


            fpd.setCornerRadius(200);

            fpd.show(getFragmentManager(),"");                        // Show flip-progress-dialg
            //fpd.dismiss();

        }








}
