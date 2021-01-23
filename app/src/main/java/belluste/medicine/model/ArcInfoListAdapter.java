package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArcInfoListAdapter extends RecyclerView.Adapter<ArcInfoViewHolder> {

    private final ArrayList<MedArchiviata.Info> lista;

    public ArcInfoListAdapter(ArrayList<MedArchiviata.Info> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ArcInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ArcInfoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArcInfoViewHolder holder, int position) {
        holder.bind(lista.get(position).getDataAgg(), lista.get(position).getDataArc(), lista.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
