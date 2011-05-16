package Problema;


/**
 *
 * @author Alvaro
 */
public class Recorrido {
    String[] Puntos;
    String[] Horario;
    int Tiempo;
    int id;

    public Recorrido(int id) {
        this.id = id;
    }

    public void setHorario(String[] Horario) {
        this.Horario = Horario;
        int[] ho = new int[2];
        int nHo = Horario.length;
        for(int i=0; i<nHo; i++) {
            String[] aux = new String[2];
            String aux2;
            aux = Horario[i].split(":");
            aux[0] = (aux[0].compareTo("00")==0)?"24":aux[0];
            aux2 = aux[0]+aux[1];
            ho[i] = Integer.parseInt(aux2);
        }
        int dif = ho[1]-ho[0];
        dif -=(int)Math.ceil((float)dif/(float)100)*40;
        this.setTiempo(dif);
    }

    public void setPuntos(String[] Puntos) {
        this.Puntos = Puntos;
    }

    public void setTiempo(int Tiempo) {
        this.Tiempo = Tiempo;
    }
}
