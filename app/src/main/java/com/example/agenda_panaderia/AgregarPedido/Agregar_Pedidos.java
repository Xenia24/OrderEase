package com.example.agenda_panaderia.AgregarPedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_panaderia.Objetos.Pedido;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agenda_panaderia.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Agregar_Pedidos extends AppCompatActivity {
    TextView id_Usuario, Correo_usuario, Fecha_Actual, Fecha, Estado;
    EditText Descripcion, Titulo, Nombre_Cliente;
    Button Btn_Calendario;
    ImageView Btn_pedido;
    private RadioGroup formaRadioGroup;
    private RadioButton localRadioButton;
    private RadioButton domicilioRadioButton;
    int dia, mes, year;
    DatabaseReference BD_firebase;
    String nombre= " ", titulo= " ", descripcion=" ", fecha= " ", forma_entrega = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedidos);

        InicializarVariables();
        Obtenerdatos();
        Obtener_Fecha();


        Btn_Calendario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();

                dia= calendario.get(Calendar.DAY_OF_MONTH);
                mes= calendario.get(Calendar.MONTH);
                year= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Agregar_Pedidos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearselect, int messelect, int diaselect) {
                        // Crear un objeto Calendar con la fecha seleccionada
                        Calendar fechaSeleccionada = Calendar.getInstance();
                        fechaSeleccionada.set(yearselect, messelect, diaselect);

                        // Obtener la fecha actual
                        Calendar fechaActual = Calendar.getInstance();
                        if (fechaSeleccionada.after(fechaActual)) {

                            String diaFormat, mesFormat;

                            if (diaselect < 10) {
                                diaFormat = "0" + String.valueOf(diaselect);
                            } else {
                                diaFormat = String.valueOf(diaselect);
                            }

                            int Mes = messelect + 1;

                            if (mes < 10) {
                                mesFormat = "0" + String.valueOf(Mes);
                            } else {
                                mesFormat = String.valueOf(Mes);
                            }

                            Fecha.setText(diaFormat + "/" + mesFormat + "/" + yearselect);
                        }
                        else {
                            Toast.makeText(Agregar_Pedidos.this, "La fecha no puede ser anterior a la fecha actual", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                        ,year,mes,dia);

                datePickerDialog.show();


            }
        });
        Btn_pedido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ValidarDatos();
            }
        });

    }


    private void InicializarVariables() {
        id_Usuario = findViewById(R.id.id_Usuario);

        Correo_usuario=findViewById(R.id.Correo_usuario);
        Fecha_Actual=findViewById(R.id.Fecha_Actual);
        Fecha=findViewById(R.id.Fecha);
        Estado=findViewById(R.id.Estado);
        Descripcion=findViewById(R.id.Descripcion);
        Titulo=findViewById(R.id.Titulo);
        Nombre_Cliente=findViewById(R.id.Nombre_Cliente);
        Btn_Calendario=findViewById(R.id.Btn_Calendario);
        formaRadioGroup = findViewById(R.id.formaRadioGroup);
        localRadioButton = findViewById(R.id.Local);
        domicilioRadioButton = findViewById(R.id.Domicilio);
        BD_firebase=FirebaseDatabase.getInstance().getReference();
        Btn_pedido=findViewById(R.id.Agregar_Pedido);


    }
    private void agregarpedido(){
        String uid_usuario = id_Usuario.getText().toString();
        String fecha_actual=Fecha_Actual.getText().toString();
        String fecha=Fecha.getText().toString();
        String estado=Estado.getText().toString();
        String titulo=Titulo.getText().toString();
        String descrip=Descripcion.getText().toString();
        String Nombre=Nombre_Cliente.getText().toString();
        String id_pedido = BD_firebase.push().getKey();


        if( uid_usuario.equals("") || fecha_actual.equals("") || titulo.equals("") ||
                descrip.equals("") || fecha.equals("") || estado.equals("") || Nombre.equals("")  || formaRadioGroup.getCheckedRadioButtonId() == -1) {

            int radioButtonId = formaRadioGroup.getCheckedRadioButtonId();
            String formaEntrega = "";

            if (radioButtonId == R.id.Local) {
                formaEntrega = "Local";
            } else if (radioButtonId == R.id.Domicilio) {
                formaEntrega = "Domicilio";
            }

            Pedido pedido = new Pedido(id_pedido,
                    fecha_actual,
                    uid_usuario,
                    Nombre,
                    titulo,
                    descrip,
                    fecha,
                    formaEntrega,
                    estado);
//h

            String nombre_BD = "Pedidos_Realizados";
            assert id_pedido != null;
            BD_firebase.child(nombre_BD).child(id_pedido).setValue(pedido);


            Toast.makeText(this, "Pedido Realizado", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        else{
            Toast.makeText(this, "Por favor, llene todos los campos y seleccione una forma de entrega", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidarDatos(){

        nombre = Nombre_Cliente.getText().toString();
        titulo = Titulo.getText().toString();
        descripcion = Descripcion.getText().toString();
        fecha = Fecha.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Ingresar nombre", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(titulo)){
            Toast.makeText(this,"Ingresar el titulo", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(descripcion)){
            Toast.makeText(this,"Ingresar una descripcion", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(fecha)){
            Toast.makeText(this,"Seleccione una fecha", Toast.LENGTH_SHORT).show();
        }
        else if (formaRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Selecciona una forma de entrega", Toast.LENGTH_SHORT).show();
        }
        else {
            agregarpedido();
        }

    }

    private void Obtener_Fecha(){
        String Fecha_hora_registro = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a",
                Locale.getDefault()).format(System.currentTimeMillis());
        Fecha_Actual.setText(Fecha_hora_registro);
    }


    private void Obtenerdatos() {

        String uid_recuperado= getIntent().getStringExtra("Uid");
        String email_recuperado= getIntent().getStringExtra("Correo");

        id_Usuario.setText(uid_recuperado);
        Correo_usuario.setText(email_recuperado);

    }
}
