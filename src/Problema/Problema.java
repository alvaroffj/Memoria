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
    public List<Bus> buses;
    public List<String[]> depositos;
    public List<Chofer> choferes;
    public List<List> L; //recorridos compatibles
    CPrincipal control;
    public int maxMinCont, maxMinPDia, diasLibres, minMargen;
    public int hrBus, hrChofer;

    public Problema(CPrincipal cp) {
        this.control = cp;
        this.diasLibres = 2;
        this.maxMinCont = 5 * 60;
        this.maxMinPDia = 8 * 60;
        this.minMargen = 10;
        this.hrBus = 20000 / 60;
        this.hrChofer = 10000 / 60;
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

            int i=0;
            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                Bus bAux = new Bus(i, txt[1], txt[2], txt[3]);
                this.buses.add(bAux);
                i++;
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
    
    public int[] findBusLibre(int dia, Recorrido rReq) {
        int[] r = new int[3];
        Recorrido rAux;
        Bus bAux = new Bus();
        int encontrado = 0;
        int[] comAux = new int[2];
        int i=0;
        int j=0;
        int nBus = this.buses.size();
        int isCompatible = 1;
        
        while(encontrado<1 && i<nBus) {
            bAux = this.buses.get(i);
            List pDia = bAux.getPlanDia(dia);
            int nRDia = pDia.size();
            j = 0;
            if(nRDia > 0) {
                isCompatible = 1;
                j = 0;
                while(isCompatible > 0 && j < nRDia) {
                    rAux = this.recorridos.get(((int[])pDia.get(j))[1]);
                    //Revisar compatibilidad con los recorridos siguientes en el plan del bus
                    comAux = this.isRecCompatible(rAux, rReq);
                    isCompatible = isCompatible * comAux[0];
                    j++;
                }
                encontrado = isCompatible;
            } else {
                encontrado = 1;
                comAux[1] = this.getDeadTrip(bAux.getDeposito(), rReq.getPuntos()[0]);
            }
            if(encontrado<1)
                i++;
        }
        r[0] = (encontrado>0)?i:-1;
        r[1] = comAux[1];
        r[2] = j;
        return r;
    }
    
    /**
     * Buscar un chofer disponible para el dia y recorrido solicitado
     * @param dia Dia para el cual se busca chofer
     * @param idRec ID del recorrido para el que se busca chofer
     * @return [id del chofer, id del dead trip, posicion a insertar recorrido en el chofer]
     * Si el id del chofer es -1, entonces no encontro a chofer
     * Si el id del dead trip es -1, entonces el chofer se encuentra en el mismo lugar de donde sale el recorrido
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
                int isCompatible = 1;
                j = 0;
                while(isCompatible > 0 && j < nRDia) {
                    rAux = this.recorridos.get(((int[])pDia.get(j))[1]);
                    comAux = this.isRecCompatible(rAux, rReq);
                    //Revisar compatibilidad con los recorridos siguientes en el plan del chofer
                    if(rReq.getTiempo() + cAux.getTotalMinDia(dia) > this.maxMinPDia) {
                        comAux[0] = 0;
                    }
                    isCompatible = isCompatible * comAux[0];
                    j++;
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
    
    /**
     * Obtiene la diferencia, en minutos, entre 2 horas
     * @param ini hora inicial en enteros, ej: 05:30 -> 530
     * @param fin hora final en enteros, ej: 05:30 -> 530
     * @return minutos entre la hora inicial y final
     */
    public int getMinDif(int ini, int fin) {
        int dif;
        dif = fin-ini;
        if(dif>=60)
            dif -= (int)Math.ceil((float)dif/(float)100)*40;
        return dif;
    }
    
    /**
     * Revisa si 2 recorridos son compatibles de realizar
     * @param a Recorrido base
     * @param b Recorrido a evaluar
     * @return [compatibilidad, id  deadtrip]
     * compatibilidad = 1 => compatible
     * compatibilidad = 0 => no compatible
     * id deadtrip = -1 => no hay deadtrip
     */
    public int[] isRecCompatible(Recorrido a, Recorrido b) {
        int[] r = new int[2];
        int isCompatible = 0;
        int dif = 0;
        int deadTrip = -1;
        int tDT = 0;
        if(a.getId() != b.getId()) {
            int[] aH = a.getHorario();
            int[] bH = b.getHorario();
            if(bH[1] < aH[0]) { //antes
                isCompatible = 1;
                dif = this.getMinDif(bH[1], aH[0]);
                deadTrip = this.getDeadTrip(b.getPuntos()[1], a.getPuntos()[0]);
            } else if(bH[0] > aH[1]) { //despues
                isCompatible = 1;
                dif = this.getMinDif(aH[1], bH[0]);
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
//                    if(minTotal + b.getTiempo() < this.maxMinPDia) {
                        isCompatible = 1;
//                    } else isCompatible = 0;
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
    
    public void getL() {
        this.L = new LinkedList();
        int n = this.recorridos.size();
        int[] com = new int[2];
        int[] tra;
        Recorrido auxA, auxB;
        for(int i=0; i<n; i++) {
            auxA = this.recorridos.get(i);
            List<int[]> rec = new LinkedList();
            for(int j=i+1; j<n; j++) {
                auxB = this.recorridos.get(j);
                com = this.isRecCompatible(auxA, auxB);
                if(com[0]==1) { //compatible
                    tra = new int[2];
                    tra[0] = auxB.getId();
                    tra[1] = com[1];
                    rec.add(tra);
                }
            }
            this.L.add(i, rec);
        }
    }
    
}

