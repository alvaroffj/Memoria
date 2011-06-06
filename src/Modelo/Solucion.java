package Modelo;

import Controlador.CPrincipal;
import Problema.Bus;
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
    int costoChoferBus;
    int costoDT;
    int minChofer;
    int minBus;
    int minChoferDeadTrip;
    int minBusDeadTrip;

    public Solucion(CPrincipal cp) {
        this.control = cp;
    }

    /*
     * Genera la primera solucion al problema
     */
    public void generar() {
        System.out.println("Solucion: Generar INI");
        Problema p = this.control.getProblema();
        List<Recorrido>re = p.recorridos;
        List<Chofer>ch = p.choferes;
        List<Bus>bu = p.buses;
        int nRe = re.size();
        Chofer cAux;
        Bus bAux;
        int[] chAux, buAux;
        this.plan = new Dia[7];
        for(int i=0; i<7; i++) {
            Dia dAux = new Dia();
            Recorrido rAux = new Recorrido();
            for(int j=0; j<nRe; j++) {
                int[] r = new int[3];
                rAux = re.get(j);
                r[0] = rAux.getId();
                chAux = p.findChoferLibre(i, rAux);
                buAux = p.findBusLibre(i, rAux);
                if(chAux[0]>=0 && buAux[0]>=0) {
                    cAux = ch.get(chAux[0]);
                    bAux = bu.get(buAux[0]);
                    r[1] = cAux.getId();
                    r[2] = bAux.getId();
                    cAux.addRecorrido(i, chAux[2], rAux, chAux[1]);
                    bAux.addRecorrido(i, buAux[2], rAux, buAux[1]);
                    dAux.addViaje(r);
                }
            }
            this.plan[i] = dAux;
        }
        System.out.println("Solucion: Generar FIN");
        this.evaluar();
    }

    /*
     * Evalua la solucion actual
     */
    public void evaluar() {
        Problema p = this.control.getProblema();
        List<Recorrido>re = p.recorridos;
        List<Chofer>ch = p.choferes;
        List<Bus>bu = p.buses;
        int i=0;                                //contador de dias
        int j=0;                                //contador de viajes
        Dia dAux;                               //Dia a calcular
        List<int[]>pAux;                        //Plan del dia a calcular
        int nPDia;                              //Numero de viajes en el plan
        int[] vAux = new int[3];                //viaje a calcular
        int[] bRAux = new int[2];               //recorrido del bus (tiene el deadtrip)
        int[] cRAux = new int[2];               //recorrido del chofer (tiene el deadtrip)
        int minBu = 0;
        int minCh = 0;
        int minBuDT = 0;
        int minChDT = 0;
        Chofer cAux;
        Bus bAux;
        Recorrido rAux;
        String[] cDTAux;
        String[] bDTAux;
        for(i=0; i<7; i++) {                    //recorre toda la semana
            dAux = this.plan[i];
            pAux = dAux.getPlan();
            nPDia = pAux.size();
            for(j=0; j<nPDia; j++) {
                vAux = pAux.get(j);
                rAux = re.get(vAux[0]);
                cAux = ch.get(vAux[1]);
                bAux = bu.get(vAux[2]);
                cRAux = cAux.getRecorrido(vAux[0], i);
                bRAux = bAux.getRecorrido(vAux[0], i);
                minBu += rAux.getTiempo();
                minCh += rAux.getTiempo();
                if((int)cRAux[0]>=0) {
                    cDTAux = p.depositos.get((int)cRAux[0]);
                    minChDT += Integer.parseInt(cDTAux[2]);
                }
                if((int)bRAux[0]>=0) {
                    bDTAux = p.depositos.get((int)bRAux[0]);
                    minBuDT += Integer.parseInt(bDTAux[2]);
                }
            }
        }
        this.costoChoferBus = p.hrBus*minBu + p.hrChofer*minCh;
        this.costoDT = p.hrBus*minBuDT;
        this.minBus = minBu;
        this.minChofer = minCh;
        this.minBusDeadTrip = minBuDT;
        this.minChoferDeadTrip = minChDT;
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
