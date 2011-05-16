package Problema;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Dia {
    List<Recorrido> plan;

    public Dia() {
        plan = new LinkedList();
    }
    
    public void addRecorrido(Recorrido r) {
        plan.add(r);
    }
    
    public boolean isValido() {
        return true;
    }
}
