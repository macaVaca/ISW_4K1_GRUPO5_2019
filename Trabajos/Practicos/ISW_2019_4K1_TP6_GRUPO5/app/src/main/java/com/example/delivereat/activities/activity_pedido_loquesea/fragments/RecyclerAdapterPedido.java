package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivereat.R;
import com.example.delivereat.activities.activity_pedido_loquesea.ActivityPedidoLoQueSea;
import com.example.delivereat.entities.DetallePedidoLoQueSea;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerAdapterPedido extends RecyclerView.Adapter<RecyclerAdapterPedido.DetallePedidoVH> {

        private ArrayList<DetallePedidoLoQueSea> pedido;
        private FragmentPedido mContext;
        private DecimalFormat df = new DecimalFormat("0.00");

        RecyclerAdapterPedido(FragmentPedido context, ArrayList<DetallePedidoLoQueSea> pedido) {
            this.mContext = context;
            this.pedido = pedido;
        }

    @NonNull
    @Override
    public DetallePedidoVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pedido, viewGroup, false);
        return new DetallePedidoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetallePedidoVH holder, int position) {
            holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return pedido.size();
    }

    class DetallePedidoVH extends RecyclerView.ViewHolder {
            private ImageView ivFoto;
            private TextView tvSubtotal;
            private TextView tvCantidad;
            private TextView tvNombre;
            private View view;

            DetallePedidoVH(View itemView) {
                super(itemView);
                view = itemView;
                ivFoto = itemView.findViewById(R.id.ivImagenProducto);
                tvNombre = itemView.findViewById(R.id.tvNombreArticulo);
                tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
                tvCantidad = itemView.findViewById(R.id.tvUnidades);
            }

        void bindView(final int position) {
            tvNombre.setText(pedido.get(position).getNombre());
            String subtotal = "$" + " " + df.format(pedido.get(position).getPrecioFinal());
            String unidades = pedido.get(position).getCantidad() + " " + pedido.get(position).getUnidad();
            tvSubtotal.setText(subtotal);
            tvCantidad.setText(unidades);
            if (pedido.get(position).getImagen() != null) ivFoto.setImageBitmap(pedido.get(position).getImagen());
            else ivFoto.setImageResource(R.mipmap.bmp_cart);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DialogConfirmar(((ActivityPedidoLoQueSea)mContext.requireActivity()), new OnSelectedOption() {
                        @Override
                        public void onYes() {
                            ((ActivityPedidoLoQueSea)mContext.requireActivity()).eliminarDetalleDePedido(position);
                            notifyItemRemoved(position);
                            mContext.updateList();
                        }

                        @Override
                        public void onNo() {

                        }
                    }, "¿Eliminar producto del pedido?" ,R.drawable.ic_warning).show();
                }
            });
        }
    }


}
