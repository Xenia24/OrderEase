package com.example.agenda_panaderia.Pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_panaderia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Actualizar_Pedidos extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    TextView Id_nota_A,Nombre_Cliente_A, Uid_Usuario_A, Fecha_registro_A, Estado_A, Fecha_A, Estado_nuevo, Estado_nuevo2, Estado_E;
    EditText Titulo_A,Descripcion_A;
    Button Btn_Calendario_A, Btn_Actualizar_P_A;

    Spinner Spinner_estado, Spinner_estado_2;
    String id_pedido_R, uid_usuario_R,nombre_cliente_R, fecha_registro_R, fecha_R,estado_n_R, titulo_R,descripcion_R,tipo_pedido_R;
    int year,mes,dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pedidos);
        InicializarVistas();
        RecuperarDatos();
        SetearDatos();
        Spinner_Estado();
        Spinner_Pedido();


        Btn_Actualizar_P_A.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ActualizarDatos();
            }
        });
        Btn_Calendario_A.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();

                dia= calendario.get(Calendar.DAY_OF_MONTH);
                mes= calendario.get(Calendar.MONTH);
                year= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Actualizar_Pedidos.this, new DatePickerDialog.OnDateSetListener() {
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

                            Fecha_A.setText(diaFormat + "/" + mesFormat + "/" + yearselect);
                        }
                        else {
                            Toast.makeText(Actualizar_Pedidos.this, "La fecha no puede ser anterior a la fecha actual", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                        ,year,mes,dia);

                datePickerDialog.show();


            }
        });
    }
    private void InicializarVistas() {
        Id_nota_A = findViewById(R.id.Id_pedido_A);
        Uid_Usuario_A = findViewById(R.id.Uid_Usuario_A);
        Estado_A = findViewById(R.id.Estado_A);
        Estado_E = findViewById(R.id.Estado_E);
        Fecha_A = findViewById(R.id.Fecha_A);
        Nombre_Cliente_A=findViewById(R.id.Nombre_Cliente_A);
        Estado_nuevo = findViewById(R.id.Estado_nuevo);
        Titulo_A = findViewById(R.id.Titulo_A);
        Descripcion_A = findViewById(R.id.Descripcion_A);
        Btn_Calendario_A = findViewById(R.id.Btn_Calendario_A);
        Spinner_estado_2 = findViewById(R.id.Spinner_estado_2);
        Spinner_estado = findViewById(R.id.Spinner_estado);
        Fecha_registro_A = findViewById(R.id.Fecha_registro_A);
        Estado_nuevo2 = findViewById(R.id.Estado_nuevo2);
        Btn_Actualizar_P_A=findViewById(R.id.Btn_Actualizar_P_A);
    }
    private void RecuperarDatos(){
        Bundle intent= getIntent().getExtras();
        id_pedido_R= intent.getString("id_pedido");
        uid_usuario_R= intent.getString("uid_usuario");
        nombre_cliente_R=intent.getString("nombre_usuario");
        fecha_registro_R= intent.getString("fecha_registro");
        estado_n_R= intent.getString("estado");
        fecha_R= intent.getString("fecha_pedido");
        titulo_R= intent.getString("titulo");
        descripcion_R= intent.getString("descripcion");
        tipo_pedido_R=intent.getString("forma_entrega");


    }
    private void  SetearDatos(){
        Id_nota_A.setText(id_pedido_R);
        Uid_Usuario_A.setText(uid_usuario_R);
        Fecha_A.setText(fecha_R);
        Fecha_registro_A.setText(fecha_registro_R);
        Estado_nuevo.setText(estado_n_R);
        Fecha_A.setText(fecha_R);
        Titulo_A.setText(titulo_R);
        Descripcion_A.setText(descripcion_R);
        Estado_nuevo2.setText(tipo_pedido_R);
        Nombre_Cliente_A.setText(nombre_cliente_R);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void Spinner_Estado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Estados_pedido, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_estado.setAdapter(adapter);
        Spinner_estado.setOnItemSelectedListener(new EstadoItemSelectedListener());
    }

    private void Spinner_Pedido() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Forma_pedido, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_estado_2.setAdapter(adapter);
        Spinner_estado_2.setOnItemSelectedListener(new PedidoItemSelectedListener());
    }

    private class EstadoItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String ESTADO_ACTUAL = Estado_A.getText().toString();
            String Posicion_1 = adapterView.getItemAtPosition(0).toString();
            String estado_seleccionado = adapterView.getItemAtPosition(i).toString();
            Estado_nuevo.setText(estado_seleccionado);

            if (ESTADO_ACTUAL.equals("No Entregado")) {
                Estado_nuevo.setText(Posicion_1);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class PedidoItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String Estado_Pedido = Spinner_estado_2.getSelectedItem().toString();
            String Posicion_2 = adapterView.getItemAtPosition(0).toString();
            String estado_seleccionado2 = adapterView.getItemAtPosition(i).toString();
            Estado_nuevo2.setText(estado_seleccionado2);

            if (Estado_Pedido.equals("Local")) {
                Estado_nuevo2.setText(Posicion_2);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Implementa según sea necesario
        }
    }


    private  void ActualizarDatos(){
        String nombre_Ac= Nombre_Cliente_A.getText().toString();
        String titulo_Ac=Titulo_A.getText().toString();
        String descripcion_Ac=Descripcion_A.getText().toString();
        String fecha_pedido_Ac=Fecha_A.getText().toString();
        String estado_Ac=Estado_nuevo.getText().toString();
        String forma_entrega_Ac=Estado_nuevo2.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = firebaseDatabase.getReference("Pedidos_Realizados");
        Query query = dbReference.orderByChild("id_pedido").equalTo(id_pedido_R);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ds.getRef().child("nombre").setValue(nombre_Ac);
                    ds.getRef().child("titulo").setValue(titulo_Ac);
                    ds.getRef().child("descripcion").setValue(descripcion_Ac);
                    ds.getRef().child("fecha_pedido").setValue(fecha_pedido_Ac);
                    ds.getRef().child("estado").setValue(estado_Ac);
                    ds.getRef().child("forma_entrega").setValue(forma_entrega_Ac);
                }
                Toast.makeText(Actualizar_Pedidos.this,"Pedido actualizado",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}