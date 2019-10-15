package com.example.delivereat.activities.activity_pedido_loquesea;

import android.location.Address;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentConfirmarUbicacionCliente;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentConfirmarUbicacionNegocio;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentFechaHora;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentPedido;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentNuevoDetalleDeProducto;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentSeleccionDeUbicacionCliente;
import com.example.delivereat.activities.activity_pedido_loquesea.fragments.FragmentSeleccionDeUbicacionNegocio;
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
    public static final int FRAGMENT_CONFIRMAR_UBICACION_NEGOCIO = 6;
    public static final int FRAGMENT_UBICACION_NEGOCIO = 7;
    public static final int FRAGMENT_CUANDO_LO_ENVIAMOS = 8;

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
        setCurrentFragment(FRAGMENT_UBICACION_NEGOCIO);
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * Permite el flujo de la transaccion, pasando de interaz en interfaz y permitiendo volver a la intefaz anterior
     */
    public void setCurrentFragment(int codigo) {
        currentFragment = codigo;
        switch (codigo) {
            //pantalla de seleccion de ubicacion del negocio
            case FRAGMENT_UBICACION_NEGOCIO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentSeleccionDeUbicacionNegocio.newInstance())
                        .commit();
                tvTitulo.setText("Donde buscámos tu pedido");
                break;
                //Fragment de validacion de los datos de la ubicacion del negocio
            case FRAGMENT_CONFIRMAR_UBICACION_NEGOCIO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentConfirmarUbicacionNegocio.newInstance())
                        .commit();
                tvTitulo.setText("Confirmá los datos del negocio");
                break;
                //pantalla de direccion  del cliente
            case FRAGMENT_UBICACION_CLIENTE:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentSeleccionDeUbicacionCliente.newInstance())
                        .commit();
                tvTitulo.setText("¿Dónde llevamos tu pedido?");
                break;
                // Fragment de validacion de los datos de la direccion del cliente
            case FRAGMENT_CONFIRMAR_UBICACION_CLIENTE:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentConfirmarUbicacionCliente.newInstance())
                        .commit();
                tvTitulo.setText("Confirmá tu ubicación");
                break;
                // Pantalla de agregar productos en el carrito
            case FRAGMENT_PEDIDO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentPedido.newInstance())
                        .commit();
                tvTitulo.setText("Tu pedido");
                break;
                //pantalla de datos del producto a agregar al carrito
            case FRAGMENT_NUEVO_DETALLE_PRODUCTO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentNuevoDetalleDeProducto.newInstance())
                        .commit();
                tvTitulo.setText("Nuevo producto");
                break;
                // Pantalla de seleccion de forma de pago
            case FRAGMENT_SELECCION_FORMA_PAGO:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentSeleccionFormaPago.newInstance())
                        .commit();
                tvTitulo.setText("Seleccionar forma de pago");
                break;
                //pantalla de seleccion de horario de envio: "lo antes posible" o "horario programado"
            case FRAGMENT_CUANDO_LO_ENVIAMOS:
                getSupportFragmentManager().beginTransaction()
                        /*.setCustomAnimations()*/
                        .replace(R.id.fragment_container, FragmentFechaHora.newInstance())
                        .commit();
                tvTitulo.setText("¿Cuándo enviamos su pedido?");
                break;
            default:
                break;
        }
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    /**
     * Define la direccion parcial de entrega que se confirmara en una instancia del fragment de confirmacion de ubicacion
     * @param direccionClienteMapa
     */
    public void setDireccionClienteMapa(Address direccionClienteMapa) {
        this.direccionClienteMapa = direccionClienteMapa;
        setCurrentFragment(FRAGMENT_CONFIRMAR_UBICACION_CLIENTE);
    }

    /**
     * Define la direccion parcial del comercio que se confirmara en una instancia del fragment de confirmacion de ubicacion
     */
    public void setDireccionNegocioMapa(Address currentAdress) {
        this.direccionNegocioMapa = currentAdress;
        setCurrentFragment(FRAGMENT_CONFIRMAR_UBICACION_NEGOCIO);
    }

    /**
     * Define la direccion definitiva de entrega validada
     */
    public void setDireccionCliente(Ubicacion direccionCliente) {
        this.direccionCliente = direccionCliente;
        setCurrentFragment(FRAGMENT_SELECCION_FORMA_PAGO);
    }

    /**
     * Define la direccion definitiva del comercio validada
     */
    public void setDireccionNegocio(Ubicacion ubicacion) {
        this.direccionNegocio = ubicacion;
        setCurrentFragment(FRAGMENT_PEDIDO);
    }


    public Address getDireccionClienteMapa() {
        return direccionClienteMapa;
    }

    /**
     * Elimina un producto del detalle del pedido
     */
    public void eliminarDetalleDePedido(int index){
        pedido.remove(index);
    }
    /**
     * Agrega un detalle de pedido al pedido
     */
    public void insertarDetalleDePedido(DetallePedidoLoQueSea detalle){
        pedido.add(detalle);
    }

    public ArrayList<DetallePedidoLoQueSea> getPedido() {
        return pedido;
    }

    public Address getDireccionNegocioMapa() {
        return direccionNegocioMapa;
    }

    /**
     * calcula el gasto total de los detalles del pedido
     */
    public float getTotalAPagar(){
        float total = 0;
        for (DetallePedidoLoQueSea det : pedido){
            total += det.getPrecioFinal();
        }
        return total;
    }


    public void grabarDatosDePago() {
        setCurrentFragment(FRAGMENT_CUANDO_LO_ENVIAMOS);
    }
}
