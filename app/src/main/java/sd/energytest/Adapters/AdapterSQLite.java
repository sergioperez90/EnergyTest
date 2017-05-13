package sd.energytest.Adapters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import sd.energytest.Clases.Serie;

/**
 * Created by sergio on 13/5/17.
 */

public class AdapterSQLite {
    private AdminSQLite admin;
    private Context context;
    private ArrayList<Serie> series;

    public AdapterSQLite(Context context){
        this.context = context;
        admin = new AdminSQLite(this.context, "tvshows", null, 1);
        series = new ArrayList<Serie>();
    }

    public void create(String titulo, int numtemporadas, String poster, String capitulo, String fecha, String fanart){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("titulo", titulo);
        registro.put("numtemporadas", numtemporadas);
        registro.put("poster", poster);

        if(!comprobar(titulo)){
            db.insert("serie", null, registro);
            Log.e("Serie","insertada correctamente");
        }else{
            Log.e("NOTA","la nota ya existe");
        }

        /*ContentValues registro2 = new ContentValues();
        registro2.put("tituloserie", titulo);
        registro2.put("titulocapitulo", capitulo);
        registro2.put("fecha", fecha);
        registro2.put("fanart", fanart);

        db.insert("capitulo", null, registro2);*/


        db.close();
    }

    public boolean comprobar(String titulo){
        SQLiteDatabase db = admin.getWritableDatabase();
        boolean res = false;
        String consulta = "select * from serie where titulo = ?";

        Cursor fila = db.rawQuery(consulta, new String[] {titulo});
        if(fila.getCount() > 0){
            res = true;
        }
        return res;
    }


    public ArrayList<Serie> selectAll(){
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = null;

        fila = db.rawQuery("select titulo, numtemporadas, poster from serie", null);

        if (fila.moveToFirst()) {
            while (fila.isAfterLast() == false) {
                String titulo = fila.getString(fila.getColumnIndex("titulo"));
                String numtemporadas = fila.getString(fila.getColumnIndex("numtemporadas"));
                String poster = fila.getString(fila.getColumnIndex("poster"));
                System.out.println("Titulo: "+titulo);
                System.out.println("Poster: "+poster);
                System.out.println("Temporadas: "+numtemporadas);
                //nota = new Nota(guid, titulo, contenido, fecha); // Creo la nota
                //notas.add(nota); // La añado al ArrayList
                fila.moveToNext();
            }
        }


        db.close();
        return series;
    }


}
