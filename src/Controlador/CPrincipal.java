package Controlador;

import Modelo.SA;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import Modelo.Solucion;
import Problema.Problema;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Alvaro
 */
public class CPrincipal {

    File log;
    SA sa;
    Problema problema;

    /**
     * 
     */
    public CPrincipal() {
        System.out.println("CPrincipal");
        this.problema = new Problema(this);
        this.problema.cargarDatos();
        this.problema.getL();
        this.run(1000, 10, 0.8);
    }

    /**
     * 
     * @param ti temperatura de inicio
     * @param tf temperatura de termino
     * @param en % de enfriamiento
     */
    public void run(int ti, int tf, double en) {
        System.out.println("CPrincipal: run");
//        this.log = new File("../logs/log_" + ti + "_" + tf + "_" + en + ".txt");
//        this.sa = new SA(this);
//        this.sa.setPorcEnfriamiento(en);
//        this.sa.setTempInicial(ti);
//        this.sa.setTempFinal(tf);
//        this.sa.run();
    }
    
    public double parseLongitude(double _lon, String d) {
        if (_lon < 99999.0) {
            double lon = (double)((long)_lon / 100L); // _lon is always positive here
            System.out.println(lon);
            lon += (_lon - (lon * 100.0)) / 60.0;
            System.out.println(lon);
            return d.equals("W")? -lon : lon;
        } else {
            return 180.0; // invalid longitude
        }
    }

    /**
     * 
     * @param t temperatura
     * @param costo costo de la solucion
     */
    public void grabar(int t, List costo) {
        FileWriter fW;
        BufferedWriter bW;
        PrintWriter salida;
        int l = costo.size(), i;
        try {
            fW = new FileWriter(this.log, true);
            bW = new BufferedWriter(fW);
            salida = new PrintWriter(bW);
            for (i = 0; i < l; i++) {
                salida.println(t + "\t" + costo.get(i).toString().substring(0, costo.get(i).toString().length() - 2));
            }
            salida.close();
            bW.close();
            fW.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public Problema getProblema() {
        return problema;
    }
}
