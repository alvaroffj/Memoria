package Modelo;

import Controlador.CPrincipal;
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
        List ch = p.choferes;
        List bu = p.buses;
        int nRe = re.size();
        
        this.plan = new Dia[7];
        for(int i=0; i<7; i++) {
            Dia dAux = new Dia();
            for(int j=0; j<nRe; j++) {
                String[] aux = (String[]) re.get(j);
                Recorrido rAux = new Recorrido(j);
                String[] pu = new String[2];
                String[] ho = new String[2];
                
                pu[0] = aux[1];
                pu[1] = aux[2];
                
                ho[0] = aux[3];
                ho[1] = aux[4];
                
                rAux.setPuntos(pu);
                rAux.setHorario(ho);
                dAux.addRecorrido(rAux);
            }
            plan[i] = dAux;
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
