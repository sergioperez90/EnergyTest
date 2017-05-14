package sd.energytest.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import sd.energytest.Adapters.AdapterGridView;
import sd.energytest.Adapters.ListSeries;
import sd.energytest.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListSeries listSeries;
    private GridView gridView;
    final String PREFS_NAME = "MisPrefs";
    private SharedPreferences settings;
    private ConnectivityManager connectivityManager;
    private boolean conexion;
    private String [] capitulos;
    private String [] fechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Comprobamos la conexion a internet para la carga de imagenes
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            conexion = true;

        }else{
            conexion = false;
        }
        gridView = (GridView)findViewById(R.id.gridview);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                capitulos = new String[listSeries.getmSeries().get(position).getCapitulos().size()];
                fechas = new String[listSeries.getmSeries().get(position).getCapitulos().size()];
                for(int i = 0; i<capitulos.length; i++){
                    capitulos[i] = listSeries.getmSeries().get(position).getCapitulos().get(i).getNombre();
                    fechas[i] = listSeries.getmSeries().get(position).getCapitulos().get(i).getFecha();
                }

                Intent in = new Intent(MainActivity.this, DetalleSerie.class);
                in.putExtra("titulo", listSeries.getmSeries().get(position).getTitulo());
                in.putExtra("fanart", listSeries.getmSeries().get(position).getCapitulos().get(0).getFanart());
                in.putExtra("capitulos", capitulos);
                in.putExtra("fechas", fechas);
                startActivity(in);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        //Comprobamos si es la primera vez que iniciamos o no
        if (settings.getBoolean("firstrun", true)) {
            listSeries = new ListSeries(this, gridView, "primera_vez", conexion);
            listSeries.execute();
            // Lo cambiamos a false para que no vuelva a ejecutarlo
            settings.edit().putBoolean("firstrun", false).commit();
        }else{
            listSeries = new ListSeries(this, gridView, "otra_vez", conexion);
            listSeries.execute();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Actualizamos el contenido
        if (id == R.id.actualizar) {
            listSeries = new ListSeries(this, gridView, "actualizar", conexion);
            listSeries.execute();
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
