package Modelo;

import Controlador.CPrincipal;
import java.util.Random;
import java.util.Vector;

public class Solucion {
//lala
    CPrincipal control;
    Vector<Vector> plan;
    Double costo;

    public Solucion(CPrincipal cp) {
        this.control = cp;
    }

    public Solucion() {
        
    }

    public void generar() {
        
    }

    public double evaluar() {
        return 0.0;
    }

    public Solucion genVecino() {
        
        Solucion r = new Solucion(this.control);
        Random seed;
        int randomFila,randomColumn,randomFila2=0,randomColumn2=0;
        int largoSol,largoCamion;
        boolean flgRand=true;

        //ejemplo de camiones en solucion actual
        /*Vector cam1 = new Vector();
        Vector cam2 = new Vector();
        Vector cam3 = new Vector();
        Vector cam4 = new Vector();
        cam1.addElement("tar1.1");
        cam1.addElement("tar1.2");
        cam1.addElement("tar1.2");
        cam2.addElement("tar2.1");
        cam2.addElement("tar2.2");
        cam3.addElement("tar3.1");
        cam3.addElement("tar3.2");
        cam3.addElement("tar3.3");
        cam3.addElement("tar3.4");
        cam4.addElement("tar4.1");
        cam4.addElement("tar4.2");
        cam4.addElement("tar4.3");*/
        /*********************************/

        //solucion actual
        /*r.plan = new Vector();
        r.plan.addElement(cam1);
        r.plan.addElement(cam2);
        r.plan.addElement(cam3);
        r.plan.addElement(cam4);*/
        /******************************/

        //for(int i=0;i<r.plan.size();i++)
            //System.out.println(r.plan.elementAt(i));


        /*************GENERAR VECINO******************/
        r.plan=this.plan;

        largoSol=r.plan.size();
        //System.out.println("largo sol: "+largoSol);

        seed = new Random();
        do {
            randomFila=seed.nextInt(largoSol);
            //System.out.println("random fila: "+randomFila);

            largoCamion=r.plan.elementAt(randomFila).size();
            //System.out.println("largo camion: "+largoCamion);
        } while(largoCamion<=0);
        
        randomColumn=seed.nextInt(largoCamion);
        //System.out.println("random column: "+randomColumn);
        
        while(flgRand){
            
            randomFila2=seed.nextInt(largoSol);
            //System.out.println("random fila2: "+randomFila2);
            largoCamion=r.plan.elementAt(randomFila2).size();
            if(largoCamion == 0) largoCamion++;
            randomColumn2=seed.nextInt(largoCamion);
            //System.out.println("random column2: "+randomColumn2);

            if(randomFila!=randomFila2 || randomColumn!=randomColumn2)
                flgRand=false;
        }

        Object subtarea = new Object();
        subtarea = r.plan.elementAt(randomFila).remove(randomColumn);

        r.plan.elementAt(randomFila2).add(randomColumn2, subtarea);

        //for(int i=0;i<r.plan.size();i++)
            //System.out.println(r.plan.elementAt(i));

        return r;
    }
}
