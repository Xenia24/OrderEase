package com.example.agenda_panaderia.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_panaderia.R;

import kotlin.text.UStringsKt;

public class ViewHolderContacto extends RecyclerView.ViewHolder {
    View mView;

    private  ViewHolderContacto.ClickListener mClickListener;
public interface ClickListener {
    void onItemClick(View view, int position);/*Se ejecuta al presionar en el item*/
    void onItemLongClick(View view, int position);/*Se ejecuta al mantener presionado en el item*/
}
public void  setOnItemLongClickListener(ViewHolderContacto.ClickListener clickListener){
    mClickListener = clickListener;
}

    public ViewHolderContacto(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });

        itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
            }
        });
    }
    public void SetearDatosContacto(Context context, String id_contacto, String uid_contacto, String nombres, String apellidos, String correo, String telefono, String direccion){

        TextView Uid_Usuario, Nombre_c,Apellido_c,Correo_c,Telefono_c,Direccion_c;


        Uid_Usuario=mView.findViewById(R.id.Uid_Usuario);
        Nombre_c=mView.findViewById(R.id.Nombre_c);
        Apellido_c=mView.findViewById(R.id.Apellido_c);
        Correo_c=mView.findViewById(R.id.Correo_c);
        Telefono_c=mView.findViewById(R.id.Telefono_c);
        Direccion_c=mView.findViewById(R.id.Direccion_c);

        Uid_Usuario.setText(uid_contacto);
        Nombre_c.setText(nombres);
        Apellido_c.setText(apellidos);
        Correo_c.setText(correo);
        Telefono_c.setText(telefono);
        Direccion_c.setText(direccion);

    }
}
