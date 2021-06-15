package jesuitas.dam.extra2021;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelJuegos extends AndroidViewModel {

    private RepositorioJuegos repositorioJuegos;
    private LiveData<List<Juego>> juegos;


    public ViewModelJuegos(@NonNull Application application) {
        super(application);
        this.repositorioJuegos = new RepositorioJuegos(application);
        this.juegos = repositorioJuegos.getAllJuegos();
    }

    public void insert(Juego juego) { this.repositorioJuegos.insert(juego); }
    public void update(Juego juego) { this.repositorioJuegos.update(juego); }
    public void delete(Juego juego) {
        this.repositorioJuegos.delete(juego);
    }
    public void deleteAllJuegos()    { repositorioJuegos.deleteAllJuegos(); }
    public LiveData<List<Juego>> getAllJuegos() {
        return juegos;
    }

}
