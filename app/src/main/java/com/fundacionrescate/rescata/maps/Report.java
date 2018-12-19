package com.fundacionrescate.rescata.maps;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.fragment.Reporte;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Report extends Fragment implements OnMapReadyCallback,
        GoogleMap.CancelableCallback,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "LocationMap";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private Runnable mRunnable;
    private Address mAddressObj;
    GoogleMap mGoogleMap;
    private double mLatitude;
    private double mLongitude;
    GoogleApiClient mGoogleApiClient;
    private Handler mHandler;
    private boolean canceled = false;
    AsyncTask<Void, Void, Void> taskGps;
    private boolean waitingResult = false;
    private String mAddress;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    SupportMapFragment fm;

    final LatLng position = null;
    final float latGuayaquil = -2.203816f;
    final float lonGuayaquil = -79.897453f;

    TextView searchView;
    LinearLayout topBar;
    TextView topBarTitle;
    ImageView topBarImg, map_direccion_back;
    Button buttonNext;

    AutocompleteFilter typeFilter;


    public Report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_report, container, false);
//        topBar = v.findViewById(R.id.map_top_bar_container);
//        topBarTitle = v.findViewById(R.id.map_top_bar_title);
//        topBarImg = v.findViewById(R.id.map_top_bar_img);
//        buttonNext = v.findViewById(R.id.map_button_next);
//        map_direccion_back = v.findViewById(R.id.map_direccion_back);


        typeFilter = new AutocompleteFilter.Builder().setCountry("EC").build();
        ButterKnife.bind(this, v);

        searchView = v.findViewById(R.id.search_view_map);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evtSearchView();
            }
        });


        fm = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        fm.getMapAsync(this);
        return  v;
    }


