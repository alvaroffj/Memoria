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
    public List recorridos, buses, choferes;
    CPrincipal control;

    public Problema(CPrincipal cp) {
        this.control = cp;
    }


    /**
     * Gestiona la carga de todos los archivos necesarios para el funcionamiento del algoritmo
     */
    public void cargarDatos() {
        System.out.println("Problema: cargarDatos INI");
        this.cargaRecorridos("cargar/recorridos.pmt");
        this.cargaBuses("cargar/buses.pmt");
        this.cargaChoferes("cargar/choferes.pmt");
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

            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                this.recorridos.add(txt);
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

            while((texto = bR.readLine())!=null) {
                txt = texto.split("\t");
                this.choferes.add(txt);
            }

            fR.close();
            bR.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
