package com.example.agenda_panaderia.Contactos;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.agenda_panaderia.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Actualizar_Contactos extends AppCompatActivity {
    TextView Id_C_A, Uid_C_A, Telefono_C_A;
    EditText Nombres_C_A, Apellidos_C_A, Correo_C_A, Direccion_C_A;
    ImageView Imagen_C_A, Actualizar_imagen_C_A, Actualizar_Telefono_C_A, regresar;
    Button Btn_Actualizar_C_A;
    String id_c , uid_usuario, nombres_c, apellidos_c, correo_c, telefono_c, direccion_c;
    Dialog dialog_establecer_telefono;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Uri imagenUri;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contactos);
        regresar = findViewById(R.id.regresar);


        InicializarVistas();
        RecuperarDatos();
        SetearDatosRecuperados();
        ObtenerImagen();
        Actualizar_Telefono_C_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Establecer_telefono_contacto();
            }
        });
        Actualizar_Telefono_C_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Establecer_telefono_contacto();
            }
        });
        Btn_Actualizar_C_A.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Actualizar_Contactos.this, Listar_Contactos.class);
                startActivity(intent);
                ActualizarInformacionContacto();

                //Toast.makeText(Menu_Principal.this,"Contactos", Toast.LENGTH_SHORT).show();
                //uno dos tres
            }

        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Actualizar_Contactos.this, Listar_Contactos.class);
                startActivity(intent);
            }
        });

        Actualizar_imagen_C_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  SeleccionarImagenGaleria();
                }

        });
        progressDialog = new ProgressDialog(Actualizar_Contactos.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);


    }

    private void SeleccionarImagenGaleria() {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galeriaActivityResultLauncher.launch(intent);
        }

        private ActivityResultLauncher<Intent> galeriaActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imagenUri = data.getData();
                            Imagen_C_A.setImageURI(imagenUri);
                            subirImagenStorage();
                        }else {
                            Toast.makeText(Actualizar_Contactos.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    private ActivityResultLauncher<String> SolicitarPermisoGaleria = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted){
                    SeleccionarImagenGaleria();
                }else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );


    private void InicializarVistas(){
        Id_C_A = findViewById(R.id.Id_C_A);
        Uid_C_A = findViewById(R.id.Uid_C_A);
        Telefono_C_A = findViewById(R.id.Telefono_C_A);
        Nombres_C_A = findViewById(R.id.Nombres_C_A);
        Apellidos_C_A = findViewById(R.id.Apellidos_C_A);
        Correo_C_A = findViewById(R.id.Correo_C_A);
        Direccion_C_A = findViewById(R.id.Direccion_C_A);
        Imagen_C_A = findViewById(R.id.Imagen_C_A);
        Actualizar_imagen_C_A = findViewById(R.id.Actualizar_imagen_C_A);
        Actualizar_Telefono_C_A = findViewById(R.id.Actualizar_Telefono_C_A);
        Btn_Actualizar_C_A = findViewById(R.id.Btn_Actualizar_C_A);
        dialog_establecer_telefono = new Dialog(Actualizar_Contactos.this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }
    private void RecuperarDatos(){
        Bundle bundle = getIntent().getExtras();
        id_c = bundle.getString("id_c");
        uid_usuario = bundle.getString("uid_usuario");
        nombres_c = bundle.getString("Nombre_c");
        apellidos_c = bundle.getString("Apellidos_c");
        correo_c = bundle.getString("Correo_c");
        telefono_c = bundle.getString("Telefono_c");
        direccion_c = bundle.getString("Direccion_C_A");
    }
    private void SetearDatosRecuperados(){
        Id_C_A.setText(id_c);
        Uid_C_A.setText(uid_usuario);
        Nombres_C_A.setText(nombres_c);
        Apellidos_C_A.setText(apellidos_c);
        Correo_C_A.setText(correo_c);
        Telefono_C_A.setText(telefono_c);
        Direccion_C_A.setText(direccion_c);
    }

    private void Establecer_telefono_contacto(){
        CountryCodePicker ccp;
        EditText Establecer_Telefono;
        Button Btn_Aceptar_Telefono;

        dialog_establecer_telefono.setContentView(R.layout.cuadro_dialogo_establecer_telefono);

        ccp = dialog_establecer_telefono.findViewById(R.id.ccp);
        Establecer_Telefono = dialog_establecer_telefono.findViewById(R.id.Establecer_Telefono);
        Btn_Aceptar_Telefono = dialog_establecer_telefono.findViewById(R.id.Btn_Aceptar_Telefono);

        Btn_Aceptar_Telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo_pais = ccp.getSelectedCountryCodeWithPlus();
                String telefono = Establecer_Telefono.getText().toString();
                String codigo_pais_telefono = codigo_pais+telefono;

                if (!telefono.equals("")){
                    Telefono_C_A.setText(codigo_pais_telefono);
                    dialog_establecer_telefono.dismiss();
                }else {
                    Toast.makeText(Actualizar_Contactos.this, "Ingrese un número telefónico", Toast.LENGTH_SHORT).show();
                    dialog_establecer_telefono.dismiss();
                }
            }
        });

        dialog_establecer_telefono.show();
        dialog_establecer_telefono.setCanceledOnTouchOutside(true);
    }

    private void ActualizarInformacionContacto(){
        String NombresActualizar = Nombres_C_A.getText().toString().trim();
        String ApellidosActualizar = Apellidos_C_A.getText().toString().trim();
        String CorreoActualizar = Correo_C_A.getText().toString().trim();
        String TelefonoActualizar = Telefono_C_A.getText().toString().trim();
        String DireccionActualizar = Direccion_C_A.getText().toString().trim();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
DatabaseReference databaseReference =  firebaseDatabase.getReference("Usuarios");

        Query query = databaseReference.child(user.getUid()).child("Contactos").orderByChild("id_contacto").equalTo(id_c);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ds.getRef().child("nombres").setValue(NombresActualizar);
                    ds.getRef().child("apellidos").setValue(ApellidosActualizar);
                    ds.getRef().child("correo").setValue(CorreoActualizar);
                    ds.getRef().child("telefono").setValue(TelefonoActualizar);
                    ds.getRef().child("direccion").setValue(DireccionActualizar);
                }

                Toast.makeText(Actualizar_Contactos.this, "Información actualizada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ActualizarImagenBD(String uriIMAGEN) {

        progressDialog.setMessage("Actualizando la imagen");
        progressDialog.show();

        String id_c = getIntent().getStringExtra("id_c");

        HashMap<String, Object> hashMap = new HashMap<>();
        if (imagenUri != null){
            hashMap.put("imagen", ""+uriIMAGEN);
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(user.getUid()).child("Contactos").child(id_c)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Actualizar_Contactos.this, "Imagen actualizada con éxito", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Actualizar_Contactos.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void ObtenerImagen(){
        String imagen = getIntent().getStringExtra("Imagen_c");

        try {

            Glide.with(getApplicationContext()).load(imagen).placeholder(R.drawable.contacto).into( Imagen_C_A);

        }catch (Exception e){

            Toast.makeText(this, "Esperando imagen", Toast.LENGTH_SHORT).show();
        }
    }
    private void subirImagenStorage() {
        progressDialog.setMessage("Subiendo imagen");
        progressDialog.show();
        String id_c = getIntent().getStringExtra("id_c");

        String carpetaImagenesContactos = "ImagenesPerfilContactos/";
        String NombreImagen = carpetaImagenesContactos + id_c;
        StorageReference reference = FirebaseStorage.getInstance().getReference(NombreImagen);
        reference.putFile(imagenUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        String UriIMAGEN = "" + uriTask.getResult();
                        ActualizarImagenBD(UriIMAGEN);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Actualizar_Contactos.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}