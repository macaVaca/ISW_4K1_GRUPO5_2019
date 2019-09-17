package com.example.delivereat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.delivereat.util.DialogAlert;

public class ActivitySeleccionDeTipoPedido extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_pedido);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_location);
            //((TextView) toolbar.findViewById(R.id.tvToolbar)).setText("Nuevo Pedido");
        }

        findViewById(R.id.card_view_loquesea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivitySeleccionDeTipoPedido.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            ActivityPedidoLoQueSea.PERMISO_UBICACION);
                }
                else{
                    iniciarPedidoDeLoQueSea();
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ActivityPedidoLoQueSea.PERMISO_UBICACION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    iniciarPedidoDeLoQueSea();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }

    private void iniciarPedidoDeLoQueSea(){
        Intent intent = new Intent(ActivitySeleccionDeTipoPedido.this, ActivityPedidoLoQueSea.class);
        startActivity(intent);
    }




}
