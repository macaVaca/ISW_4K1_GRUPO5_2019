package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.entities.DetallePedidoLoQueSea;
import com.example.delivereat.util.DialogAlert;

import java.io.IOException;
import java.text.DecimalFormat;

public class FragmentNuevoDetalleDeProducto extends Fragment {

    private Bitmap bitmap;
    private ImageView ivProducto;
    private View layoutImagen;
    private View placeholderNoImage;
    private EditText etNombre;
    private EditText etCantidad;
    private EditText etSubtotal;
    private EditText etPrecioU;
    private Spinner spinnerUnidad;

    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 1;

    public static FragmentNuevoDetalleDeProducto newInstance() {
        FragmentNuevoDetalleDeProducto fragment = new FragmentNuevoDetalleDeProducto();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevo_detalle_pedido, container, false);
        view.findViewById(R.id.buttonAgregarDetalle).setOnClickListener(listenerAgregarProducto);
        ivProducto = view.findViewById(R.id.ivImagenProducto);
        view.findViewById(R.id.buttonSeleccionarImage).setOnClickListener(listenerBuscarImagen);
        layoutImagen = view.findViewById(R.id.layoutImagenSeleccionada);
        placeholderNoImage = view.findViewById(R.id.tvPlaceholderNoImage);
        view.findViewById(R.id.buttonEliminarImagen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = null;
                togglePlaceholders();
            }
        });

        etNombre = view.findViewById(R.id.etNombreProducto);
        etPrecioU = view.findViewById(R.id.etPrecioUnitario);
        etCantidad = view.findViewById(R.id.etCantidad);
        spinnerUnidad = view.findViewById(R.id.spinnerUnidad);
        etSubtotal = view.findViewById(R.id.etSubtotal);

        ArrayAdapter<CharSequence> adapterUnidades = ArrayAdapter.createFromResource(requireActivity(),
                R.array.unidades, R.layout.plantilla_spinner);
        adapterUnidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidad.setAdapter(adapterUnidades);

        togglePlaceholders();
        df.setMaximumFractionDigits(2);

        etCantidad.addTextChangedListener(twSubtotal);
        etPrecioU.addTextChangedListener(twSubtotal);

        return view;
    }

    /**
     * agrega el producto creado al pedido actual
     */
    private View.OnClickListener listenerAgregarProducto = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            DetallePedidoLoQueSea detalle = crearDetalle();
            if (detalle != null) {
                if (bitmap != null) detalle.setImagen(bitmap);
                ((ActivityPedidoLoQueSea)requireActivity()).insertarDetalleDePedido(detalle);
                new DialogConfirmar((ActivityPedidoLoQueSea) requireActivity(), oso, "", R.drawable.ic_check).show();
            }
        }
    };

    /**
     * crea el producto con sus detalles y valida los datos del msimo
     */
    private DetallePedidoLoQueSea crearDetalle(){
        String nombre;
        float cantidad;
        float precio;
        int unidad;
        String error = "";
        DetallePedidoLoQueSea detalle = null;

        if(!etNombre.getText().toString().isEmpty()){
            nombre = etNombre.getText().toString();
            if (!etCantidad.getText().toString().isEmpty() && !etCantidad.getText().toString().equals(".")){
                cantidad = Float.parseFloat(etCantidad.getText().toString());
                if (cantidad > 0){
                    if (!etPrecioU.getText().toString().isEmpty() && !etPrecioU.getText().toString().equals(".")){
                        precio = Float.parseFloat(etPrecioU.getText().toString());
                        if (precio > 0){
                            unidad = spinnerUnidad.getSelectedItemPosition();
                            detalle = new DetallePedidoLoQueSea(nombre, precio, cantidad, unidad, bitmap);
                        }
                        else error = "Ingresá un precio unitario mayor a 0";
                    }
                    else error = "Ingresá una cantidad mayor a 0";
                }
                else error = "Ingresá una cantidad mayor a 0";
            }
            else error = "Ingresá una cantidad mayor a 0";
        }
        else error = "Ingrese una descripción al producto";

        if (!error.isEmpty()) new DialogAlert(requireContext(), error).show();

        return detalle;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK ){
            if (requestCode == GALLERY_REQUEST_CODE && data.getData() != null){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                    bitmap = getScaledBitMap(bitmap);
                    ivProducto.setImageBitmap(bitmap);
                    togglePlaceholders();
                }
                catch(IOException e){
                    Log.d("DeliverEat", "Error al cargar imagen");
                }
            }
            else if(requestCode == CAMERA_REQUEST_CODE){
                Log.d("debug", "onActivityResult: haybitmap" );
                if(data.getExtras() != null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Log.d("debug", "onActivityResult: haybitmap" );
                    if (bitmap != null){
                        bitmap = getScaledBitMap(bitmap);
                        ivProducto.setImageBitmap(bitmap);
                        togglePlaceholders();
                    }
                }
            }
        }
    }

    private View.OnClickListener listenerBuscarImagen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DialogSeleccionarFoto(requireContext(), osoFoto).show();
        }
    };

    /***
     * Con este metodo garantizamos la eficiencia en el manejo de las imagenes
     * Formateamos cualquier imagen Jpeg que se seleccione o se tome y le hacemos resize a 1000 * 1000 px como máximo
     * Una imagen de 1000 x 1000 con profundidad de bits de 32 (máximo) tiene 4 bytes x pixel
     * lo cual tiene 1000 * 1000 * 4 bytes, o sea 4000000 bytes, lo que son 4000kb, o sea, 4Mb como máximo que se puede subir.
     * No limitamos a los usuarios a seleccionar una imagen liviana porque directamente nosotros la hacemos liviana.
     * Al server se subiría entonces como máximo con 4mb.
     *
     * @param bmp bitmap a reducir
     * @return bitmap resizeado y comprimido.
     */
    private Bitmap getScaledBitMap(Bitmap bmp){
        float height = bmp.getHeight();
        float width = bmp.getWidth();
        float multiplicador;
        int tamanoDeseado = 1000;

        if (height > width){
            //Imagen mas alta
            //50/92 0.6
            multiplicador = height/width;
            height = tamanoDeseado;
            width = Math.round(tamanoDeseado / multiplicador);
        }
        else{
            multiplicador = width / height;
            width = tamanoDeseado;
            height = Math.round(tamanoDeseado / multiplicador);
        }

        bmp = Bitmap.createScaledBitmap(bmp, Math.round(width), Math.round(height),false);

        return bmp;
    }

    private void togglePlaceholders(){
        layoutImagen.setVisibility(bitmap == null ? View.GONE : View.VISIBLE);
        placeholderNoImage.setVisibility(bitmap == null ? View.VISIBLE : View.GONE);
    }

    private OnSelectedOption oso = new OnSelectedOption() {
        @Override
        public void onYes() {
            ((ActivityPedidoLoQueSea)requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_NUEVO_DETALLE_PRODUCTO);
        }

        @Override
        public void onNo() {
            ((ActivityPedidoLoQueSea)requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_PEDIDO);
        }
    };
    /**
     * seleccion de imagen para el producto: opcion galeria de celular y opcion camara del celular
     */
    private OnSelectedOption osoFoto = new OnSelectedOption() {
        @Override
        public void onYes() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        }

        @Override
        public void onNo() {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    };
    /**
     * ejecuta el calculo del subtotal luego de modificar el precio y la cantidad unitaria del producto
     */
    private TextWatcher twSubtotal = new TextWatcher() {

        public void afterTextChanged(Editable s) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            etSubtotal.setText(calcularSubtotal());
        }
    };


    private DecimalFormat df = new DecimalFormat("0.00");

    private String calcularSubtotal(){
        if (!etCantidad.getText().toString().isEmpty() && !etCantidad.getText().toString().equals(".") &&
                !etPrecioU.getText().toString().isEmpty() && !etPrecioU.getText().toString().equals(".")){

            Float cantidad = Float.parseFloat(etCantidad.getText().toString());
            Float precioUnitario = Float.parseFloat(etPrecioU.getText().toString());
            return "$" + df.format(cantidad * precioUnitario);

        }
        return "$0.00";
    };
}
