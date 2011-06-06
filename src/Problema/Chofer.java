
package Problema;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Chofer {
    String nombre;
    int id;
    String ini;
    int[] diasLibres;
    List[] plan;
    int[] totalMinDia;

    public Chofer() {}
    
    public Chofer(int id, int nDiasLibres) {
        this.id = id;
        this.diasLibres = new int[nDiasLibres];
        this.plan = new LinkedList[7];
        this.totalMinDia = new int[7];
        for(int i=0; i<7; i++) {
            this.totalMinDia[i] = 0;
            this.plan[i] = new LinkedList();
        }
    }

    public void setIni(String ini) {
        this.ini = ini;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void addRecorrido(int dia, int pos, Recorrido rec, int idDT) {
        this.totalMinDia[dia] += rec.getTiempo();
        int[] aux = new int[2];
        aux[0] = idDT;
        aux[1] = rec.getId();
        //TODO revisar que pasa cuando se inserta antes de un recorrido, actualizar deadTrip
        this.plan[dia].add(pos, aux);
    }
    
    public List getPlanDia(int dia) {
        return this.plan[dia];
    }

    public int[] getDiasLibres() {
        return diasLibres;
    }

    public int getId() {
        return id;
    }

    public String getIni() {
        return ini;
    }

    public String getNombre() {
        return nombre;
    }

    public List[] getPlan() {
        return plan;
    }
    
    public int getTotalMinDia(int dia) {
        return this.totalMinDia[dia];
    }
    
    public int[] getRecorrido(int idRec, int dia) {
        List<int[]>dAux = this.plan[dia];
        int encontrado = 0;
        int nRec = dAux.size();
        int i=0;
        int[] rAux = new int[2];
        while(encontrado < 1 && i<nRec) {
            rAux = dAux.get(i);
            if(rAux[1] == idRec) {
                encontrado = 1;
            } else {
                i++;
            }
        }
        return rAux;
    }
}
