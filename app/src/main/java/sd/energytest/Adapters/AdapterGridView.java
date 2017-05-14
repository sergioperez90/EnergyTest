package sd.energytest.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sd.energytest.Clases.Serie;
import sd.energytest.R;

/**
 * Created by sergio on 13/5/17.
 */

public final class AdapterGridView extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private ArrayList<Serie> series;
    public AdapterGridView(Context context, ArrayList<Serie> series) {
        this.series = series;

        mInflater = LayoutInflater.from(context);

        for(int i = 0; i<series.size(); i++){
            mItems.add(new Item(series.get(i).getTitulo(), R.drawable.logotipo, series.get(i).getNumTemporadas() + " temporadas"));
        }

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;
        TextView temp;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.temporadas, v.findViewById(R.id.temporadas));
        }else{
           v.getTag();
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);
        temp = (TextView) v.getTag(R.id.temporadas);

        new LoadImage(picture).execute(series.get(i).getPoster());

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);
        temp.setText(item.temp);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;
        public final String temp;

        Item(String name, int drawableId, String temp) {
            this.name = name;
            this.drawableId = drawableId;
            this.temp = temp;
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