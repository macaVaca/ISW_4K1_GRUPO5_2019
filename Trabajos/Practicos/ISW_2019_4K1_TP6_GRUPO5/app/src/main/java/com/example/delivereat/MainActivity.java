package com.example.delivereat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.delivereat.util.DialogAlert;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonIniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewLocalidad = new Intent(MainActivity.this, ActivitySeleccionDeTipoPedido.class);
                startActivity(intentNewLocalidad);
            }
        });

        findViewById(R.id.buttonNuevoUsuario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAlert(MainActivity.this, "¡Funcionalidad en construcción!").show();
            }
        });
    }
}
