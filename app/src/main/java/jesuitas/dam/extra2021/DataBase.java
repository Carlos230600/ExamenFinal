package jesuitas.dam.extra2021;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


@Database(entities = {Juego.class}, version = 1)
public abstract class DataBase extends RoomDatabase{

    private static DataBase instance;
    public abstract JuegoDAO juegoDAO();

    public static synchronized DataBase getInstance(Context context){
        if(instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataBase.class, "basedatos_peliculas")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }


    //Métodos para "rellenar" la bbdd por pirmera vez para tener datos de prueba.
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private JuegoDAO juegoDAO;

        public PopulateDbAsyncTask(DataBase db)
        {
            juegoDAO = db.juegoDAO();
        }


        //Metodo para insertar valores inciales
        @Override
        protected Void doInBackground(Void... voids) {
            juegoDAO.insertarJuego(new Juego(1,"League of Legends","Riot Games","2009","esFavorito"));
            juegoDAO.insertarJuego(new Juego(2,"Minecaft","Mojang","2011","esFavorito"));
            return null;
        }
    }
}
