package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.util.DialogAlert;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/***
 * Este fragment tiene la funcion de dar al cliente la opción de seleccionar
 * una ubicacion en el mapa interactivo. Esa ubicacion luego dera validada en el fragment
 * para confirmar la ubicacion del cliente.
 *
 * Mapa interactivo: Mapa brindado por la API maps de google,
 * que mediante un objeto del tipo GeoCoder nos trae una Location a partir de las coordenadas seleccionadas en el mapa.
 * Lo ideal era usar la api PLACES de Google pero es paga, y nosotros somos pobres.
 *
 * Validaciones a realizar:
 *  - Que el usuario cuente con internet
 *  - Que el usuario haya dado los permisos de GPS y esté el GPS activado.
 *  - Si no está el GPS activado no se debe mostrar este fragment y se pasa a la selección manual de ubicacion automaticamente
 *
 */
public class FragmentSeleccionDeUbicacionNegocio extends Fragment implements OnMapReadyCallback {

    public static final int PERMISO_UBICACION = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private TextView tvUbicacion;
    private boolean updateRequested = false;
    private final static float defLat = -31.44225f;
    private final static float defLong = -64.1931658f;
    private Address currentAdress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_picker, container, false);
        locationManager = ((ActivityPedidoLoQueSea) requireActivity()).getLocationManager();
        tvUbicacion = view.findViewById(R.id.textViewPlacePicker);
        view.findViewById(R.id.buttonPlacePicker).setOnClickListener(listenerSeleccionarUbicacion);
        view.findViewById(R.id.buttonIntroducirManual).setOnClickListener(listenerSeleccionManual);
        return view;
    }


    /***
     * El fragment ya está attached a la activity, podemos buscar el mapa.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        else{
            Log.d("Error", "No se cargo");
        }
    }

    public static FragmentSeleccionDeUbicacionNegocio newInstance() {
        FragmentSeleccionDeUbicacionNegocio fragment = new FragmentSeleccionDeUbicacionNegocio();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    /***
     * Este metodo se ejecuta luego de hacer una request de mapa. Es un callback
     * Cuando el mapa esta listo, lo guardamos en la variable privada mMap.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Si tengo los permisos
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = getLastKnownLocation();
            LatLng latYLong = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latYLong));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latYLong, 18.0f));
            setLocation(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLong = new LatLng(location.getLatitude(), location.getLongitude());
                    setLocation(latLong);
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
            });
        }
        else{
            //Marcamos el patio olmos por defecto si no hay permiso de GPS
            LatLng latYLong = new LatLng(defLat, defLong);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latYLong));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latYLong, 18.0f));
            Location lastKnownLocation = getLastKnownLocation();
            setLocation(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                setLocation(point);
            }
        });

    }

    /***
     * @param latLng punto de coordenadas donde queremos setear la ubicacion actual
     */
    private void setLocation(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            currentAdress = addresses.get(0);
            tvUbicacion.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            tvUbicacion.setText("No se pudo encontrar la ubicación");
            currentAdress = null;
            e.printStackTrace();
        }
    }

    private LocationManager getLocationManager(){
        return ((ActivityPedidoLoQueSea) requireActivity()).getLocationManager();
    }


    private Location getLastKnownLocation() {
        locationManager = getLocationManager();
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        Location l;
        for (String provider : providers) {
            if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Si no hay permisos marco que estoy en la ubicación por defecto. La utn.
                l = new Location("");
                l.setLatitude(defLat);
                l.setLongitude(defLong);
                return l;
            }
            l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if(bestLocation == null){
            l = new Location("");
            l.setLatitude(defLat);
            l.setLongitude(defLong);
            return l;
        }
        return bestLocation;
    }

    private View.OnClickListener listenerSeleccionarUbicacion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String[] provincias = getResources().getStringArray(R.array.array_provincias);
            boolean localidadValida = false;

            if (currentAdress != null){
                if(currentAdress.getAdminArea() != null){
                    for (String provincia : provincias) {
                        if(currentAdress.getAdminArea().equals(provincia)){
                            if(validarLocalidad(provincia, currentAdress.getLocality())) localidadValida = true;
                            break;
                        }
                    }
                }
            }

            if(localidadValida) ((ActivityPedidoLoQueSea) requireActivity()).setDireccionNegocioMapa(currentAdress);
            else{
                new DialogAlert(requireContext(), "¡Ubicación no válida!").show();
            }
        }
    };

    private View.OnClickListener listenerSeleccionManual = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((ActivityPedidoLoQueSea) requireActivity()).setDireccionNegocioMapa(null);
        }
    };


    private boolean validarLocalidad(String provincia, String localidad){
        if(currentAdress.getLocality() == null) return false;
        for (String loc : getLocalidadesDeProvincia(provincia)){
            if(localidad.equals(loc)) return true;
        }
        return false;
    }


    private String[] getLocalidadesDeProvincia(String provincia){
        switch (provincia){
            case "Córdoba":
                return getResources().getStringArray(R.array.array_localidades_cb);
            case "Buenos Aires":
                return getResources().getStringArray(R.array.array_localidades_ba);
            case "Santa Fe":
                return getResources().getStringArray(R.array.array_localidades_sf);
            default:
                return getResources().getStringArray(R.array.array_localidades_ca);
        }
    }


}
