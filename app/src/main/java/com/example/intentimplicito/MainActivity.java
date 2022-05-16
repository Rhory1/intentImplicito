package com.example.intentimplicito;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    //Definir los atributos de sus vistas
    private EditText etTelefono;
    private ImageButton btnLlamar, btnCamara;

    //Atributo de tipos primitivos
    private String numeroTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarVistas();
        btnLlamar.setOnClickListener(view -> {
            obtenerInformacion();
            configurarIntentImplicito();

        });
        btnCamara.setOnClickListener(view -> {
        });
    }


    private void configurarIntentImplicito() {
        //Primero validamos si el campo de texto no esta vacio
        if(!numeroTelefono.isEmpty()){
            // Primer problema
            // las llamadas han cambiado desde la version 6 o sdk23
            // a partir de esa version se hace el codigo con algunos cambios
            // antes de esa version tenia otra manera de hacer el codigo

            // Validar si la version de tu proyecto es mayor o igual a la version de android donde
            // cambio la forma de procesar
            // Ej: SDK_INT = 24(Nugat) Ej. VERSION_CODES.M =23
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Para versiones nuevas
            }else {
                //Para versiones antiguas
                configurarVersionesAntiguas();
            }
        }
    }

    private void configurarVersionesAntiguas() {
        // Configurar el intent implicito para versiones anteriores a la version
        // donde cambio el codigo

        // Intent implicito se debe configurar 2 cosas
        //1- que accion quieren realizar
        //2- que datos quieres enviar en el intent

        //URI es como el URL de web donde configuras las cabeceras de
        // tu ruta donde quieres pasar datos
        Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ numeroTelefono));
        startActivity(intentLlamada);
    }

    private void obtenerInformacion() {
        numeroTelefono = etTelefono.getText().toString();
    }

    private void inicializarVistas() {
        etTelefono = findViewById(R.id.etTelefono);
        btnCamara = findViewById(R.id.btnCamara);
        btnLlamar = findViewById(R.id.btnLlamar);
    }
    private boolean revisarPermisos(String permiso){
        //Android maneja los permisos de esta manera:
        //GRANTED: permiso otorgado
        //DENIED: permiso no otorgado
        //Validar si el permiso a evaluar en su aplicacion tiene el valor que Android maneja para
        // un permiso otorgado (GRANTED)
        return false;
    }
}
/*
    los explicitos se indica qu componente se quiere que realice la accion
    para que lanse una nueva pantalla, el sistema escoje cual es la mejor alternativa
    para realizar la accion-


*/