package jesuitas.dam.extra2021;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JuegoDAO {

    @Query("SELECT * FROM tabla_juego")
    LiveData<List<Juego>> obtenerJuegos();

    @Insert
    void insertarJuego(Juego juego);

    @Update
    void updateJuego(Juego juego);

    @Delete
    void deleteJuego(Juego juego);

    @Query("DELETE FROM tabla_juego")
    void deleteAllJuegos();

}
