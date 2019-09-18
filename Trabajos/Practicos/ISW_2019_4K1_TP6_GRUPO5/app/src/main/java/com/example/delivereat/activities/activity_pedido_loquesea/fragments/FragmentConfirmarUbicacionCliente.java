package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    private String piso;
    private String depto;
    private EditText editTextCalle;
    private EditText editTextNumero;
    private EditText editTextDepto;
    private EditText editTextPiso;
    private TextView tvRegresarAMapa;

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
        editTextPiso = view.findViewById(R.id.editTextPiso);
        editTextDepto = view.findViewById(R.id.editTextDepto);

        view.findViewById(R.id.buttonVolverAMapa).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityPedidoLoQueSea)requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_UBICACION_CLIENTE);
                }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        address = ((ActivityPedidoLoQueSea)requireActivity()).getDireccionClienteMapa();
        actualizarCamposDeTexto();
    }

    private void actualizarCamposDeTexto() {
        if(address != null){
            provincia = address.getAdminArea();
            localidad = address.getLocality();
            calle = address.getThoroughfare();
            numero = address.getSubThoroughfare();
        }
        else{
            provincia = null;
            localidad = null;
            calle = null;
            numero = null;
        }

        piso = null;
        depto = null;

        if (provincia != null) {

        }
        else{

        }

        if (localidad != null){

        }
        else{

        }

        editTextCalle.setText((calle == null)? "" : calle);
        editTextNumero.setText((numero == null)? "" : numero);
        editTextPiso.setText("");
        editTextDepto.setText("");
    }
}
