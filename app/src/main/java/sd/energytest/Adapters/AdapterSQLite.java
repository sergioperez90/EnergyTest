package sd.energytest.Adapters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.energysistem.uitest.tvshow.Chapter;

import java.util.ArrayList;

import sd.energytest.Clases.Capitulo;
import sd.energytest.Clases.Serie;

/**
 * Created by sergio on 13/5/17.
 */

public class AdapterSQLite {
    private AdminSQLite admin;
    private Context context;
    private ArrayList<Serie> series;
    private Serie serie;

    public AdapterSQLite(Context context){
        this.context = context;
        admin = new AdminSQLite(this.context, "tvshows", null, 1);
        series = new ArrayList<Serie>();
    }

    public void create(String titulo, int numtemporadas, String poster, String fanart, Chapter [] capitulos){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("titulo", titulo);
        registro.put("numtemporadas", numtemporadas);
        registro.put("poster", poster);

        if(!comprobarSerie(titulo)){
            db.insert("serie", null, registro);
            //Log.e("SERIE","insertada correctamente");
        }else{
            //Log.e("SERIE","la serie ya existe");
        }

        ContentValues registro2 = new ContentValues();
        for(int i = 0; i<capitulos.length; i++){
            registro2.put("tituloserie", titulo);
            registro2.put("titulocapitulo", capitulos[i].getTitle());
            registro2.put("fecha", capitulos[i].getPublishDate());
            registro2.put("fanart", fanart);

            if(!comprobarCapitulo(capitulos[i].getTitle(), titulo)){
                db.insert("capitulo", null, registro2);
                //Log.e("CAPIUTLO","insertado correctamente");
            }else {
                //Log.e("CAPITULO", "el capitulo ya existe");
            }
        }



        db.close();
    }

    public boolean comprobarSerie(String titulo){
        SQLiteDatabase db = admin.getWritableDatabase();
        boolean res = false;
        String consulta = "select * from serie where titulo = ?";

        Cursor fila = db.rawQuery(consulta, new String[] {titulo});
        if(fila.getCount() > 0){
            res = true;
        }
        return res;
    }


    public boolean comprobarCapitulo(String capitulo, String titulo){
        SQLiteDatabase db = admin.getWritableDatabase();
        boolean res = false;
        String consulta = "select * from capitulo where titulocapitulo = ? and tituloserie = ?";

        Cursor fila = db.rawQuery(consulta, new String[] {capitulo, titulo});
        if(fila.getCount() > 0){
            res = true;
        }

        return res;
    }

    //Cargamos las series al arraylist de series
    public ArrayList<Serie> selectAll(){
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = null;
        fila = db.rawQuery("select titulo, numtemporadas, poster from serie", null);
        Capitulo [] capitulo = null;

        if (fila.moveToFirst()) {
            while (fila.isAfterLast() == false) {
                String titulo = fila.getString(fila.getColumnIndex("titulo"));
                String numtemporadas = fila.getString(fila.getColumnIndex("numtemporadas"));
                String poster = fila.getString(fila.getColumnIndex("poster"));
                serie = new Serie(titulo, numtemporadas, poster);
                capitulo = selectCapitulo(titulo);
                for(int i = 0; i<capitulo.length; i++){
                    serie.addCapitulo(capitulo[i].getNombre(), capitulo[i].getFecha(), capitulo[i].getFanart());
                }

                series.add(serie);
                fila.moveToNext();
            }
        }

        db.close();
        return series;
    }

    //Cargamos los capitulos al arraylist de series
    private Capitulo [] selectCapitulo(String titulo){
        Capitulo [] capitulo = null;
        SQLiteDatabase db = admin.getWritableDatabase();
        String consulta = "select titulocapitulo, fecha, fanart from capitulo where tituloserie = ?";

        Cursor fila = db.rawQuery(consulta, new String[] {titulo});
        int i = 0;
        capitulo = new Capitulo[fila.getCount()];
        if (fila.moveToFirst()) {
            while (fila.isAfterLast() == false) {
                String titulocapitulo = fila.getString(fila.getColumnIndex("titulocapitulo"));
                String fecha = fila.getString(fila.getColumnIndex("fecha"));
                String fanart = fila.getString(fila.getColumnIndex("fanart"));
                capitulo[i] = new Capitulo(titulocapitulo, fecha, fanart);
                i++;
                fila.moveToNext();
            }
        }
        System.out.println();

        db.close();

        return capitulo;

    }


}
