package com.example.agenda_panaderia.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_panaderia.R;

public class ViewHolderContacto extends RecyclerView.ViewHolder {
    View mView;

    private  ViewHolderContacto.ClickListener mClickListener;



    public interface ClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolderContacto.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public ViewHolderContacto(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getBindingAdapterPosition());
                return false;
            }
        });
    }
    public void SetearDatosContacto(Context context,
                                    String id_contacto,
                                    String uid_contacto,
                                    String nombres,
                                    String apellidos,
                                    String correo,
                                    String telefono,
                                    String direccion){

        TextView Id_c_Item, Uid_c_Item,nombre_c_Item,apellido_c_Item,Correo_c_Item,telefono_c_Item,direccion_c_Item;


        Id_c_Item=mView.findViewById(R.id.Id_c_Item);
        Uid_c_Item=mView.findViewById(R.id.Uid_c_Item);
        nombre_c_Item=mView.findViewById(R.id.nombre_c_Item);
        apellido_c_Item=mView.findViewById(R.id.apellido_c_Item);
        Correo_c_Item=mView.findViewById(R.id.Correo_c_Item);
        telefono_c_Item=mView.findViewById(R.id.telefono_c_Item);
        direccion_c_Item=mView.findViewById(R.id.direccion_c_Item);

        Id_c_Item.setText(id_contacto);
        Uid_c_Item.setText(uid_contacto);
        nombre_c_Item.setText(nombres);
        apellido_c_Item.setText(apellidos);
        Correo_c_Item.setText(correo);
        telefono_c_Item.setText(telefono);
        direccion_c_Item.setText(direccion);

    }
}
