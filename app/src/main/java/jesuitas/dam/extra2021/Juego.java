package jesuitas.dam.extra2021;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabla_juego")
public class Juego {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String desarrollador;
    private String anio;
    private String esFavorito;

    public Juego(int id, String titulo, String desarrollador, String anio, String esFavorito) {
        this.id = id;
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.anio = anio;
        this.esFavorito = esFavorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(String esFavorito) {
        this.esFavorito = esFavorito;
    }
}
