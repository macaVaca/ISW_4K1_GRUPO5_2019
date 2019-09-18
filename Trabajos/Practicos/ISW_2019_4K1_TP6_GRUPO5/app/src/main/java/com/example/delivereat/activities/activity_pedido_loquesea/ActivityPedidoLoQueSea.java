package com.example.delivereat.activities.activity_pedido_loquesea;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentConfirmarUbicacionCliente;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentSeleccionDeUbicacionCliente;


public class ActivityPedidoLoQueSea extends AppCompatActivity {

    private static final int FRAGMENT_UBICACION_CLIENTE = 0;
    private static final int FRAGMENT_CONFIRMAR_UBICACION_CLIENTE = 1;
    public static final int PERMISO_UBICACION = 1;
    LocationManager locationManager;
    TextView tvTitulo;
    //Direccion real de entrega
    private Address direccionCliente;
    //Direccion seleccionada en el mapa
    private Address direccionClienteMapa;
    //Direccion del negocio
    private Address direccionNegocio;
    //Direccion del negocio en el mapa
    private Address direccionNegocioMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SETTINGS Y PERMISOS
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_nuevo_pedido);
        tvTitulo = findViewById(R.id.tvToolbar);
        setCurrentFragment(FRAGMENT_UBICACION_CLIENTE);
    }


    private void setCurrentFragment(int codigo) {
        switch (codigo) {
            case FRAGMENT_UBICACION_CLIENTE:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentSeleccionDeUbicacionCliente.newInstance())
                        .commit();
                tvTitulo.setText("Seleccioná tu ubicación");
                break;
            case FRAGMENT_CONFIRMAR_UBICACION_CLIENTE:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentConfirmarUbicacionCliente.newInstance())
                        .commit();
                tvTitulo.setText("Confirmá tu ubicación");
                break;
            default:
                break;
        }
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    /***
     * Define la direccion parcial de entrega que se confirmara en una instancia del fragment de confirmacion de ubicacion
     * @param direccionClienteMapa
     */
    public void setDireccionClienteMapa(Address direccionClienteMapa) {
        this.direccionClienteMapa = direccionClienteMapa;
        setCurrentFragment(FRAGMENT_CONFIRMAR_UBICACION_CLIENTE);
    }

    public void setDireccionCliente(Address direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public Address getDireccionClienteMapa() {
        return direccionClienteMapa;
    }
}
