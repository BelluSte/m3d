package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

import static belluste.medicine.HomeFragment.salva;


public class HomeListAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final LinkedList<Medicina> lista;
    private final ArrayList<Integer> posizioni;

    public HomeListAdapter(ArrayList<Integer> posizioni, LinkedList<Medicina> lista) {
        this.posizioni = posizioni;
        this.lista = lista;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HomeViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.tvNome.setText(lista.get(posizioni.get(position)).getNome());
        holder.tvTot.setText(String.valueOf(lista.get(posizioni.get(position)).getTotale()));
        holder.tvTipo.setText(lista.get(posizioni.get(position)).getTipo());
        holder.btnPrendi.setOnClickListener(v -> {
            int totale = lista.get(posizioni.get(position)).getTotale();
            if (totale != 0) {
                totale--;
                lista.get(posizioni.get(position)).setTotale(totale);
                holder.tvTot.setText(String.valueOf(totale));
                if (!salva) {
                    salva = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posizioni.size();
    }

}
