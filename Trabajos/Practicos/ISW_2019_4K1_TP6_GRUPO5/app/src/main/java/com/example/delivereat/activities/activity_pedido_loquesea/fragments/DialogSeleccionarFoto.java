package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.delivereat.R;

class DialogSeleccionarFoto extends Dialog {
    DialogSeleccionarFoto(@NonNull Context context, final OnSelectedOption oso) {
        super(context);
        if (getWindow() != null)
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_seleccion_imagen);
        findViewById(R.id.ll_seleccionar_foto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oso.onYes();
                dismiss();
            }
        });
        findViewById(R.id.ll_tomar_foto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oso.onNo();
                dismiss();
            }
        });
    }
}
