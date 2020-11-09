package com.example.myapp2.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp2.R;
import com.example.myapp2.util.MainActivityFragmentManager;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    //UI Components

    // Vars

    //Location
    private FusedLocationProviderClient mFusedLocationClient;
    private CollectionReference audioFirestoreRef;
    private CollectionReference geoFirestoreRef;
    private double latitude;
    private double longitude;
    // Initializing other items
    // from layout file
    private TextView latitudeTextView, longitTextView;
    private Button button;
    int PERMISSION_ID = 44;
    private LocationCallback
            mLocationCallback
            = new LocationCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(
                LocationResult locationResult) {
            Location mLastLocation
                    = locationResult
                    .getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "hello");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);

        mFusedLocationClient
                = LocationServices
                .getFusedLocationProviderClient(this);

        audioFirestoreRef = FirebaseFirestore.getInstance().collection("Audio");
        geoFirestoreRef =
                FirebaseFirestore.getInstance().collection("GeoFire Location");
//        setGeoLocation();

        // method to get location
        button = findViewById(R.id.locationButton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "In button click");
                getLastLocation();
            }
        });

        loadFragment(HomeFragment.newInstance(), true);
        Log.d(TAG, "load fragment called:");
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return false;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my_menu, menu);

        //onOptionsMenuCreated(menu);

        // setup the SearchView
        final MenuItem searchItem = menu.findItem(R.id.search_icon);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search Here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "text submit");
                displaySearchResults(query, searchView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                //arrayAdapter.getFilter().filter(s);
                Log.d(TAG, "text changed and arrayadapter filtered");
                return true;
            }
        });

        return true;
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.search_icon) {
            final SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Search Here!");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "text submit");
                    displaySearchResults(query, searchView);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //arrayAdapter.getFilter().filter(s);
                    Log.d(TAG, "text changed and arrayadapter filtered");
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }*/

    private void loadFragment(Fragment fragment, boolean lateralMovement){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(lateralMovement){
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        String tag ="";
        if(fragment instanceof HomeFragment){

            tag = getString(R.string.fragment_home);
        } else if (fragment instanceof CategoryFragment) {
            tag = getString(R.string.fragment_category);
            //transaction.addToBackStack(tag);
        }

        transaction.replace(R.id.main_container,fragment,tag);

        transaction.commit();
        Log.d(TAG, "loaded fragment");

        //MainActivityFragmentManager.getInstance().addFragment(fragment);

        //showFragment(fragment);
    }

    /*@Override
    public void onTextChange(MenuItem menuItem) {
        loadFragment(CategoryFragment.newInstance(menuItem));
    }*/

    private void showFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.show(fragment);
        transaction.commit();

        for (Fragment f : MainActivityFragmentManager.getInstance().getFragments()) {
            if (f != null) {
                if (!f.getTag().equals(fragment.getTag())) {
                    FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                    t.hide(f);
                    t.commit();
                }
            }
        }

        Log.d(TAG, "showFragment: num fragments: " + MainActivityFragmentManager.getInstance().getFragments().size());
    }

    private void displaySearchResults(String query, @NonNull final View searchView) {
        // hide the keyboard
        searchView.clearFocus();

        // open SearchVideoGridFragment and display the results
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CategoryFragment.QUERY, query);
        categoryFragment.setArguments(bundle);
        loadFragment(categoryFragment, true);

        Log.d(TAG, "MainActivity: Display search results called");
    }

    private void setGeoLocation() {
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
        geoFirestore.setLocation("NSi3IIiupScbO6Qj8pku", new GeoPoint(37.7852, -122.4056));

    }

    @Override
    public void onBackPressed() {
        String tag ="";

        if(!(getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof HomeFragment))
        {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction t= getSupportFragmentManager().beginTransaction();
            t.replace(R.id.main_container,homeFragment).commit();
        }else {
            super.onBackPressed();
        }
    }

    private void searchByGeoLocation(double currLat, double currLong){
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
        GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(currLat, currLong), 50);
        final ArrayList<String> arrLocs = new ArrayList<>();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String s, GeoPoint geoPoint) {
                System.out.println("Location key found - "+ s + " at location - " + geoPoint);
                arrLocs.add(s);
            }

            @Override
            public void onKeyExited(String s) {
                arrLocs.remove(s);
            }

            @Override
            public void onKeyMoved(String s, GeoPoint geoPoint) {

            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("Locations :"+ arrLocs);
                getAudioNearby(arrLocs);
            }

            @Override
            public void onGeoQueryError(Exception e) {

            }
        });
    }

    private void getAudioNearby(ArrayList<String> arrLocs){
        for (String arrLoc: arrLocs){
            Log.d(TAG, arrLoc);
            audioFirestoreRef.whereEqualTo("media_id", arrLoc)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                // getting last location from FusedLocationClient object
                mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener(
                                new OnCompleteListener<Location>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<Location> task) {
                                        Location location = task.getResult();
                                        if (location == null) {
                                            requestNewLocationData();
                                        } else {
                                            latitude = location.getLatitude();
                                            longitude = location.getLongitude();
                                            latitudeTextView.setText(latitude + "");
                                            longitTextView.setText(longitude + "");
                                            Log.d(TAG, "------: Latitude");
                                            Log.d(TAG, "------: " + location.getLatitude());
                                            searchByGeoLocation(latitude, longitude);
                                        }
                                    }
                                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...",
                                Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest
                = new LocationRequest();
        mLocationRequest.setPriority(
                LocationRequest
                        .PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient
                = LocationServices
                .getFusedLocationProviderClient(this);

        mFusedLocationClient
                .requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback,
                        Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        // If we want background location
        // on Android 10.0 and higher,
        // use:
        /* ActivityCompat
                .checkSelfPermission(
                    this,
                    Manifest.permission
                        .ACCESS_BACKGROUND_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        */
    }

    // method to requestfor permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission
                                .ACCESS_COARSE_LOCATION,
                        Manifest.permission
                                .ACCESS_FINE_LOCATION},
                PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager
                locationManager
                = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        return locationManager
                .isProviderEnabled(
                        LocationManager.GPS_PROVIDER)
                || locationManager
                .isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(
                        requestCode,
                        permissions,
                        grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager
                    .PERMISSION_GRANTED) {

                getLastLocation();
            }
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        }
//    }

}


















