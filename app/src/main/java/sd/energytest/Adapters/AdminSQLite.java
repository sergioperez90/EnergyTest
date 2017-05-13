package sd.energytest.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sergio on 13/5/17.
 */

public class AdminSQLite extends SQLiteOpenHelper {

    public AdminSQLite(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, nombre, factory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table serie(_id integer primary key autoincrement, titulo text, numtemporadas integer, poster text)");
        db.execSQL("create table capitulo(_id integer primary key autoincrement, tituloserie text, titulocapitulo text, fecha text, fanart text)");
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        //db.execSQL("drop table if exists series");

        //db.execSQL("create table serie(_id integer primary key autoincrement, titulo text, numtemporadas integer, poster text)");
        //db.execSQL("create table capitulo(_id integer primary key autoincrement, fecha text, fanart text)");
    }


}