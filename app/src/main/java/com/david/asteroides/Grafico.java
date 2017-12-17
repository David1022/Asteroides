package com.david.asteroides;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by david on 2/3/17.
 */

public class Grafico {

    private Drawable drawable;   //Imagen que dibujaremos
    private double posX, posY;   //Posición
    private double incX, incY;   //Velocidad desplazamiento
    private int angulo, rotacion;//Ángulo y velocidad rotación
    private int ancho, alto;     //Dimensiones de la imagen

    private int radioColision;   //Para determinar colisión
    //Donde dibujamos el gráfico (usada en view.ivalidate)
    private View view;

    // Para determinar el espacio a borrar (view.ivalidate)
    public static final int MAX_VELOCIDAD = 20;


    public Grafico(View view, Drawable drawable) {

        this.view = view;
        this.drawable = drawable;
        ancho = drawable.getIntrinsicWidth();
        alto = drawable.getIntrinsicHeight();
        radioColision = (alto + ancho) / 4;

    }

    public void dibujaGrafico(Canvas canvas) {

        int x = (int) (posX + ancho / 2);
        int y = (int) (posY + alto / 2);
        drawable.setBounds((int) posX, (int) posY,
                (int) posX + ancho, (int) posY + alto);
        canvas.save();
        canvas.rotate((float) angulo, (float) x, (float) y);
        drawable.draw(canvas);
        canvas.restore();
        int rInval = (int) Math.hypot(ancho, alto) / 2 + MAX_VELOCIDAD;
        view.invalidate(x - rInval, y - rInval, x + rInval, y + rInval);

    }

    public void incrementaPos(double factor) {

        posX += incX * factor;
        // Si salimos de la pantalla, corregimos posición
        if (posX < -ancho / 2) {
            posX = view.getWidth() - ancho / 2;
        }
        if (posX > view.getWidth() - ancho / 2) {
            posX = -ancho / 2;
        }

        posY += incY * factor;
        if (posY < -alto / 2) {
            posY = view.getHeight() - alto / 2;
        }
        if (posY > view.getHeight() - alto / 2) {
            posY = -alto / 2;
        }

        angulo += rotacion * factor; //Actualizamos ángulo

    }

    public double distancia(Grafico g) {

        return Math.hypot(posX - g.posX, posY - g.posY);

    }

    public boolean verificaColision(Grafico g) {

        return (distancia(g) < (radioColision + g.radioColision));

    }

    public Drawable getDrawable() {

        return drawable;

    }

    public double getPosX() {

        return posX;

    }

    public double getPosY() {

        return posY;

    }

    public double getIncX() {

        return incX;

    }

    public double getIncY() {

        return incY;

    }

    public int getAngulo() {

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

    public void setPosX(double posX) {

        this.posX = posX;

    }

    public void setPosY(double posY) {

        this.posY = posY;

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
