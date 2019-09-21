package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.os.Bundle;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;

public class FragmentSeleccionFormaPago extends Fragment {

    RadioGroup rdFormaPago;
    View viewDatosTarjeta;
    Button  buttonMontoPago;
    Double valorMontoPago;

    RadioGroup.OnCheckedChangeListener checkedFromaPago = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if( R.id.radioButtonTarjetaVISA == checkedId) {
                viewDatosTarjeta.setVisibility(View.VISIBLE);
                buttonMontoPago.setAlpha(0);
            } else if (R.id.radioButtonEfectivo == checkedId) {
                viewDatosTarjeta.setVisibility(View.GONE);
                buttonMontoPago.setAlpha(1);
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
        vv.findViewById(R.id.buttonMontoPago).setOnClickListener(listenerIngresarMonto);
        buttonMontoPago = vv.findViewById(R.id.buttonMontoPago);
        buttonMontoPago.setAlpha(0);
        rdFormaPago.setOnCheckedChangeListener(checkedFromaPago);
       return vv;

    }

    private View.OnClickListener listenerIngresarMonto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((ActivityPedidoLoQueSea) requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_MONTO_PAGO);
        }
    };

    public void setValorMontoPago(Double montoPago) {
        this.valorMontoPago = montoPago;
        ((ActivityPedidoLoQueSea)requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_MONTO_PAGO);
    }


}
