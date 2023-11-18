package com.example.agenda_panaderia.Pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.agenda_panaderia.R;

public class Detalle_Pedido extends AppCompatActivity {

        String id_pedido, uid_usuario, nombre_usuario,fecha_registro, titulo, descripcion, fecha_pedido, forma_entrega,estado;
        TextView Id_Pedido_Detalle, Id_Usuario_Detalle, Nombre_Detalle, Titulo_Detalle, Descripcion_Detalle, Fecha_Registro_Detalle, Fecha_Pedido_Detalle, Estado_Detalle;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detalle_pedido);
            Iniciar_vista();
            RecuperarDatos();
            SetDatosR();
//r
        }
        private void  Iniciar_vista(){
            Id_Pedido_Detalle=findViewById(R.id.Id_Pedido_Detalle);
            Id_Usuario_Detalle=findViewById(R.id.Id_Usuario_Detalle);
            Nombre_Detalle=findViewById(R.id.Nombre_Detalle);
            Titulo_Detalle=findViewById(R.id.Titulo_Detalle);
            Descripcion_Detalle=findViewById(R.id.Descripcion_Detalle);
            Fecha_Registro_Detalle=findViewById(R.id.Fecha_Registro_Detalle);
            Fecha_Pedido_Detalle=findViewById(R.id.Fecha_Pedido_Detalle);
            Estado_Detalle=findViewById(R.id.Estado_Detalle);
        }
        private  void RecuperarDatos(){
            Bundle intent = getIntent().getExtras();

            id_pedido=intent.getString("id_pedido");
            uid_usuario=intent.getString("uid_usuario");
            nombre_usuario=intent.getString("nombre_usuario");
            titulo=intent.getString("titulo");
            descripcion=intent.getString("descripcion");
            fecha_registro=intent.getString("fecha_registro");
            fecha_pedido=intent.getString("fecha_pedido");
            //forma_entrega=intent.getString("forma_entrega");
            estado=intent.getString("estado");
        }
        private void SetDatosR(){
            Id_Pedido_Detalle.setText(id_pedido);
            Id_Usuario_Detalle.setText(uid_usuario);
            Nombre_Detalle.setText(nombre_usuario);
            Titulo_Detalle.setText(titulo);
            Descripcion_Detalle.setText(descripcion);
            Fecha_Registro_Detalle.setText(fecha_registro);
            Fecha_Pedido_Detalle.setText(fecha_pedido);
            Estado_Detalle.setText(estado);
        }
}

