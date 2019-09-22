package com.example.delivereat.activities.activity_pedido_loquesea;

import android.location.Address;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentConfirmarUbicacionCliente;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentPedido;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentMontoPago;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentNuevoDetalleDeProducto;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentSeleccionDeUbicacionCliente;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentSeleccionFormaPago;
import com.example.delivereat.entities.DetallePedidoLoQueSea;
import com.example.delivereat.entities.Ubicacion;

import java.util.ArrayList;


public class ActivityPedidoLoQueSea extends AppCompatActivity {

    public static final int FRAGMENT_UBICACION_CLIENTE = 0;
    public static final int FRAGMENT_CONFIRMAR_UBICACION_CLIENTE = 1;
    public static final int FRAGMENT_PEDIDO = 2;
    public static final int FRAGMENT_NUEVO_DETALLE_PRODUCTO = 3;
    public static final int FRAGMENT_SELECCION_FORMA_PAGO = 4;
    public static final int FRAGMENT_MONTO_PAGO = 5;

    private int currentFragment;

    public static final int PERMISO_UBICACION = 1;

    LocationManager locationManager;
    //textView publica para ser accedida por los fragmentos siguientes de otro fragmento
    private TextView tvTitulo;
    //Direccion real de entrega, ubicacion es una entidad cerada por nosotros para manipular los datos necesarios para registrar la direccion real.
    private Ubicacion direccionCliente;
    //Direccion seleccionada en el mapa, address es un objeto manejado por el mapa de google
    private Address direccionClienteMapa;
    //Direccion del negocio
    private Ubicacion direccionNegocio;
    //Direccion del negocio en el mapa
    private Address direccionNegocioMapa;
    //ArrayList de Detalles de pedido loquesea
    private ArrayList<DetallePedidoLoQueSea> pedido = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SETTINGS Y PERMISOS
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_nuevo_pedido);
        tvTitulo = findViewById(R.id.tvToolbar);
        setCurrentFragment(FRAGMENT_PEDIDO);
    }


    public void setCurrentFragment(int codigo) {
        currentFragment = codigo;
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
            case FRAGMENT_PEDIDO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentPedido.newInstance())
                        .commit();
                tvTitulo.setText("Tu pedido");
                break;
            case FRAGMENT_NUEVO_DETALLE_PRODUCTO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentNuevoDetalleDeProducto.newInstance())
                        .commit();
                tvTitulo.setText("Nuevo producto");
                break;
            case FRAGMENT_SELECCION_FORMA_PAGO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentSeleccionFormaPago.newInstance())
                        .commit();
                tvTitulo.setText("Seleccionar forma de pago");
            case FRAGMENT_MONTO_PAGO:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FragmentMontoPago.newInstance())
                        .commit();
                tvTitulo.setText("Ingrese el monto a pagar");
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

    public void setDireccionCliente(Ubicacion direccionCliente) {
        this.direccionCliente = direccionCliente;
        setCurrentFragment(FRAGMENT_SELECCION_FORMA_PAGO);
    }


    public Address getDireccionClienteMapa() {
        return direccionClienteMapa;
    }

    public void descartarDetallesDePedido(){
        pedido = new ArrayList<>();
    }

    public void eliminarDetalleDePedido(int index){
        pedido.remove(index);
    }

    public void insertarDetalleDePedido(DetallePedidoLoQueSea detalle){
        pedido.add(detalle);
    }

    public ArrayList<DetallePedidoLoQueSea> getPedido() {
        return pedido;
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == FRAGMENT_NUEVO_DETALLE_PRODUCTO) setCurrentFragment(FRAGMENT_PEDIDO);
        else super.onBackPressed();
    }

}