//    @OnClick(R.id.register_new_button)
//    void nextFormReport() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        String lon =  mLongitude+","+mLatitude;
//        fragmentTransaction.replace(R.id.fragment_content,  Reporte.newInstance(mAddress,lon));
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;

            buildGoogleApiClient();
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;

        if(taskGps!= null){
            taskGps.cancel(true);
        }


    }

    /**
     * */

    private void evtSearchView()
    {
        try {
            searchView.setEnabled(false);
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCameraMove() {
        if (mRunnable == null) {
            // Schedule a task to reverse geocode position
            mRunnable = new Runnable() {
                public void run() {
//                    getAddressAsync(mGoogleMap.getCameraPosition().target);
                }
            };
        }

        // If we have not scheduled one before, we created it.
        // Else, we REMOVE the old one (this makes it so that we only
        // call it 500 ms after the map has NOT moved.
        if (mHandler == null) {
            mHandler = new android.os.Handler();
        } else {
            mHandler.removeCallbacks(mRunnable);
        }
        waitingResult = true;
        // Send task
        mHandler.postDelayed(mRunnable, 200);
        LatLng latLng = mGoogleMap.getCameraPosition().target;
        if (latLng != null) {
            mLatitude = latLng.latitude;
            mLongitude = latLng.longitude;
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleMap.clear();
        getAddressAsync(latLng);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraMoveListener(this);
        mGoogleMap.setOnMapClickListener(this);


        getPermissions();
        try {
            boolean success = mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
    }
    protected GoogleApiClient buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            Activity act = getActivity();
            mGoogleApiClient = new GoogleApiClient.Builder(act)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            locationChecker(mGoogleApiClient, (AppCompatActivity)getActivity());
        }
        //CreateLocationRequest();
        mGoogleApiClient.connect();
        return mGoogleApiClient;
    }
    public void locationChecker(GoogleApiClient mGoogleApiClient, final AppCompatActivity activity) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    activity, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
    private void getAddressAsync(final LatLng latLng) {

        if (canceled) {
            return;
        }

        taskGps = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Geocoder geoCoder = new Geocoder(getActivity());
                List<Address> matches;
                try {
                    matches = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                if (bestMatch != null) {
                    mAddressObj = bestMatch;
            /* Toast.makeText(this, bestMatch.getAddressLine(0), Toast.LENGTH_LONG)
                    .show();
                    */
                    String newAddress = "Posición sin dirección";
                    if (bestMatch.getAddressLine(0) != null && !bestMatch.getAddressLine(0).isEmpty()) {
                        newAddress = bestMatch.getAddressLine(0);
                        if (bestMatch.getAddressLine(1) != null && !bestMatch.getAddressLine(1).isEmpty()) {
                            newAddress += ", " + bestMatch.getAddressLine(1);
                        }
                    }
                    mAddress = newAddress;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                waitingResult = false;
                if (!canceled) {
                    System.out.println("Address: " + mAddress);
                    searchView.setText(mAddress);
//
//                    mGoogleMap.addMarker(new MarkerOptions().position(latLng)
//                            .title("Reportar aqui"));
//                    if (mHandler == null) {
//                        mHandler = new android.os.Handler();
//                    }
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run () {
                            // make operation on UI - on example
                            // on progress bar.
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            String lon =   latLng.latitude+","+ latLng.longitude;
                            fragmentTransaction.replace(R.id.fragment_content,  Reporte.newInstance(mAddress,lon));
                            fragmentTransaction.addToBackStack(null);

                            fragmentTransaction.commit();
//                        }
//                    }, 500);


                    taskGps.cancel(true);
                    //EventBus.getDefault().post(new AddressEvent(mAddress));
                }
            }
        }.execute();
    }

    public void getPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            initializeLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                new MaterialDialog.Builder(getContext())
                        .title("Ubicación")
                        .content("¿Aceptas dar permisos para poder centrar el mapa en tu ubicación?")
                        .positiveText(R.string.map_permission_yes_)
                        .negativeText(R.string.map_permission_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //TODO: Pasar a compat
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        LOCATION_PERMISSION_REQUEST_CODE);

                            }
                        })
                        .show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE); // MAP CHANGES
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //isPermissionGranted = true;
                    //getLastKnownLocationIfReady();
                    initializeLocation();
                }
            }
        }
    }
    private void initializeLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Add user location button
        //mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        LatLng locationPacifico = new LatLng(latGuayaquil, lonGuayaquil);
                        LatLng position = (currentLocation != null) ? new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()) : locationPacifico;
                        // Default zoom
                        int zoom = 17;
                        // If we go inside the if, the user does not have a saved location
                        // AND there is no last known location
                        // In that case, use a constant position centered in Santiago
                        mGoogleApiClient.connect();
                        /*if (DatamanagerCustomerPetition.getInstance().getCustomerPetition() != null) {
                            DatamanagerCustomerPetition.getInstance().getCustomerPetition().setGeoImageMapLatitude(position.latitude + "");
                            DatamanagerCustomerPetition.getInstance().getCustomerPetition().setGeoImageMapLongitude(position.latitude + "");
                        }*/
                        moveCamera(position, zoom);

                    } else {
                        int zoom = 17;
                        LatLng locationPacifico = new LatLng(latGuayaquil, lonGuayaquil);
                        moveCamera(position, zoom);

                        Log.d(TAG, "onComplete: current location is null");

                    }
                }
            });

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }
    private void moveCamera(LatLng position, int zoom) {
        /*
        mGoogleMap.clear();
        MarkerOptions mp = new MarkerOptions();
        mp.position(position);
        mGoogleMap.addMarker(mp);*/
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(position.latitude, position.longitude), 13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(position.latitude, position.longitude))      // Sets the center of the map to location user
                .zoom(zoom)                   // Sets the zoom
                // .bearing(90)                // Sets the orientation of the camera to east
                // .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        searchView.setEnabled(true);

        onAutoCompleResult(requestCode, resultCode, data);
    }
    public void onAutoCompleResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                LatLng position = PlaceAutocomplete.getPlace(getContext(), data).getLatLng();
                Log.i(TAG, "Place: " + place.getName() + "position" + position.latitude + position.longitude + "direccion" + place.getAddress());
                //searchView.setText(place.getAddress());
                moveCamera(position, 17);
                getAddressAsync(place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}
