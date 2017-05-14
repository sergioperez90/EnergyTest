package sd.energytest.Adapters;

/**
 * Created by sergio on 14/5/17.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import sd.energytest.R;


public class AdapterList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] nombre;
    private final String[] fecha;
    public AdapterList(Activity context,
                      String[] web, String[] fecha) {
        super(context, R.layout.lista_simple, web);
        this.context = context;
        this.nombre = web;
        this.fecha = fecha;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.lista_simple, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.capitulos);
        TextView txtFecha = (TextView) rowView.findViewById(R.id.fecha);

        txtTitle.setText(nombre[position]);
        txtFecha.setText(fecha[position]);

        return rowView;
    }

}