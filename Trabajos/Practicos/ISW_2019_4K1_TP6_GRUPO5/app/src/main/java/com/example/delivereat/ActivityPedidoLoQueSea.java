package com.example.delivereat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.delivereat.fragments.FragmentSeleccionUbicacionCliente;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ActivityPedidoLoQueSea extends FragmentActivity implements OnMapReadyCallback {

    private static final int FRAGMENT_UBICACION_CLIENTE = 0;
    public static final int PERMISO_UBICACION = 1;
    GoogleMap mMap;
    LocationManager locationManager;
    TextView tvUbicacion;
    private androidx.appcompat.widget.Toolbar toolbar;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SETTINGS Y PERMISOS
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.place_picker);

        toolbar = findViewById(R.id.toolbar);

        //setCurrentFragment(FRAGMENT_UBICACION_CLIENTE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        ((TextView) findViewById(R.id.tvToolbar)).setText("Dirección de entrega");

        tvUbicacion = findViewById(R.id.textViewPlacePicker);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }


    private void setCurrentFragment(int codigo) {
        switch (codigo) {
            case FRAGMENT_UBICACION_CLIENTE:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentSeleccionUbicacionCliente.newInstance())
                        .commit();
                break;
            default:
                break;
        }
        ;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latYLong;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityPedidoLoQueSea.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISO_UBICACION);
            latYLong = new LatLng(-31.41976, -64.18835); //Patio Olmos es la location por default
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = getLastKnownLocation();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            latYLong = new LatLng(latitude, longitude);
        }


        // Add a marker in Sydney, Australia, and move the camera.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latYLong));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latYLong, 18.0f));
        setLocation(latYLong);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                setLocation(point);
            }
        });

    }

    private void setLocation(LatLng point) {
        mMap.addMarker(new MarkerOptions().position(point));
        Geocoder geocoder = new Geocoder(ActivityPedidoLoQueSea.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
            tvUbicacion.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            tvUbicacion.setText("No se pudo encontrar la ubicación");
            e.printStackTrace();
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


}
