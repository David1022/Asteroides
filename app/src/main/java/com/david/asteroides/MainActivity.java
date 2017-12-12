package com.david.asteroides;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView texto = (TextView) findViewById(R.id.textView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tweenanimation);

        Button bJugar = (Button) findViewById(R.id.botonJugar);
        bJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirJugar();
            }
        });
        Button bConfiguracion = (Button) findViewById(R.id.botonConfiguracion);
        bConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirConfiguracion();
            }
        });
        Button bAcercaDe = (Button) findViewById(R.id.botonAcercaDe);
        bAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAcercaDe();
            }
        });
        Button bSalir = (Button) findViewById(R.id.botonSalir);
        bSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();
            }
        });

        texto.startAnimation(animation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        if (id == R.id.preferencias) {
            abrirConfiguracion();
            return true;
        }
        if (id == R.id.acercaDe) {
            abrirAcercaDe();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void abrirJugar() {

        Intent intent = new Intent(this, Juego.class);
        startActivity(intent);

    }

    public void abrirConfiguracion(){

        Intent intent = new Intent(this, Preferencias.class);
        startActivity(intent);

    }

    public void abrirAcercaDe(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(R.string.acercaDe);
        dialogo.setMessage(R.string.textoAcercaDe);
        dialogo.setCancelable(true);
        dialogo.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                dialogo.cancel();
            }
        });
        dialogo.show();

    }

    public void salir(){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(R.string.salir);
        dialogo1.setMessage(R.string.confirmacionSalir);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });
        dialogo1.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });
        dialogo1.show();

    }

}
