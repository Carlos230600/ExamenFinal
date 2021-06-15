package jesuitas.dam.extra2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity2 extends AppCompatActivity {

    private ViewModelJuegos viewModelJuegos;
    private JuegoDAO juegoDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        List<Juego> juegos = new ArrayList<Juego>();

        viewModelJuegos = ViewModelProviders.of(this).get(ViewModelJuegos.class);
        viewModelJuegos.getAllJuegos().observe(this, new Observer<List<Juego>>() {
            @Override
            public void onChanged(List<Juego> juegos) {
                Toast.makeText(Activity2.this, "se ha insertado un juego", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.borrar:
                //viewModelJuegos.delete();
                Toast.makeText(Activity2.this,"Eliminando Juego",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void guardar(View view) {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Activity2.this);
        myAlertBuilder.setTitle("Confirmacion");
        myAlertBuilder.setMessage("¿Estas Seguro?");

        //Boton de confirmar(OK)
        myAlertBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Borro las personas en otro hilo
                MainActivity.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        viewModelJuegos.insert(new Juego(8,"Ejemplo","Desarrollador Ejemplo","2021","esFavorito"));
                    }
                });

                Toast.makeText(Activity2.this,"Has añadido el juego",Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Activity2.this,"Operacion cancelada",Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.show();
    }
}