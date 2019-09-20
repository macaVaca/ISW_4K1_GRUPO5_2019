package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.os.Bundle;
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

public class FragmentMontoPago extends Fragment {

    TextView  montoPago ;

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
        montoPago = vv.findViewById(R.id.textMontoPago);
        vv.findViewById(R.id.buttonListoMontoPago).setOnClickListener(listenerConfirmarMontoPago);
        return vv;
    }

    private View.OnClickListener listenerConfirmarMontoPago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            rollback();
            /* if (montoPago.getText().toString() != " ") {
                Double montoFinal = Double.parseDouble(montoPago.getText().toString());
                setMontoPago(montoFinal);
                rollback();
            } else {
                new DialogAlert(requireContext(),"¡debe ingresar un monto a abonar!").show();
            } */
        }
    };

    public void rollback() {
                            getFragmentManager().beginTransaction()
                                                        .replace(R.id.fragment_container, FragmentSeleccionFormaPago.newInstance())
                                                        .addToBackStack(null)
                                                        .commit();
    }




    public void setMontoPago (Double monto) { ((FragmentSeleccionFormaPago) requireParentFragment()).setValorMontoPago(monto); }
}
