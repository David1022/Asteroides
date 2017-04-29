package com.david.asteroides;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by david on 2/3/17.
 */

public class Grafico {

    private Drawable drawable;   //Imagen que dibujaremos
    private double cenX, cenY;   //Posición
    private double incX, incY;   //Velocidad desplazamiento
    private int angulo, rotacion;//Ángulo y velocidad rotación
    private int ancho, alto;     //Dimensiones de la imagen
    private int radioColision;   //Para determinar colisión
    //Donde dibujamos el gráfico (usada en view.ivalidate)
    private View view;
    // Para determinar el espacio a borrar (view.ivalidate)
    public static final int MAX_VELOCIDAD = 20;

    public Grafico(View view, Drawable drawable){

        this.view = view;
        this.drawable = drawable;
        ancho = drawable.getIntrinsicWidth();
        alto = drawable.getIntrinsicHeight();
        radioColision = (alto+ancho)/4;

    }
    public void dibujaGrafico(Canvas canvas){

        int x=(int) (cenX - ancho/2);
        int y=(int) (cenY - alto/2);
        drawable.setBounds((int)cenX, (int)cenY,
                (int)cenX+ancho, (int)cenY+alto);
        canvas.save();
        canvas.rotate((float) angulo,(float) cenX,(float) cenY);
        drawable.draw(canvas);
        canvas.restore();
        int rInval = (int) Math.hypot(ancho,alto)/2 + MAX_VELOCIDAD;
        view.invalidate(x-rInval, y-rInval, x+rInval, y+rInval);

    }
    public void incrementaPos(double factor){

        cenX += incX * factor;
        // Si salimos de la pantalla, corregimos posición
        if(cenX<-ancho/2) {cenX=view.getWidth()-ancho/2;}
        if(cenX>view.getWidth()-ancho/2) {cenX=-ancho/2;}

        cenY += incY * factor;
        if(cenY<-alto/2) {cenY=view.getHeight()-alto/2;}
        if(cenY>view.getHeight()-alto/2) {cenY=-alto/2;}

        angulo += rotacion * factor; //Actualizamos ángulo

    }
    public double distancia(Grafico g) {

        return Math.hypot(cenX-g.cenX, cenY-g.cenY);

    }
    public boolean verificaColision(Grafico g) {

        return(distancia(g) < (radioColision+g.radioColision));

    }

    public Drawable getDrawable() {

        return drawable;

    }

    public double getCenX() {

        return cenX;

    }

    public double getCenY() {

        return cenY;

    }

    public double getIncX() {

        return incX;

    }

    public double getIncY() {

        return incY;

    }

    public double getAngulo() {

        return angulo;

    }

    public double getRotacion() {

        return rotacion;

    }

    public int getAncho() {

        return ancho;

    }

    public int getAlto() {

        return alto;

    }

    public int getRadioColision() {

        return radioColision;

    }

    public void setDrawable(Drawable drawable) {

        this.drawable = drawable;

    }

    public void setCenX(double cenX) {

        this.cenX = cenX;

    }

    public void setCenY(double cenY) {

        this.cenY = cenY;

    }

    public void setIncX(double incX) {

        this.incX = incX;

    }

    public void setIncY(double incY) {

        this.incY = incY;

    }

    public void setAngulo(int angulo) {

        this.angulo = angulo;

    }

    public void setRotacion(int rotacion) {

        this.rotacion = rotacion;

    }

    public void setAncho(int ancho) {

        this.ancho = ancho;

    }

    public void setAlto(int alto) {

        this.alto = alto;

    }

    public void setRadioColision(int radioColision) {

        this.radioColision = radioColision;

    }

}
