package sd.energytest.Clases;

import java.util.ArrayList;

/**
 * Created by sergio on 13/5/17.
 */

public class Serie {
    private String titulo;
    private int numtemporadas;
    private String poster;
    private ArrayList<Capitulo> capitulos;
    private Capitulo capitulo;

    Serie(String titulo, int numtemporadas, String poster){
        this.titulo = titulo;
        this.numtemporadas = numtemporadas;
        this.poster = poster;
        capitulos = new ArrayList<Capitulo>();
    }


    public String getTitulo(){
        return this.titulo;
    }

    public int getNumTemporadas(){
        return this.numtemporadas;
    }

    public String getPoster(){
        return this.poster;
    }

    public void addCapitulo(String nombre, String fecha, String fanart){
        capitulo = new Capitulo(nombre, fecha, fanart);
        capitulos.add(capitulo);
    }

}
