package jesuitas.dam.extra2021;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioJuegos {

    private JuegoDAO juegoDAO;
    private LiveData<List<Juego>> listaJuegos;


    public RepositorioJuegos(Application application) {
        DataBase database = DataBase.getInstance(application);
        juegoDAO = database.juegoDAO();
        listaJuegos = juegoDAO.obtenerJuegos();
    }


    public void insert (Juego juego) {
        new InsertJuegoAsyncTask(juegoDAO).execute(juego);
    }
    public void update (Juego juego)
    {
        new UpdateJuegoAsyncTask(juegoDAO).execute(juego);
    }
    public void delete (Juego juego)
    {
        new DeleteJuegoAsyncTask(juegoDAO).execute(juego);
    }
    public void deleteAllJuegos()
    {
        new DeleteAllJuegoAsyncTask(juegoDAO).execute();
    }
    public LiveData<List<Juego>> getAllJuegos()
    {
        return listaJuegos;
    }


    private static class InsertJuegoAsyncTask extends AsyncTask<Juego, Void, Void> {
        private JuegoDAO juegoDAO;

        private InsertJuegoAsyncTask(JuegoDAO juegoDAO) {
            this.juegoDAO = juegoDAO;
        }

        @Override
        protected Void doInBackground(Juego... Juegos) {
            juegoDAO.insertarJuego(Juegos[0]);
            return null;
        }
    }



    private static class UpdateJuegoAsyncTask extends AsyncTask<Juego, Void, Void> {
        private JuegoDAO juegoDAO;

        private UpdateJuegoAsyncTask(JuegoDAO juegoDAO)
        {
            this.juegoDAO = juegoDAO;
        }

        @Override
        protected Void doInBackground(Juego... Juegos) {
            juegoDAO.updateJuego(Juegos[0]);
            return null;
        }
    }



    private static class DeleteJuegoAsyncTask extends AsyncTask<Juego, Void, Void> {
        private JuegoDAO juegoDAO;

        private DeleteJuegoAsyncTask(JuegoDAO juegoDAO)
        {
            this.juegoDAO = juegoDAO;
        }
        @Override
        protected Void doInBackground(Juego... Juegos) {
            juegoDAO.deleteJuego(Juegos[0]);
            return null;
        }
    }

    private static class DeleteAllJuegoAsyncTask extends AsyncTask<Void, Void, Void> {
        private JuegoDAO juegoDAO;

        private DeleteAllJuegoAsyncTask(JuegoDAO juegoDAO)
        {
            this.juegoDAO = juegoDAO;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            juegoDAO.deleteAllJuegos();
            return null;
        }
    }

}
