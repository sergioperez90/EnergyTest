package sd.energytest.Clases;

/**
 * Created by sergio on 13/5/17.
 */

public class Capitulo {
    private String nombre;
    private String fecha;
    private String fanart;

    public Capitulo(String nombre, String fecha, String fanart){
        this.nombre = nombre;
        this.fecha = fecha;
        this.fanart = fanart;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getFecha(){
        return this.fecha;
    }

    public String getFanart(){
        return this.fanart;
    }
}
