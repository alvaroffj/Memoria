package Problema;

import Controlador.CPrincipal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alvaro
 */
public class Problema {
    public List<Recorrido> recorridos; 
    public List buses;
    public List<String[]>depositos;
    public List<Chofer> choferes;
    CPrincipal control;
    public int maxMinCont, maxMinPDia, diasLibres, minMargen;

    public Problema(CPrincipal cp) {
        this.control = cp;
        this.diasLibres = 2;
        this.maxMinCont = 5 * 60;
        this.maxMinPDia = 8 * 60;
        this.minMargen = 10;
    }


    /**
     * Gestiona la carga de todos los archivos necesarios para el funcionamiento del algoritmo
     */
    public void cargarDatos() {
        System.out.println("Problema: cargarDatos INI");
        this.cargaRecorridos("cargar/recorridos.pmt");
        this.cargaBuses("cargar/buses.pmt");
        this.cargaChoferes("cargar/choferes.pmt");
        this.cargaDepositos("cargar/depositos.pmt");
        System.out.println("Problema: cargarDatos FIN");
    }

    
    /**
     * Carga el archivo con los recorridos a planificar
     * @param url archivo a cargar
     */
    public void cargaRecorridos(String url) {
        System.out.println("Cargar: "+url);
        File aux = new File(url);
        FileReader fR;
        BufferedReader bR;
        try {
            this.recorridos = new LinkedList();
            fR = new FileReader(aux);
            bR = new BufferedReader(fR);
            String texto;
            String[] txt;
            int i = 0;
            
            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                Recorrido rAux = new Recorrido(i);
                String[] pu = new String[2];
                String[] ho = new String[2];
                
                pu[0] = txt[1];
                pu[1] = txt[2];
                
                ho[0] = txt[3];
                ho[1] = txt[4];
                
                rAux.setPuntos(pu);
                rAux.setHorario(ho);
                this.recorridos.add(rAux);
                i++;
            }

            fR.close();
            bR.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }


    /**
     * Carga el archivo con los buses a asignar
     * @param url archivo a cargar
     */
    public void cargaBuses(String url) {
        System.out.println("Cargar: "+url);
        File aux = new File(url);
        FileReader fR;
        BufferedReader bR;
        try {
            this.buses = new LinkedList();
            fR = new FileReader(aux);
            bR = new BufferedReader(fR);
            String texto;
            String[] txt;

            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                this.buses.add(txt);
            }

            fR.close();
            bR.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Carga el archivo con los choferes disponibles
     * @param url archivo a cargar
     */
    public void cargaChoferes(String url) {
        System.out.println("Cargar: "+url);
        File aux = new File(url);
        FileReader fR;
        BufferedReader bR;
        try {
            this.choferes = new LinkedList();
            fR = new FileReader(aux);
            bR = new BufferedReader(fR);
            String texto;
            String[] txt;
            int i = 0;
            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                Chofer cAux = new Chofer(i, this.diasLibres);
                cAux.setIni(txt[2]);
                cAux.setNombre(txt[1]);
                
                this.choferes.add(cAux);
                i++;
            }

            fR.close();
            bR.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public void cargaDepositos(String url) {
        System.out.println("Cargar: "+url);
        File aux = new File(url);
        FileReader fR;
        BufferedReader bR;
        try {
            this.depositos = new LinkedList();
            fR = new FileReader(aux);
            bR = new BufferedReader(fR);
            String texto;
            String[] txt;

            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                this.depositos.add(txt);
            }

            fR.close();
            bR.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    /**
     * 
     * @param dia Dia para el cual se busca chofer
     * @param idRec ID del recorrido para el que se busca chofer
     * @return [id del chofer, id del dead trip, posicion a insertar recorrido en el chofer]
     */
    public int[] findChoferLibre(int dia, Recorrido rReq) {
        Chofer cAux = new Chofer();
        Recorrido rAux;
        Boolean encontrado = false;
        int[] r = new int[3];
        int[] comAux = new int[2];
        int i = 0;
        int nCh = this.choferes.size();
        int j = 0;
        while(!encontrado && i<nCh) {
            cAux = this.choferes.get(i);
            List pDia = cAux.getPlanDia(dia);
            int nRDia = pDia.size();
            j = 0;
            if(nRDia>0) {
                //TODO averiguar si el chofer puede realizar el viaje
                int isCompatible = 1;
                //TODO no es necesario revisar todos los viajes, basta con uno que no sea compatible
                for(j=0; j<nRDia; j++) {
                    rAux = this.recorridos.get(((int[])pDia.get(j))[1]);
                    comAux = this.isRecCompatible(rAux, rReq, 0, cAux.getTotalMinDia(dia));
                    isCompatible = isCompatible * comAux[0];
                }
                encontrado = (isCompatible>0)?true:false;
            } else {
                encontrado = true;
                comAux[1] = this.getDeadTrip(cAux.getIni(), rReq.getPuntos()[0]);
            }
            if(!encontrado)
                i++;
        }
        r[0] = (encontrado)?i:-1;
        r[1] = comAux[1];
        r[2] = j;
        return r;
    }
    
    public int[] isRecCompatible(Recorrido a, Recorrido b, int minCont, int minTotal) {
        int[] r = new int[2];
        int isCompatible = 0;
        int dif = 0;
        int deadTrip = -1;
        int tDT = 0;
        if(a.getId() != b.getId()) {
            int[] aH = a.getHorario();
            int[] bH = b.getHorario();
            //TODO Mejorar el calculo de la diferencia de tiempo
            if(bH[1] < aH[0]) { //antes
                isCompatible = 1;
                dif = aH[0]-bH[1];
                dif -= (int)Math.ceil((float)dif/(float)100)*40;
                deadTrip = this.getDeadTrip(b.getPuntos()[1], a.getPuntos()[0]);
            } else if(bH[0] > aH[1]) { //despues
                isCompatible = 1;
                dif = bH[0]-aH[1];
                dif -= (int)Math.ceil((float)dif/(float)100)*40;
                deadTrip = this.getDeadTrip(a.getPuntos()[1], b.getPuntos()[0]);
            } else {
                isCompatible = 0;
            }
            if(isCompatible>0) {
                if(deadTrip>=0) {
                    tDT =Integer.parseInt(this.depositos.get(deadTrip)[2]);
                } else {
                    tDT = 0;
                }
                if(dif > tDT) {
                    if(dif <= this.minMargen) {
                        if(minCont + dif < this.maxMinCont && minTotal + b.getTiempo() < this.maxMinPDia) {
                            isCompatible = 1;
                        } else isCompatible = 0;
                    } else {
                        if(minTotal + b.getTiempo() < this.maxMinPDia) {
                            isCompatible = 1;
                        } else isCompatible = 0;
                    }
                } else isCompatible = 0;
            }
        }
        r[0] = isCompatible;
        r[1] = deadTrip;
        return r;
    }
    
    /**
     * Obtiene el tiempo necesario para ir de un deposito a otro
     * @param ini Deposito de inicio
     * @param fin Deposito de destino
     * @return Minutos
     */
    public int getDeadTrip(String ini, String fin) {
        int nDep = this.depositos.size();
        int i = 0;
        Boolean encontrado = false;
        String[] aux;
        while(!encontrado && i<nDep) {
            aux = (String[])this.depositos.get(i);
            if(ini.compareTo((String)aux[0]) == 0 && fin.compareTo((String)aux[1]) == 0) {
                encontrado = true;
            } else {
                i++;
            }
        }
        if(encontrado)
            return i;
        else return -1;
    }
}

