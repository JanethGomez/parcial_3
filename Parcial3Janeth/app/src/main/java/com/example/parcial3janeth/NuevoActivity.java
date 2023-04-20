package com.example.parcial3janeth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial3janeth.dao.Contactos;
import com.example.parcial3janeth.dao.ContactosDao;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtTelefono, txtCorreoElectronico;
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {

                    ContactosDao dbContactos = new ContactosDao(NuevoActivity.this);
                    Contactos c = new Contactos();
                    c.setNombres(txtNombre.getText().toString());
                    c.setApellidos(txtApellido.getText().toString());
                    c.setTelefono(txtTelefono.getText().toString());
                    c.setCorreo_electornico(txtCorreoElectronico.getText().toString());
                    long id = dbContactos.insertarContacto(c);

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }
}