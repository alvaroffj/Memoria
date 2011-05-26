package Modelo;

import Controlador.CPrincipal;
import Problema.Chofer;
import Problema.Dia;
import Problema.Problema;
import Problema.Recorrido;
import java.util.Random;
import java.util.List;



/**
 * 
 * @author Alvaro
 */
public class Solucion {
    CPrincipal control;
    Dia[] plan;
    Double costo;

    public Solucion(CPrincipal cp) {
        this.control = cp;
    }

    /*
     * Genera la primera solucion al problema
     */
    public void generar() {
        System.out.println("Solucion: Generar INI");
        Problema p = this.control.getProblema();
        List re = p.recorridos;
        List<Chofer>ch = p.choferes;
        List bu = p.buses;
        int nRe = re.size();
        Chofer cAux;
        int[] chAux;
        this.plan = new Dia[7];
        for(int i=0; i<7; i++) {
            Dia dAux = new Dia();
            Recorrido rAux = new Recorrido();
            for(int j=0; j<nRe; j++) {
                int[] r = new int[3];
                rAux = (Recorrido)re.get(j);
                r[0] = rAux.getId();
                chAux = p.findChoferLibre(i, rAux);
                if(chAux[0]>=0) {
                    cAux = ch.get(chAux[0]);
                    r[1] = cAux.getId();
                    cAux.addRecorrido(i, chAux[2], rAux, chAux[1]);
                    dAux.addViaje(r);
                }
                //TODO asignar bus
            }
            this.plan[i] = dAux;
        }
        System.out.println("Solucion: Generar FIN");
    }

    /*
     * Evalua la solucion actual
     */
    public double evaluar() {
        return 0.0;
    }

    /*
     * Genera un nuevo vecino en base a la solucion actual
     */
    public Solucion genVecino() {        
        Solucion r = new Solucion(this.control);
        Random seed;

        return r;
    }
}
