package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.entities.DetallePedidoLoQueSea;
import com.example.delivereat.util.DialogAlert;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FragmentPedido extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapterPedido recyclerAdapterPedido;
    private TextView tvTotal;
    private ArrayList<DetallePedidoLoQueSea> pedido;
    private View emptyLayout;

    public static FragmentPedido newInstance() {
        FragmentPedido fragment = new FragmentPedido();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles_pedido, container, false);
        view.findViewById(R.id.buttonAgregarDetalle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPedidoLoQueSea) requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_NUEVO_DETALLE_PRODUCTO);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerDetalles);

        pedido = ((ActivityPedidoLoQueSea) requireActivity()).getPedido();
        recyclerAdapterPedido = new RecyclerAdapterPedido(this, pedido);
        recyclerView.setAdapter(recyclerAdapterPedido);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        tvTotal = view.findViewById(R.id.tvTotal);
        emptyLayout = view.findViewById(R.id.emptyLayout);
        updateList();
        view.findViewById(R.id.buttonConfirmarPedido).setOnClickListener(listenerConfirmarPedido);
        return view;
    }

    private DecimalFormat df = new DecimalFormat("0.00");
    private void calcularTotal(){
        float total = 0;
        for (DetallePedidoLoQueSea det : pedido){
            total += det.getPrecioFinal();
        }

        String textTotal = "$" + " " + df.format(total);
        tvTotal.setText(textTotal);
    }

    void updateList(){
        emptyLayout.setVisibility((recyclerAdapterPedido.getItemCount() == 0) ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility((recyclerAdapterPedido.getItemCount() == 0) ? View.GONE : View.VISIBLE);
        calcularTotal();
    }

    private View.OnClickListener listenerConfirmarPedido = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(recyclerAdapterPedido.getItemCount() > 0){
                ((ActivityPedidoLoQueSea)requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_UBICACION_CLIENTE);
            }
            else new DialogAlert(requireContext(), "¡Pedido vacío!" ).show();
        }
    };
}
