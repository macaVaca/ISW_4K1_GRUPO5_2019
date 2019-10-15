package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.util.DialogAlert;

import java.security.spec.ECField;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class FragmentSeleccionFormaPago extends Fragment {

    private View viewDatosTarjeta;
    private View viewMontoPago;

    private Button buttonMontoPago;
    private float montoAPagar;

    private EditText etTarjeta;
    private EditText etFechaExpirar;
    private float totalPago;

    private int seleccion = 0;

    /**
     * radio botones de seleccion de forma de pago: pago en efectivo o pago con tarjeta VISA
     */
    private RadioGroup.OnCheckedChangeListener checkedFormaPago = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if( R.id.radioButtonTarjetaVISA == checkedId) {
                viewDatosTarjeta.setVisibility(View.VISIBLE);
                viewMontoPago.setVisibility(View.GONE);
                seleccion = 1;
                //buttonMontoPago.setAlpha(0);
            } else if (R.id.radioButtonEfectivo == checkedId) {
                viewDatosTarjeta.setVisibility(View.GONE);
                viewMontoPago.setVisibility(View.VISIBLE);
                //buttonMontoPago.setAlpha(1);
                seleccion = 2;
            }
        }
    };

   public static FragmentSeleccionFormaPago newInstance() {
       Bundle args = new Bundle();
       FragmentSeleccionFormaPago fragment = new FragmentSeleccionFormaPago();
       fragment.setArguments(args);
       return fragment;
   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_seleccion_forma_de_pago, container, false);
       viewDatosTarjeta = view.findViewById(R.id.llayoutDatosTarjeta);
        viewDatosTarjeta.setVisibility(View.GONE);
        viewMontoPago = view.findViewById(R.id.layoutMontoPago);
        viewMontoPago.setVisibility(View.GONE);
        RadioGroup rdFormaPago = view.findViewById(R.id.radioGroupFormaPago);
        rdFormaPago.setOnCheckedChangeListener(checkedFormaPago);
        totalPago = ((ActivityPedidoLoQueSea)requireActivity()).getTotalAPagar();
        String total = "$" + totalPago;
        ((TextView)view.findViewById(R.id.tvTotalAPagar)).setText(total);
        etTarjeta = view.findViewById(R.id.textNumTarjeta);
        etFechaExpirar = view.findViewById(R.id.textFechaExp);
        etTarjeta.addTextChangedListener(twTarjeta);
        etFechaExpirar.addTextChangedListener(twFecha);

        view.findViewById(R.id.buttonContFormaPago).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccion == 1){
                    //VISA
                    validarYGrabarTarjetaDeCredito();
                }
                else if(seleccion == 2){
                    //Efectivo
                    validarYGrabarEfectivo();
                }
                else new DialogAlert(requireContext(), "Seleccione un método de pago").show();
            }
        });

       return view;
    }

    /**
     * valida los datos de la tarjeta de Credito VISA y lo guarda para pagar el pedido
     */
    private void validarYGrabarTarjetaDeCredito(){
        if (!((EditText)requireActivity().findViewById(R.id.textNombreTitular)).getText().toString().isEmpty()){
            String validacionTarjeta = validarNumeroTarjeta();
            if (validacionTarjeta.equals("OK")){
                String validacionFecha = validarFecha();
                if (validacionFecha.equals("OK")){
                    if (validarCVC()){
                        ((ActivityPedidoLoQueSea)requireActivity()).grabarDatosDePago();
                    }
                    else{
                        //Validar CVC
                        new DialogAlert(requireContext(), "Ingrese código de 3 dígitos").show();
                    }
                }
                else{
                    //mensaje fecha
                    new DialogAlert(requireContext(), validacionFecha).show();
                }
            }
            else{
                //mostrar mensaje tarjeta
                new DialogAlert(requireContext(), validacionTarjeta).show();
            }
        }
        else new DialogAlert(requireContext(), "Ingrese titular de la tarjeta").show();
    }


    /**
     * validacion del numero de la tarejta de credito VISA
     * @return
     */
    private String validarNumeroTarjeta() {
        String numeroTarjeta = ((EditText)requireActivity().findViewById(R.id.textNumTarjeta)).getText().toString();
        if (numeroTarjeta.isEmpty()) return "Ingrese un número de tarjeta";
        if (numeroTarjeta.length() != 19){
            return "Numero inválido";
        }
        else if (!numeroTarjeta.substring(0,1).equals("4")) return "La tarjeta ingresada no es VISA";
        return "OK";
    }

    /**
     * validacion de la fecha de vencimiento de la tarjeta de credito
     * @return
     */
    private String validarFecha(){
       String fechaCaducidad = ((EditText)requireActivity().findViewById(R.id.textFechaExp)).getText().toString();
       if (fechaCaducidad.isEmpty()) return "Ingrese una fecha de validez";
       if (fechaCaducidad.length() == 5){
           //La tarjeta expira el ultimo dia del mes que esta impreso. Googleado.
           int mes = Integer.parseInt(fechaCaducidad.substring(0,2));
           int ano = Integer.parseInt(fechaCaducidad.substring(3,5));
           ano += 2000;

           if (mes > 12 || mes == 0) return "Ingrese un mes válido";

           int mesActual = Calendar.getInstance().get(Calendar.MONTH);
           int anoActual = Calendar.getInstance().get(Calendar.YEAR);

           if(ano > anoActual || (ano == anoActual && mes >= mesActual)){
                return "OK";
           }
           else return "La tarjeta expiró";

       }
       return "Ingrese una fecha válida";
    }

    private boolean validarCVC(){
        String CVC = ((EditText)requireActivity().findViewById(R.id.textCVC)).getText().toString();
        return CVC.length() == 3;
    }

    /**
     * valida el monto ingresado si es igual o mayor al total de pago del pedido y guarda para el pago del pedido
     */
    private void validarYGrabarEfectivo(){
       String textPago = ((EditText)requireActivity().findViewById(R.id.editTextAPagar)).getText().toString();
       if(!textPago.isEmpty() && !textPago.equals(".")){
           float aPagar = Float.parseFloat(textPago);
           if (aPagar >= totalPago) ((ActivityPedidoLoQueSea)requireActivity()).grabarDatosDePago();
           else new DialogAlert(requireContext(), "Monto insuficiente").show();
       }
       else new DialogAlert(requireContext(), "Ingrese el monto con el que va a pagar").show();
    }

    /**
     * controla el texto de ingreso de numero de tarjeta y el formato del numero de tarjeta
     */
    private TextWatcher twTarjeta = new TextWatcher() {
        public void afterTextChanged(Editable s) {

        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ((s.toString().length() == 5 || s.toString().length() == 10 || s.toString().length() == 15) && s.charAt(s.length() - 1) != '-'){
                String text = s.toString().substring(0, s.length() - 1) + "-" + s.toString().substring(s.length()-1, s.length());
                etTarjeta.setText(text);
                etTarjeta.setSelection(etTarjeta.getText().length());
            }
        }
    };


    /**
     * controla el texto de ingreso de la fecha de vencimiento de tarjeta y el formato de la fecha
     */
    private TextWatcher twFecha = new TextWatcher() {
        public void afterTextChanged(Editable s) {

        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() == 3 && s.charAt(s.length() - 1) != '/'){
                String text = s.toString().substring(0, s.length() - 1) + "/" + s.toString().substring(s.length()-1, s.length());
                etFechaExpirar.setText(text);
                etFechaExpirar.setSelection(etFechaExpirar.getText().length());
            }
        }
    };



}
