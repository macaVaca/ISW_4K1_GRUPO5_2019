package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;

class DialogConfirmar extends Dialog {

    private ActivityPedidoLoQueSea context;
    private boolean optionSelected = false;

    DialogConfirmar(@NonNull ActivityPedidoLoQueSea context, final OnSelectedOption oso, String mensaje, int imgResource) {
        super(context);
        if (getWindow() != null)
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_confirmacion);
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

        if (!mensaje.isEmpty()) ((TextView)findViewById(R.id.tvMensaje)).setText(mensaje);
        ((ImageView)findViewById(R.id.ivConfirmar)).setImageResource(imgResource);

        this.context = context;
    }

    @Override
    public void dismiss() {
        if (!optionSelected) context.setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_PEDIDO);
        super.dismiss();
    }
}
