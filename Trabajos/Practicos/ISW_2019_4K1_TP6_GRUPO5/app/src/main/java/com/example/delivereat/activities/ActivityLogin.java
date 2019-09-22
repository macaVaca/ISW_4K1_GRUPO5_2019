package com.example.delivereat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.delivereat.R;
import com.example.delivereat.util.DialogAlert;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonIniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewLocalidad = new Intent(ActivityLogin.this, ActivitySeleccionDeTipoPedido.class);
                startActivity(intentNewLocalidad);
            }
        });

        findViewById(R.id.buttonNuevoUsuario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlert(ActivityLogin.this, "¡Funcionalidad en construcción!").show();
            }
        });
    }
}
