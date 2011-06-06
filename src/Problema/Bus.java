package Problema;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Bus {
    int id;
    String nombre;
    String patente;
    String deposito;
    List[] plan;

    public Bus() {
    }

    public Bus(int id) {
        this.id = id;
        this.plan = new LinkedList[7];
        for(int i=0; i<7; i++) {
            this.plan[i] = new LinkedList();
        }
    }

    public Bus(int id, String nombre, String patente, String deposito) {
        this.id = id;
        this.nombre = nombre;
        this.patente = patente;
        this.deposito = deposito;
        this.plan = new LinkedList[7];
        for(int i=0; i<7; i++) {
            this.plan[i] = new LinkedList();
        }
    }

    public String getDeposito() {
        return deposito;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPatente() {
        return patente;
    }
    
    public List getPlanDia(int dia) {
        return this.plan[dia];
    }
    
    public void addRecorrido(int dia, int pos, Recorrido rec, int idDT) {
        int[] aux = new int[2];
        aux[0] = idDT;
        aux[1] = rec.getId();
        //TODO revisar que pasa cuando se inserta antes de un recorrido, actualizar deadTrip
        this.plan[dia].add(pos, aux);
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
