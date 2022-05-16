package com.example.intentimplicito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
/*
    los explicitos se indica qu componente se quiere que realice la accion
    para que lanse una nueva pantalla, el sistema escoje cual es la mejor alternativa
    para realizar la accion
    

*/