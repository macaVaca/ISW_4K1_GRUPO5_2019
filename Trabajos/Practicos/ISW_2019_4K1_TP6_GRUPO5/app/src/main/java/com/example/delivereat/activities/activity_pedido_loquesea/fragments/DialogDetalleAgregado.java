package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;

class DialogDetalleAgregado extends Dialog {

    private ActivityPedidoLoQueSea context;
    private boolean optionSelected = false;

    DialogDetalleAgregado(@NonNull ActivityPedidoLoQueSea context, final OnSelectedOption oso) {
        super(context);
        if (getWindow() != null)
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_detalle_agregado);
        findViewById(R.id.buttonYES).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionSelected = true;
                oso.onYes();
                dismiss();
            }
        });
        findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionSelected = true;
                oso.onNo();
                dismiss();
            }
        });
        this.context = context;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (!optionSelected) context.setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_PEDIDO);
    }
}
