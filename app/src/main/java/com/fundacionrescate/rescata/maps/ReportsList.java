package com.fundacionrescate.rescata.maps;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.activity.Container;
import com.fundacionrescate.rescata.fragment.Reporte;
import com.fundacionrescate.rescata.fragment.ViewMascotas;
import com.google.android.gms.common.ConnectionResult;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsList extends Fragment implements OnMapReadyCallback,
        GoogleMap.CancelableCallback,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    AutocompleteFilter typeFilter;

    private static final String TAG = "LocationMap";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    final LatLng position = null;
    final float latGuayaquil = -2.203816f;
    final float lonGuayaquil = -79.897453f;
    GoogleMap mGoogleMap;

    private Runnable mRunnable;
    AsyncTask<Void, Void, Void> taskGps;

    GoogleApiClient mGoogleApiClient;
    private Handler mHandler;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    SupportMapFragment fm;

    public ReportsList() {
        // Required empty public constructor
    }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report_list, container, false);
        ButterKnife.bind(this, v);
        fm = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        fm.getMapAsync(this);
        return  v;
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

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraMoveListener(this);
        mGoogleMap.setOnMapClickListener(this);
//        new LatLng(-33.852, 151.211);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-2.193847, -79.887411))
                .title("Mascota 1"));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-2.189550, -79.886836))
                .title("Mascota 2"));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-2.185554, -79.896872))
                .title("Mascota 3"));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-2.185541, -79.895466))
                .title("Mascota 4"));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-2.176148, -79.886396))
                .title("Mascota 5"));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-2.175526, -79.887340))
                .title("Mascota 6"));
        // -2.193847, -79.887411
//        -2.189550, -79.886836

//        -2.185554, -79.896872
//        -2.185541, -79.895466

        //-2.176148, -79.886396
//        -2.175526, -79.887340
        getPermissions();
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


    //
    //register_report

    @OnClick(R.id.register_report)
    void nextMapReport() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.fragment_content,  new Report());
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        Intent modulCNB = new Intent(getContext(), Container.class);
        Bundle b = new Bundle();
        b.putInt("key", Container.FRAGMENT_REPORTE); //Your id
        modulCNB.putExtras(b); //Put your id to your next Intent
        getActivity().startActivity(modulCNB);
        getActivity().overridePendingTransition(0, 0);

    }

    @OnClick(R.id.report_adoption)
    void nextAdopcion() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.fragment_content,  new ViewMascotas());
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        Intent modulCNB = new Intent(getContext(), Container.class);
        Bundle b = new Bundle();
        b.putInt("key", Container.FRAGMENT_ADOPCION); //Your id
        modulCNB.putExtras(b); //Put your id to your next Intent
        getActivity().startActivity(modulCNB);
        getActivity().overridePendingTransition(0, 0);
    }
}
