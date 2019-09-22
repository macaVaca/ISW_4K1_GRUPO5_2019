package com.example.delivereat.activities.activity_pedido_loquesea.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivereat.R;
import com.example.delivereat.entities.DetallePedidoLoQueSea;

import java.io.IOException;
import java.util.ArrayList;

public class RecyclerAdapterPedido extends RecyclerView.Adapter<RecyclerAdapterPedido.DetallePedidoVH> {

        private ArrayList<DetallePedidoLoQueSea> pedido;
        private Context mContext;

        public RecyclerAdapterPedido(Context context, ArrayList<DetallePedidoLoQueSea> pedido) {
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

            DetallePedidoVH(View itemView) {
                super(itemView);
                mContext = itemView.getContext();
                ivFoto = itemView.findViewById(R.id.ivImagenProducto);
                tvNombre = itemView.findViewById(R.id.tvNombreArticulo);
                tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
                tvCantidad = itemView.findViewById(R.id.tvUnidades);
            }

        void bindView(int position) {
            tvNombre.setText(pedido.get(position).getNombre());
            String subtotal = "$" + " " + pedido.get(position).getPrecioFinal();
            String unidades = pedido.get(position).getCantidad() + " " + pedido.get(position).getUnidad();
            tvSubtotal.setText(subtotal);
            tvCantidad.setText(unidades);
            if (pedido.get(position).getImagen() != null) ivFoto.setImageBitmap(pedido.get(position).getImagen());
            else ivFoto.setImageResource(R.mipmap.bmp_cart);
        }
    }
}
