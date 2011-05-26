package Problema;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Dia {
    List plan;

    public Dia() {
        plan = new LinkedList();
    }
    
    public void addViaje(int[] r) {
        plan.add(r);
    }
    
    public boolean isValido() {
        return true;
    }
}
