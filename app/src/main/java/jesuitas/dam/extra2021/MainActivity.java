package jesuitas.dam.extra2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ViewModelJuegos viewModelJuegos;
    private JuegoDAO juegoDAO;

    //Creo nuevos hilos
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.item_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        JuegoAdapter juegoAdapter = new JuegoAdapter();
        recyclerView.setAdapter(juegoAdapter);

        viewModelJuegos = ViewModelProviders.of(this).get(ViewModelJuegos.class);
        viewModelJuegos.getAllJuegos().observe(this, new Observer<List<Juego>>() {
            @Override
            public void onChanged(List<Juego> juegos) {
                juegoAdapter.setJuegos(juegos);  //cuando
                //Toast.makeText(PeliculasActivity.this, "se an actualiz<ado ", Toast.LENGTH_SHORT).show();
            }
        });

        //Manejo el click en el boton flotante
        FloatingActionButton buttonAdd = findViewById(R.id.floatingActionButtonBuscar);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                startActivity(intent);
            }
        });


        //Para borrar al deslizar un item del recyclerview
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Juego juego = juegoAdapter.getJuegoEnPosicion(viewHolder.getAdapterPosition());
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                intent.putExtra("titulo",juego.getTitulo());
                intent.putExtra("desarrollador",juego.getDesarrollador());
                intent.putExtra("anio",juego.getAnio());
                startActivity(intent);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                viewModelJuegos.delete(juegoAdapter.getJuegoEnPosicion(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Persona borrada", Toast.LENGTH_SHORT).show();
            }



        }).attachToRecyclerView(recyclerView);

        //Implementación onItemClick para los items del recycler
        juegoAdapter.setOnItemClickListener(new JuegoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Juego juego) {
                Toast.makeText(MainActivity.this, "Has pulsado un item", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(MainActivity.this,Activity2.class);
                intent.putExtra("titulo",juego.getTitulo());
                intent.putExtra("desarrollador",juego.getDesarrollador());
                intent.putExtra("anio",juego.getAnio());
                startActivity(intent);*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.borrar_todo:
                //Creo un cuadro de dialogo para confirmar
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                myAlertBuilder.setTitle("¿Estas seguro de borrar todo?");
                myAlertBuilder.setMessage("Los cambios no se pueden revertir");

                //Boton de confirmar(OK)
                myAlertBuilder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Borro las personas en otro hilo
                        MainActivity.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                viewModelJuegos.deleteAllJuegos();
                            }
                        });

                        Toast.makeText(MainActivity.this,"Has borrado todo en un nuevo hilo",Toast.LENGTH_SHORT).show();
                    }
                });

                myAlertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Operacion cancelada",Toast.LENGTH_SHORT).show();
                    }
                });

                myAlertBuilder.show();

                break;
        }
        return true;
    }

    public void itemSeleccionado(View view) {
        Toast.makeText(MainActivity.this,"Has seleccioando un item",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,Activity2.class);
        startActivity(intent);
    }
}