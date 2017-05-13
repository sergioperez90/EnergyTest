package sd.energytest.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.energysistem.uitest.tvshow.Catalog;
import com.energysistem.uitest.tvshow.Chapter;
import com.energysistem.uitest.tvshow.TvShow;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Collection;

import sd.energytest.R;

/**
 * Created by sergio on 13/5/17.
 */

public class ListSeries extends AsyncTask<Void, Void, ArrayAdapter<String>> {
    private Collection<TvShow> tvShows;
    private TvShow [] series;
    private Collection<Chapter> capitulos;
    private ProgressDialog pDialog;
    private Context context;
    private Catalog catalogo;
    private AdapterSQLite sqlAdapter;


    public ListSeries(Context context){
        this.context = context;
        catalogo = new Catalog();
        sqlAdapter = new AdapterSQLite(this.context);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando Series");
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    @Override
    protected ArrayAdapter<String>doInBackground(Void...arg0){
        //Cargamos las series en un array de TvShow que son series
        try{
            tvShows = catalogo.getTvShows();
            series = new TvShow[tvShows.size()];
            tvShows.toArray(series);
            for(int i = 0; i<series.length; i++){
                sqlAdapter.create(series[i].getTitle(), series[i].getNumberOfSeasons(), series[i].getPoster(), null, null, null);
            }
            sqlAdapter.selectAll();
        }catch (ConnectException e){
            Log.e("ERROR", "ERROR EN LA CONEXION");
        }catch(IOException e){
            e.printStackTrace();
        }

    return null;
    }

    @Override
    protected void onPostExecute(ArrayAdapter<String>result){
        super.onPostExecute(result);
        pDialog.dismiss();

    }


}