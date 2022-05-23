package com.example.intentimplicito;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Definir los atributos de sus vistas
    private EditText etTelefono;
    private ImageButton btnLlamar, btnCamara;
    private ImageView ivImage;

    //Atributo de tipos primitivos
    private String numeroTelefono;
    // Atributos que represantan codigos del servicio del celular
    private final int PHONE_CODE=100;
    private final int CAMERA_CODE =50;
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
            activarCamara();
        });
    }

    private void activarCamara() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new  String[]{Manifest.permission.CAMERA},CAMERA_CODE);
        }
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
                // versiones superiores a ala 23 se usan funciones de las librerias android
                // e incorporan un asunto llamado asincronia
                // asincrono: no es necesario que una linea responda para que funcione el codigo es decir
                // el codigo seguira ejecutando aunque una linea no responda.
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CODE);
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
        if(revisarPermisos(Manifest.permission.CALL_PHONE)){

            Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ numeroTelefono));
            startActivity(intentLlamada);
        }else{
            Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerInformacion() {
        numeroTelefono = etTelefono.getText().toString();
    }

    private void inicializarVistas() {
        etTelefono = findViewById(R.id.etTelefono);
        btnCamara = findViewById(R.id.btnCamara);
        btnLlamar = findViewById(R.id.btnLlamar);
        ivImage = findViewById(R.id.ivImage);
    }
    private boolean revisarPermisos(String permiso){
        //Android maneja los permisos de esta manera:
        //GRANTED: permiso otorgado valor = 0
        //DENIED: permiso no otorgado valor = -1
        //Validar si el permiso a evaluar en su aplicacion tiene el valor que Android maneja para
        // un permiso otorgado (GRANTED)
        int resultadoPermiso = checkCallingOrSelfPermission(permiso);
        return resultadoPermiso == PackageManager.PERMISSION_GRANTED; // devuelve true o false

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PHONE_CODE:
                String permiso = permissions[0];
                int valorPermiso = grantResults[0];
                //Para evitar que hayan errores humanos mas que nada
                if (permiso.equals(Manifest.permission.CALL_PHONE)){
                    //Validar si se tiene el permiso o no
                    if (valorPermiso == PackageManager.PERMISSION_GRANTED){
                        Intent intentLlamada = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + numeroTelefono));
                        startActivity(intentLlamada);
                    }
                }
                break;
            case CAMERA_CODE:
                int valor = grantResults[0];
                if (valor == PackageManager.PERMISSION_GRANTED){
                    Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intentCamara, CAMERA_CODE );
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case CAMERA_CODE:
                if(resultCode == RESULT_OK){
                    //Para recibir imagenes generalmente se trabaja en formato de mapa de bits
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    ivImage.setImageBitmap(foto);
                }

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
/*
    los explicitos se indica qu componente se quiere que realice la accion
    para que lanse una nueva pantalla, el sistema escoje cual es la mejor alternativa
    para realizar la accion-


*/