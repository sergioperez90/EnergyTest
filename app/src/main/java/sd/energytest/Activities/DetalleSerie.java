package sd.energytest.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;

import sd.energytest.Adapters.AdapterList;
import sd.energytest.R;

public class DetalleSerie extends AppCompatActivity {
    private ConnectivityManager connectivityManager;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);

        String titulo = getIntent().getStringExtra("titulo");
        String fanart = getIntent().getStringExtra("fanart");
        String [] capitulos =  getIntent().getStringArrayExtra("capitulos");
        String [] fechas =  getIntent().getStringArrayExtra("fechas");
        setTitle(titulo);


        ImageView fanArt = (ImageView) findViewById(R.id.fanArt);
        listView = (ListView) findViewById(R.id.capitulos);

        AdapterList adapter = new
                AdapterList(DetalleSerie.this, capitulos, fechas);
        listView.setAdapter(adapter);


        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            if (fanart != null) {
                new LoadImage(fanArt).execute(fanart);
            }
        }
    }


    //Clase que permite cargar las imagenes
    class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }


        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                mIcon11 = BitmapFactory.decodeStream((InputStream) new URL(urldisplay).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }


    }
}
