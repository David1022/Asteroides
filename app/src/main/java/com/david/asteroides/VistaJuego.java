package com.david.asteroides;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.Vector;

/**
 * Created by david on 2/3/17.
 */

public class VistaJuego extends View implements SensorEventListener {

    //************  ASTEROIDES  ************
    private Vector <Grafico> asteroides;      // Vector con los asteroides
    private int numAsteroides = 5; // Numero inicial de asteroides
    private int numFragmentos = 3;  // Fragmentos en los que se divide

    //************  NAVE  ************
    private Grafico nave;           // Grafico de la nave
    private int giroNave;           // Incremento de direccion
    private float aceleracionNave;  // aumento de velocidad
    // Incremento standar de giro y aceleracion
    private static final int MAX_VELOCIDAD_NAVE = 0;
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;

    //************  THREAD Y TIEMPO  ************
    // Thread encargado de procesar el juego
    private ThreadJuego thread = new ThreadJuego();
    // Cada cuanto queremos procesar cambios (ms)
    private static int PERIODO_PROCESO = 50;
    // Cuando se realizó el último proceso
    private long ultimoProceso = 0;

    private float mX = 0, mY = 0;
    private boolean disparo = false;

    private Drawable drawableNave;
    private Drawable drawableAsteroide;
    private Drawable drawableMisil;


    private boolean hayValorInicial = false;
    private float valorInicial;

    public VistaJuego(Context context, AttributeSet attrs) {

        super(context,attrs);
        SharedPreferences pref = context.getSharedPreferences("com.david.asteroides", Context.MODE_PRIVATE);
        if (pref.getString("grafivos", "0").equals("0")) {
            dibujaVectorial();
        } else{
            drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
        }
        drawableNave = context.getResources().getDrawable(R.drawable.nave);
        nave = new Grafico(this, drawableNave);
        asteroides = new Vector <Grafico> ();
        for (int i=0; i<numAsteroides; i++){

        // Se dibujan los gráficos (asteroides y nave)
        Drawable drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
        Drawable drawableNave = context.getResources().getDrawable(R.drawable.nave);
        asteroides = new Vector<Grafico>();
        nave = new Grafico(this, drawableNave);

        for (int i = 0; i < numAsteroides; i++) {
            Grafico asteroide = new Grafico(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }

        // Se registra el sensor de orientación
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (listSensors.isEmpty()) {
            Sensor orientationSensor = listSensors.get(0);
            mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onSizeChanged(int ancho, int alto, int ancho_anter, int alto_anter) {
        super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);

        // Una vez que conocemos nuestro ancho y alto.
        nave.setCenX(ancho / 2);
        nave.setCenY(alto / 2);

        for (Grafico asteroide : asteroides) {
            do {
                asteroide.setCenX(Math.random() * ancho);
                asteroide.setCenY(Math.random() * alto);
            } while (asteroide.distancia(nave) < (ancho + alto) / 5);
        }

        ultimoProceso = System.currentTimeMillis();
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Grafico asteroide : asteroides) {
            asteroide.dibujaGrafico(canvas);
        }
        nave.dibujaGrafico(canvas);
    }

    private void dibujaVectorial(){
        // Draw asteroide
        Path pathAsteroide = new Path();
        pathAsteroide.moveTo((float) 0.3, (float) 0.0);
        pathAsteroide.lineTo((float) 0.6, (float) 0.0);
        pathAsteroide.lineTo((float) 0.6, (float) 0.3);
        pathAsteroide.lineTo((float) 0.8, (float) 0.2);
        pathAsteroide.lineTo((float) 1.0, (float) 0.4);
        pathAsteroide.lineTo((float) 0.8, (float) 0.6);
        pathAsteroide.lineTo((float) 0.9, (float) 0.9);
        pathAsteroide.lineTo((float) 0.8, (float) 1.0);
        pathAsteroide.lineTo((float) 0.4, (float) 1.0);
        pathAsteroide.lineTo((float) 0.0, (float) 0.6);
        pathAsteroide.lineTo((float) 0.0, (float) 0.2);
        pathAsteroide.lineTo((float) 0.3, (float) 0.0);
        ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(pathAsteroide,1, 1));
        dAsteroide.getPaint().setColor(Color.WHITE);
        dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
        dAsteroide.setIntrinsicWidth(50);
        dAsteroide.setIntrinsicHeight(50);
        drawableAsteroide = dAsteroide;
        setBackgroundColor(Color.BLACK);
        // Draw ship
        Path pathNave = new Path();
        pathNave.moveTo((float) 0.0, (float) 0.0);
        pathNave.lineTo((float) 0.0, (float) 1.0);
        pathNave.lineTo((float) 1.0, (float) 0.5);
        pathNave.lineTo((float) 0.0, (float) 0.0);
        ShapeDrawable dNave = new ShapeDrawable(new PathShape(pathNave,1, 1));
        dNave.getPaint().setColor(Color.WHITE);
        dNave.getPaint().setStyle(Paint.Style.STROKE);
        dNave.setIntrinsicWidth(50);
        dNave.setIntrinsicHeight(50);
        drawableNave = dNave;

    }

    public void actualizaFisica(){

        long ahora=System.currentTimeMillis();
    public void actualizaFisica() {
        long ahora = System.currentTimeMillis();

        //Si el periodo de proceso no se ha cumplido, no hacemos nada
        if (ultimoProceso + PERIODO_PROCESO > ahora) {
            return;
        }

        // Para una ejecución en tiempo real calculamos retardo
        double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
        ultimoProceso = ahora; // Para la próxima vez

        // Actualizamos velocidad y dirección de la nave a partir de
        // giroNave y aceleracionNave (según la entrada del jugador)
        nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));
        double nIncX = nave.getIncX() + aceleracionNave
                * Math.cos(Math.toRadians(nave.getAngulo())) * retardo;
        double nIncY = nave.getIncY() + aceleracionNave
                * Math.sin(Math.toRadians(nave.getAngulo())) * retardo;

        // Actualizamos si el módulo de la velocidad no excede el máximo
        if (Math.hypot(nIncX, nIncY) <= MAX_VELOCIDAD_NAVE) {
            nave.setIncX(nIncX);
            nave.setIncY(nIncY);
        }

        // Actualizamos posiciones X e Y
        nave.incrementaPos(retardo);
        for (Grafico asteroide : asteroides) {
            asteroide.incrementaPos(retardo);
        }


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float valor = sensorEvent.values[1];
        if(!hayValorInicial){
            valorInicial = valor;
            hayValorInicial = true;
        }
        giroNave = (int) (valor - valorInicial) / 3;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class ThreadJuego extends Thread {
        @Override
        public void run() {
            while (true) {
                actualizaFisica();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                disparo = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy < 6 && dx > 6) {
                    giroNave = Math.round((x - mX) / 2);
                    disparo = false;
                } else if (dx < 6 && dy > 6) {
                    aceleracionNave = Math.round((mY - y) / 25);
                    disparo = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                giroNave = 0;
                aceleracionNave = 0;
                if (disparo){
//                    activaMisil();
                }
                break;
        }
        mX = x;
        mY = y;
        return true;
    }



}