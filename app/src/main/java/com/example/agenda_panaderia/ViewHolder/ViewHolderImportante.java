package com.example.agenda_panaderia.ViewHolder;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_panaderia.R;
public class ViewHolderImportante  extends RecyclerView.ViewHolder {
    static View mView;
    private ViewHolderImportante.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderImportante.ClickListener clickListener){
        mClickListener = clickListener;
    }
    public ViewHolderImportante(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getBindingAdapterPosition());
                return false;
            }
        });
    }

    public static void SetearDatosPedidos(Context context,
                                          String id_pedido,
                                          String uid_usuario,
                                          String nombre,
                                          String fecha_actual,
                                          String titulo,
                                          String descripcion,
                                          String fecha_pedido,
                                          String estado){


        //DECLARAR LAS VISTAS
        TextView Id_Pedido_Item, Id_Usuario_Item, Nombre_Cliente_Item ,Fecha_Actual_Registro,Titulo_Item,
                Descripcion_Item, Fecha_Item, Estado_Item;

        ImageView Tarea_Finalizada_Item, Tarea_No_Finalizada_Item;

        //ESTABLECER LA CONEXIÓN CON EL ITEM
        Id_Pedido_Item = mView.findViewById(R.id.Id_Pedido_Item_I);
        Id_Usuario_Item = mView.findViewById(R.id.Uid_Usuario_Item_I);
        Nombre_Cliente_Item = mView.findViewById(R.id.Nombre_Cliente_Importante_I);
        Fecha_Actual_Registro = mView.findViewById(R.id.Fecha_hora_registro_Item_I);
        Titulo_Item = mView.findViewById(R.id.Titulo_Item_I);
        Descripcion_Item = mView.findViewById(R.id.Descripcion_Item_I);
        Fecha_Item = mView.findViewById(R.id.Fecha_Item_I);
        Estado_Item = mView.findViewById(R.id.Estado_Item_I);

        Tarea_Finalizada_Item = mView.findViewById(R.id.Tarea_Finalizada_Item_I);
        Tarea_No_Finalizada_Item = mView.findViewById(R.id.Tarea_No_Finalizada_Item_I);

        //SETEAR LA INFORMACIÓN DENTRO DEL ITEM
        Id_Pedido_Item.setText(id_pedido);
        Id_Usuario_Item.setText(uid_usuario);
        Nombre_Cliente_Item.setText(nombre);
        Fecha_Actual_Registro.setText(fecha_actual);
        Titulo_Item.setText(titulo);
        Descripcion_Item.setText(descripcion);
        Fecha_Item.setText(fecha_pedido);
        Estado_Item.setText(estado);

        //GESTIONAMOS EL COLOR DEL ESTADO
        if (estado.equals("Finalizado")){
            Tarea_Finalizada_Item.setVisibility(View.VISIBLE);
        }else {
            Tarea_No_Finalizada_Item.setVisibility(View.VISIBLE);
        }
    }
}
