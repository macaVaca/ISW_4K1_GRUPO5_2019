package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.entities.Ubicacion;
import com.example.delivereat.util.DialogAlert;

public class FragmentConfirmarUbicacionNegocio extends Fragment {

    private Address address;
    private String provincia;
    private String localidad;
    private String calle;
    private String numero;
    private String piso;
    private String depto;
    private EditText editTextCalle;
    private EditText editTextNumero;
    private EditText editTextDepto;
    private EditText editTextPiso;
    private EditText editTextReferencia;
    private Spinner spinnerProvincias;
    private Spinner spinnerLocalidades;
    boolean primeraCarga = true;


    public static FragmentConfirmarUbicacionNegocio newInstance() {
        FragmentConfirmarUbicacionNegocio fragment = new FragmentConfirmarUbicacionNegocio();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingresar_ubicacion, container, false);
        editTextCalle = view.findViewById(R.id.editTextCalle);
        editTextNumero = view.findViewById(R.id.editTextNro);
        editTextPiso = view.findViewById(R.id.editTextPiso);
        editTextDepto = view.findViewById(R.id.editTextDepto);
        editTextReferencia = view.findViewById(R.id.editTextReferencia);
        spinnerProvincias = view.findViewById(R.id.spinnerProvincia);
        spinnerLocalidades = view.findViewById(R.id.spinnerLocalidad);

        ArrayAdapter<CharSequence> adapterProvincias = ArrayAdapter.createFromResource(requireActivity(),
                R.array.array_provincias, R.layout.plantilla_spinner);

        ArrayAdapter<CharSequence> adapterLocalidades = ArrayAdapter.createFromResource(requireActivity(),
                R.array.array_localidades_ca, R.layout.plantilla_spinner);

        adapterLocalidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterProvincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerProvincias.setAdapter(adapterProvincias);

        view.findViewById(R.id.buttonVolverAMapa).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityPedidoLoQueSea)requireActivity()).setCurrentFragment(ActivityPedidoLoQueSea.FRAGMENT_UBICACION_NEGOCIO);
                }
        });

        view.findViewById(R.id.buttonConfirmarUbicacion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUbicacionNegocio();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        address = ((ActivityPedidoLoQueSea)requireActivity()).getDireccionNegocioMapa();
        cargarCamposDeTexto();
        spinnerLocalidades.setOnItemSelectedListener(listenerSpinnerLocalidades);
        spinnerProvincias.setOnItemSelectedListener(listenerSpinnerProvincias);
    }

    private void cargarCamposDeTexto() {
        if(address != null){
            provincia = address.getAdminArea();
            localidad = address.getLocality();
            calle = address.getThoroughfare();
            numero = address.getSubThoroughfare();
        }
        else{
            provincia = null;
            localidad = null;
            calle = null;
            numero = null;
        }

        piso = null;
        depto = null;


        if (provincia != null) {
            String localidadAux = localidad;
            switch (provincia){
                case "Buenos Aires":
                    spinnerProvincias.setSelection(1);
                    cargarLocalidad(localidadAux, R.array.array_localidades_ba);
                    break;
                case "Santa Fe":
                    spinnerProvincias.setSelection(2);
                    cargarLocalidad(localidadAux, R.array.array_localidades_sf);
                    break;
                case "Córdoba":
                    spinnerProvincias.setSelection(3);
                    cargarLocalidad(localidadAux, R.array.array_localidades_cb);
                    break;
                default:
                    spinnerProvincias.setSelection(0);
                    cargarLocalidad(localidadAux, R.array.array_localidades_ca);
            }
        }
        editTextCalle.setText((calle == null)? "" : calle);
        editTextNumero.setText((numero == null)? "" : numero);
        editTextPiso.setText("");
        editTextDepto.setText("");
    }

    private void cargarLocalidad(String localidadAux, int array_localidades) {
        String[] array = getResources().getStringArray(array_localidades);
        for (int i = 0; i < array.length; i++){
            if(localidadAux.equals(array[i])){
                spinnerLocalidades.setSelection(i);
                return;
            }
        }
    }

    private Spinner.OnItemSelectedListener listenerSpinnerProvincias = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            ArrayAdapter<CharSequence> adapterLocalidades;

            switch (position){
                case 0:
                    ((TextView) spinnerProvincias.getSelectedView()).setTextColor(0xff808080);
                    provincia = null;
                    localidad = null;
                    spinnerLocalidades.setEnabled(false);
                    adapterLocalidades = ArrayAdapter.createFromResource(requireActivity(),
                        R.array.array_localidades_ca, R.layout.plantilla_spinner);
                    break;
                case 1:
                    provincia = "Buenos Aires";
                    spinnerLocalidades.setEnabled(true);
                    adapterLocalidades = ArrayAdapter.createFromResource(requireActivity(),
                            R.array.array_localidades_ba, R.layout.plantilla_spinner);
                    break;
                case 2:
                    provincia = "Santa Fe";
                    spinnerLocalidades.setEnabled(true);
                    adapterLocalidades = ArrayAdapter.createFromResource(requireActivity(),
                            R.array.array_localidades_sf, R.layout.plantilla_spinner);
                    break;
                default:
                    provincia = "Córdoba";
                    spinnerLocalidades.setEnabled(true);
                    adapterLocalidades = ArrayAdapter.createFromResource(requireActivity(),
                            R.array.array_localidades_cb, R.layout.plantilla_spinner);
                    break;
            }

            spinnerLocalidades.setAdapter(adapterLocalidades);
            adapterLocalidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            if(primeraCarga){
                primeraCarga = false;
                cargarCamposDeTexto();
            }
            else {
                spinnerLocalidades.setSelection(0);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private Spinner.OnItemSelectedListener listenerSpinnerLocalidades = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                ((TextView) spinnerLocalidades.getSelectedView()).setTextColor(0xff808080);
                localidad = null;
            }
            else{

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void validarUbicacionNegocio(){

        if (spinnerLocalidades.getSelectedItemPosition() > 0 && spinnerProvincias.getSelectedItemPosition() > 0){
            Ubicacion ubicacion = new Ubicacion(spinnerProvincias.getSelectedItem().toString(), spinnerLocalidades.getSelectedItem().toString(),
                    editTextCalle.getText().toString(), editTextNumero.getText().toString(), editTextPiso.getText().toString(), editTextDepto.getText().toString(), editTextReferencia.getText().toString());
            String validacion = ubicacion.validar();
            if (validacion.equals("OK")){
                ((ActivityPedidoLoQueSea)requireActivity()).setDireccionNegocio(ubicacion);

            }
            else new DialogAlert(requireContext(), validacion).show();
        }
        else{
            new DialogAlert(requireContext(), "¡Seleccione provincia y localidad!").show();
        }
    }
}
