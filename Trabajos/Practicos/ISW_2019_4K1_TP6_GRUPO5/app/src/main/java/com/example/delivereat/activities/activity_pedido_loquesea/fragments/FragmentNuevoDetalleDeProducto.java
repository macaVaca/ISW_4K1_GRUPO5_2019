package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.entities.DetallePedidoLoQueSea;
import com.example.delivereat.util.DialogAlert;

import java.io.File;
import java.io.IOException;

public class FragmentNuevoDetalleDeProducto extends Fragment {

    private Bitmap bitmap;
    private ImageView ivProducto;
    private View layoutImagen;
    private View placeholderNoImage;

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

        togglePlaceholders();
        return view;
    }

    private View.OnClickListener listenerAgregarProducto = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String mensaje = validarCampos();
            if (mensaje.equals("OK")) {
                DetallePedidoLoQueSea detalle = new DetallePedidoLoQueSea("Detalle de prueba", 20f, 1f, 0);
                if (bitmap != null) detalle.setImagen(bitmap);
                ((ActivityPedidoLoQueSea)requireActivity()).insertarDetalleDePedido(detalle);
                new DialogDetalleAgregado((ActivityPedidoLoQueSea) requireActivity(), oso).show();
            }
            else new DialogAlert(requireContext(), mensaje).show();
        }
    };

    private String validarCampos(){
        return "OK";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK ){
            if (requestCode == GALLERY_REQUEST_CODE && data.getData() != null){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                    bitmap = getScaledBitMap(bitmap, 1000);
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
                        bitmap = getScaledBitMap(bitmap, 1000);
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

    private Bitmap getScaledBitMap(Bitmap bmp, int tamanoDeseado){
        float height = bmp.getHeight();
        float width = bmp.getWidth();
        float multiplicador;

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

    private OnSelectedOption osoFoto = new OnSelectedOption() {
        @Override
        public void onYes() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        }

        @Override
        public void onNo() {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    };
}
