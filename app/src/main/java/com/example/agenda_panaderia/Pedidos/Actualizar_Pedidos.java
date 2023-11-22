package com.example.agenda_panaderia.Pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.agenda_panaderia.R;

public class Actualizar_Pedidos extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    TextView Id_nota_A, Uid_Usuario_A, Fecha_registro_A, Estado_A, Fecha_A, Estado_nuevo, Estado_nuevo2;
    EditText Titulo_A,Descripcion_A;
    Button Btn_Calendario_A;

    Spinner Spinner_estado, Spinner_estado_2;
    String id_pedido_R, uid_usuario_R, fecha_registro_R, estado_P_R, fecha_R,estado_n_R, titulo_R,descripcion_R,tipo_pedido_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pedidos);
        InicializarVistas();
        RecuperarDatos();
        SetearDatos();
        Spinner_Estado();
        Spinner_Pedido();
    }
    private void InicializarVistas(){
        Id_nota_A =findViewById(R.id.Id_pedido_A);
        Uid_Usuario_A=findViewById(R.id.Uid_Usuario_A);
        Estado_A=findViewById(R.id.Estado_A);
        Fecha_A=findViewById(R.id.Fecha_A);
        Estado_nuevo=findViewById(R.id.Estado_nuevo);
        Titulo_A=findViewById(R.id.Titulo_A);
        Descripcion_A=findViewById(R.id.Descripcion_A);
        Btn_Calendario_A=findViewById(R.id.Btn_Calendario_A);
        Spinner_estado_2=findViewById(R.id.Spinner_estado_2);
        Spinner_estado=findViewById(R.id.Spinner_estado);
        Fecha_registro_A=findViewById(R.id.Fecha_registro_A);
        Estado_nuevo2=findViewById(R.id.Estado_nuevo2);
    }
    private void RecuperarDatos(){
        Bundle intent= getIntent().getExtras();
        id_pedido_R= intent.getString("id_pedido");
        uid_usuario_R= intent.getString("uid_usuario");
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



    }
    private void Spinner_Estado(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Estados_pedido, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_estado.setAdapter(adapter);
        Spinner_estado.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String ESTADO_ACTUAL = Estado_A.getText().toString();


        String Posicion_1 = adapterView.getItemAtPosition(1).toString();

        String estado_seleccionado = adapterView.getItemAtPosition(i).toString();
        Estado_nuevo.setText(estado_seleccionado);

        if (ESTADO_ACTUAL.equals("Entregado")){
            Estado_nuevo.setText(Posicion_1);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void Spinner_Pedido(){

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.Estados_pedido2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_estado_2.setAdapter(adapter2);
        Spinner_estado_2.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String Estado_Pedido= Estado_nuevo2.getText().toString();
        String Posicion_2= adapterView.getItemAtPosition(2).toString();
        String estado_seleccionado2= adapterView.getItemAtPosition(position).toString();
        Estado_nuevo2.setText(estado_seleccionado2);
        if (Estado_Pedido.equals("Local")){
            Estado_nuevo2.setText(Posicion_2);

        }
    }
}