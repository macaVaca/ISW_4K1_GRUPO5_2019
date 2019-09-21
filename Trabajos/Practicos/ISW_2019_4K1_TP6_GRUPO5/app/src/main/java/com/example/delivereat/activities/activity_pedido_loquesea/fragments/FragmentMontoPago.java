package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;

public class FragmentMontoPago extends Fragment{

    public static FragmentMontoPago newInstance() {
        Bundle args = new Bundle();
        FragmentMontoPago fragment = new FragmentMontoPago();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_monto_pago, container, false);
        vv.findViewById(R.id.buttonListoMontoPago).setOnClickListener(listenerConfirmarMontoPago);
        return vv;
    }

    private View.OnClickListener listenerConfirmarMontoPago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((ActivityPedidoLoQueSea) requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_SELECCION_FORMA_PAGO);
        }
    };

    public void setMontoPago (Double monto) { ((FragmentSeleccionFormaPago) requireParentFragment()).setValorMontoPago(monto); }
}
