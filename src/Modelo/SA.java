
package Modelo;

import Controlador.CPrincipal;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;

public class SA {
    CPrincipal control;
    Solucion solucion;
    Solucion mejor;
    int temp, tempFinal, tempInicial;
    double porcEnfriamiento;
    Random seed;
    int random;
    List costo;
    
    public SA(CPrincipal cp) {
        this.control = cp;
        this.solucion = new Solucion(cp);
    }


    /**
     * Ejecuta el proceso principal de Simulated Annealing
     */
    public void run() {
        Solucion vecino = new Solucion(this.control);
        this.temp = this.tempInicial;
        int N, n = 0;
        double fvec, fact;
        
        this.solucion.generar(); //SE GENERA LA SOLUCION INICIAL

        System.out.println("Costo Inicial: "+this.solucion.evaluar());
        System.out.println("T: "+this.temp);
        while(this.temp>this.tempFinal) {
            this.costo = new LinkedList();
            N = this.N(this.temp);
            n = 0;
            while(n<N) {
                vecino = this.solucion.genVecino();
                this.mejor = new Solucion(this.control);
                fact = this.solucion.evaluar();
                fvec = vecino.evaluar();
                this.mejor = this.solucion;
                if(fvec < fact) {
                    this.mejor = vecino;
                    this.solucion = vecino;
                    this.costo.add(fvec);
                } else {
                    seed = new Random();
                    if(seed.nextDouble() < Math.exp(-(Math.abs(fact-fvec)/this.temp))) {
                        this.solucion = vecino;
                        this.costo.add(fvec);
                    } else {
                        this.costo.add(fact);
                    }
                }
                n++;
            }
            this.control.grabar(this.temp, this.costo);
            this.temp=(int)(this.porcEnfriamiento*this.temp);
            System.out.println("T: "+this.temp);
        }
        System.out.println("Costo Final: "+this.solucion.evaluar());
        System.out.println("Costo Menor: "+this.mejor.evaluar());
    }

    /**
     * Calcula N segun la temperatura
     * @param t temperatura
     * @return N
     */
    public int N(int t) {
        int grupos = 10;
        int factor = 3;
        int gr = (int)(this.tempInicial-this.tempFinal)/grupos;
        int r = (int)((this.tempInicial-t)/gr+1)*factor;
        return r;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTempFinal() {
        return tempFinal;
    }

    public void setTempFinal(int tempFinal) {
        this.tempFinal = tempFinal;
    }

    public double getPorcEnfriamiento() {
        return porcEnfriamiento;
    }

    public void setPorcEnfriamiento(double porcEnfriamiento) {
        this.porcEnfriamiento = porcEnfriamiento;
    }

    public int getTempInicial() {
        return tempInicial;
    }

    public void setTempInicial(int tempInicial) {
        this.tempInicial = tempInicial;
    }
    
}
