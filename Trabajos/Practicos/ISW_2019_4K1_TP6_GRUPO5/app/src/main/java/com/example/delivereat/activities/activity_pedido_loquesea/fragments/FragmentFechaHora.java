package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.delivereat.R;
import com.example.delivereat.util.DialogAlert;
import com.example.delivereat.util.OnAlertClosed;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentFechaHora extends Fragment {

    private View layoutFechaHora;
    private Button buttonFecha;
    private Button buttonHora;
    private final Calendar calHora = Calendar.getInstance();
    private int seleccion = 1;
    boolean horaSeteada = false;
    boolean fechaSeteada = false;

    /**
     * creacion del View para la pantalla seleccion de horario de entrega: lo antes posible o entrega programada
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fecha_hora, container, false);
        layoutFechaHora = view.findViewById(R.id.layout_fecha_hora);
        layoutFechaHora.setVisibility(View.GONE);
        buttonFecha = view.findViewById(R.id.buttonSeleccionarFecha);
        buttonHora = view.findViewById(R.id.buttonSeleccionarHora);

        buttonHora.setOnClickListener(listenerHora);
        buttonFecha.setOnClickListener(listenerFecha);

        ((RadioGroup) view.findViewById(R.id.radioGroupFecha)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonYa) seleccion = 1;
                else seleccion = 2;

                layoutFechaHora.setVisibility(checkedId == R.id.radioButtonYa ? View.GONE : View.VISIBLE);
            }
        });

        view.findViewById(R.id.buttonConfirmarPedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((seleccion == 1) || (horaSeteada && fechaSeteada && Calendar.getInstance().before(calHora))) {
                    //Ok.
                    new DialogAlert(requireContext(), "Pedido registrado con éxito!", new OnAlertClosed() {
                        @Override
                        public void OnAlertClosed() {
                            requireActivity().finish();
                        }
                    }).show();
                } else {
                    new DialogAlert(requireContext(), "¡Seleccione fecha y hora válidas!").show();
                }
            }
        });

        return view;
    }

    /**
     * seleccion y formato de la hora de entrega programada
     */
    private View.OnClickListener listenerHora = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //New timepicker
            boolean use24HourClock = DateFormat.is24HourFormat(requireContext());
            TimePickerDialog tpd = new TimePickerDialog(requireActivity(), time, calHora
                    .get(Calendar.HOUR_OF_DAY), calHora.get(Calendar.MINUTE), use24HourClock);

            tpd.show();
        }
    };

    /**
     * calendario para la seleccion de la fecha de entrega programada
     */
    private Calendar calendar = Calendar.getInstance();
    private View.OnClickListener listenerFecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //new datePicker
            DatePickerDialog dpd = new DatePickerDialog(requireActivity(), dateFinListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            Calendar cal = Calendar.getInstance();
            DatePicker dp = dpd.getDatePicker();
            dp.setMinDate(cal.getTimeInMillis());
            dpd.show();

        }
    };

    private final DatePickerDialog.OnDateSetListener dateFinListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calHora.set(Calendar.YEAR, year);
            calHora.set(Calendar.MONTH, monthOfYear);
            calHora.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String fecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            buttonFecha.setText(fecha);
            fechaSeteada = true;
        }
    };

    private final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calHora.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calHora.set(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String hora24 = sdf.format(calHora.getTime());
            buttonHora.setText(hora24);
            horaSeteada = true;
        }

    };

    public static FragmentFechaHora newInstance() {
        FragmentFechaHora fragment = new FragmentFechaHora();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
