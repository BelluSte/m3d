package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static belluste.medicine.HomeFragment.salva;


public class HomeListAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private final ArrayList<Medicina> lista;

    public HomeListAdapter(ArrayList<Medicina> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HomeViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.tvNome.setText(lista.get(position).getNome());
        holder.tvTot.setText(String.valueOf(lista.get(position).getTotale()));
        holder.tvTipo.setText(lista.get(position).getTipo());
        holder.btnPrendi.setOnClickListener(v -> {
            Medicina medicina = lista.get(position);
            int totale = medicina.getTotale();
            if (totale != 0) {
                totale--;
                medicina.setTotale(totale);
                holder.tvTot.setText(String.valueOf(totale));
                if (!salva) {
                    salva = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}
