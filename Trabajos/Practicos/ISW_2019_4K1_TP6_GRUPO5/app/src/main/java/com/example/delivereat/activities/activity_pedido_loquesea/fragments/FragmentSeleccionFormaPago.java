package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;

public class FragmentSeleccionFormaPago extends Fragment {

    RadioGroup rdFormaPago;
    View viewDatosTarjeta;

    RadioGroup.OnCheckedChangeListener checkedFromaPago = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if( R.id.radioButtonTarjetaVISA == checkedId) {
                viewDatosTarjeta.setVisibility(View.VISIBLE);
            } else if (R.id.radioButtonEfectivo == checkedId) {
                viewDatosTarjeta.setVisibility(View.GONE);
            }

        }
    };

   public static FragmentSeleccionFormaPago newInstance() {

       Bundle args = new Bundle();
       FragmentSeleccionFormaPago fragment = new FragmentSeleccionFormaPago();
       fragment.setArguments(args);
       return fragment;
   }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View vv = inflater.inflate(R.layout.fragment_seleccion_forma_de_pago, container, false);
       viewDatosTarjeta = vv.findViewById(R.id.llayoutDatosTarjeta);
        viewDatosTarjeta.setVisibility(View.GONE);
        rdFormaPago = vv.findViewById(R.id.radioGroupFormaPago);
        rdFormaPago.setOnCheckedChangeListener(checkedFromaPago);
       return vv;

    }



}
