package com.example.delivereat.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.delivereat.R;

public class DialogAlert extends Dialog {

    public DialogAlert(Context context, String mensaje) {
        super(context);
        if (getWindow() != null)
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_mensaje);

        findViewById(R.id.buttonCerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((TextView) findViewById(R.id.tvMensaje)).setText(mensaje);
    }


}
