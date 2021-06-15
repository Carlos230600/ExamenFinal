package jesuitas.dam.extra2021;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JuegoAdapter extends RecyclerView.Adapter<JuegoAdapter.JuegoHolder>{

    private OnItemClickListener listener;
    private List<Juego> juegos = new ArrayList<>();

    @NonNull
    @Override
    public JuegoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new JuegoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JuegoHolder holder, int position) {
        Juego juegoActual = juegos.get(position);
        holder.textView_titulo.setText(juegoActual.getTitulo());
        holder.textView_desarrollador.setText(juegoActual.getDesarrollador());
        holder.textView_anio.setText(juegoActual.getAnio());
       holder.textView_favorito.setText(juegoActual.getEsFavorito());//Corregr
    }

    @Override
    public int getItemCount() {
        return juegos.size();
    }


    public void setJuegos(List<Juego> juegos)
    {
        this.juegos = juegos;
        notifyDataSetChanged();
    }

    /********************************************************************/
    public Juego getJuegoEnPosicion (int position)
    {
        return juegos.get(position);
    }



    class JuegoHolder extends RecyclerView.ViewHolder
    {
        private TextView textView_titulo;
        private TextView textView_desarrollador;
        private TextView textView_anio;
        private TextView textView_favorito;

        public JuegoHolder(@NonNull View itemView) {
            super(itemView);
            textView_titulo = itemView.findViewById(R.id.titulo);
            textView_desarrollador = itemView.findViewById(R.id.desarrollador);
            textView_anio= itemView.findViewById(R.id.anio);
            textView_favorito =  itemView.findViewById(R.id.isFavorito);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(juegos.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Juego juego);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
