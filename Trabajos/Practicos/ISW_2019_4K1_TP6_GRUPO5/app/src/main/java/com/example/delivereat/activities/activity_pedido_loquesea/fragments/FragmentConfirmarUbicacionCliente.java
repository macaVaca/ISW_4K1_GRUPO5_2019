package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;

public class FragmentConfirmarUbicacionCliente extends Fragment {

    private Address address;
    private String provincia;
    private String localidad;
    private String calle;
    private String numero;
    private EditText editTextCalle;
    private EditText editTextNumero;

    public static FragmentConfirmarUbicacionCliente newInstance() {
        FragmentConfirmarUbicacionCliente fragment = new FragmentConfirmarUbicacionCliente();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingresar_ubicacion, container, false);
        editTextCalle = view.findViewById(R.id.editTextCalle);
        editTextNumero = view.findViewById(R.id.editTextNro);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        address = ((ActivityPedidoLoQueSea)requireActivity()).getDireccionClienteMapa();
        provincia = address.getAdminArea();
        localidad = address.getLocality();
        calle = address.getThoroughfare();
        numero = address.getSubThoroughfare();
        editTextCalle.setText(calle);
        editTextNumero.setText(numero);
    }
}
