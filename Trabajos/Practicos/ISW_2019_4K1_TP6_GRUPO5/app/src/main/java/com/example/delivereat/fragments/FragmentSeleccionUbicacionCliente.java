package com.example.delivereat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.Places;

public class FragmentSeleccionUbicacionCliente extends Fragment {

    private Button buttonPlacePicker;
    private TextView tvPlacePicker;
    private int PLACE_PICKER_REQUEST = 1;
    private GoogleMap mMap;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.place_picker, container, false);

        buttonPlacePicker = view.findViewById(R.id.buttonPlacePicker);
        tvPlacePicker = view.findViewById(R.id.textViewPlacePicker);

        buttonPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Places.initialize(requireContext(), "AIzaSyCaULAx4n3GEh944gSgsPK2918FihVzY7c");
            }
        });




        return view;
    }

    public static FragmentSeleccionUbicacionCliente newInstance() {
        FragmentSeleccionUbicacionCliente fragment = new FragmentSeleccionUbicacionCliente();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.PLACE_PICKER_REQUEST){
            /*Place place = PlacePicker.getPlace(requireContext(), data);
            String latitude = String.valueOf(place.getLatLng().latitude);
            String longiutude = String.valueOf(place.getLatLng().longitude);
            String res = latitude + " " + longiutude;
            tvPlacePicker.setText(place.getAddress());*/
        }
    }
}
