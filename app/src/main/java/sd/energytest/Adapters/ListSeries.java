package sd.energytest.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.energysistem.uitest.exception.TvShowNotFoundException;
import com.energysistem.uitest.tvshow.Catalog;
import com.energysistem.uitest.tvshow.Chapter;
import com.energysistem.uitest.tvshow.TvShow;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;

import sd.energytest.Clases.Serie;
import sd.energytest.R;

/**
 * Created by sergio on 13/5/17.
 */

public class ListSeries extends AsyncTask<Void, Void, ArrayList<Serie>> {
    private Collection<TvShow> tvShows;
    private TvShow [] series;
    private Chapter [] capitulos;
    private Collection<Chapter> chapters;
    private ProgressDialog pDialog;
    private Context context;
    private Catalog catalogo;
    private AdapterSQLite sqlAdapter;
    private GridView gridView;
    private String pref;
    private boolean conexion;
    private boolean hayconexioninternet;
    private ArrayList<Serie> mSeries;
    private ImageView imageView;

    public ListSeries(Context context, GridView gridView, String pref, boolean hayconexioninternet, ImageView imageView){
        this.context = context;
        catalogo = new Catalog();
        sqlAdapter = new AdapterSQLite(this.context);
        this.gridView = gridView;
        mSeries = new ArrayList<Serie>();
        this.pref = pref;
        this.conexion = true;
        this.hayconexioninternet = hayconexioninternet;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        if(pref.equalsIgnoreCase("actualizar")){
            pDialog.setMessage("Actualizando Series");
        }else{
            pDialog.setMessage("Cargando Series");
        }
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    @Override
    protected ArrayList<Serie>doInBackground(Void...arg0){
        //Cargamos las series en un array de TvShow que son series
        if(pref.equalsIgnoreCase("primera_vez") || pref.equalsIgnoreCase("actualizar")){
            try{
                //Recorremos el array completo y vamos guardando en sqlite para no volver a conectar con el servidor
                tvShows = catalogo.getTvShows();
                series = new TvShow[tvShows.size()];
                tvShows.toArray(series);
                for(int i = 0; i<series.length; i++){
                    capitulos = new Chapter[series[i].getChapters().getChapters().size()];
                    series[i].getChapters().getChapters().toArray(capitulos);
                    sqlAdapter.create(series[i].getTitle(), series[i].getNumberOfSeasons(), series[i].getPoster(), series[i].getFanArt(), capitulos);
                }

            }catch (ConnectException e){
                Log.e("ERROR", "ERROR EN LA CONEXION");
                conexion = false;
            }catch(IOException e){
                e.printStackTrace();
            }

            mSeries = sqlAdapter.selectAll();
        }else{
            mSeries = sqlAdapter.selectAll();
        }

    return mSeries;

    }

    @Override
    protected void onPostExecute(ArrayList<Serie> series) {
        super.onPostExecute(series);

        if(!conexion){
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Error en la conexión");
            alert.setMessage("Comprueba que estás conectado a una red wifi o de datos móviles, recuerda que no podrás reproducir ninguna serie hasta que tengas una conexión a internet, ahora estás en local. Prueba otra vez.");
            alert.setPositiveButton("OK",null);
            alert.show();
            if(pref.equalsIgnoreCase("primera_vez")){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.INVISIBLE);
            }
        }
        gridView.setAdapter(new AdapterGridView(context, series, hayconexioninternet));

        pDialog.dismiss();
    }



    public ArrayList<Serie> getmSeries(){
        return mSeries;
    }


}