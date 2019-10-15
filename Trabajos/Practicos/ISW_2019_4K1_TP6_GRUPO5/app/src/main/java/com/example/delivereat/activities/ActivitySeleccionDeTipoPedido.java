package com.example.delivereat.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.util.DialogAlert;

public class ActivitySeleccionDeTipoPedido extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_pedido);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.card_view_loquesea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivitySeleccionDeTipoPedido.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            ActivityPedidoLoQueSea.PERMISO_UBICACION);
                }
                else{
                    if (verificarConexionAInternet()) iniciarPedidoDeLoQueSea();
                    else new DialogAlert(ActivitySeleccionDeTipoPedido.this, "¡Para realizar un pedido debes estar conectado a internet!").show();
                }
            }
        });

        findViewById(R.id.card_view_pedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new DialogAlert(ActivitySeleccionDeTipoPedido.this, "¡Funcionalidad para grupos pares!").show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Items de la toolbar
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.logout:
                finish();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_pedidos, menu);
        return true;
    }

    /**
     * pide los permisos de uso del celular: permiso de utilizacion del GPS (RECOMENDADO NO USAR EL GPS)
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ActivityPedidoLoQueSea.PERMISO_UBICACION: {
                if (verificarConexionAInternet()) iniciarPedidoDeLoQueSea();
                else new DialogAlert(ActivitySeleccionDeTipoPedido.this, "¡Para realizar un pedido debes estar conectado a internet!").show();
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }

    /**
     * comienza el inicio de actividad de pedido lo que sea
     */
    private void iniciarPedidoDeLoQueSea(){
        Intent intent = new Intent(ActivitySeleccionDeTipoPedido.this, ActivityPedidoLoQueSea.class);
        startActivity(intent);
    }

    private boolean verificarConexionAInternet(){
        ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }



}
