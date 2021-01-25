package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class ArcListAdapter extends RecyclerView.Adapter<ArcViewHolder> {

    private final LinkedList<MedArchiviata> lista;

    public ArcListAdapter(LinkedList<MedArchiviata> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ArcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ArcViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArcViewHolder holder, int position) {
        holder.bind(lista.get(position).getNome(), lista.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}
