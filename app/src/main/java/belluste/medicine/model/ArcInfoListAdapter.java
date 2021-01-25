package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArcInfoListAdapter extends RecyclerView.Adapter<ArcInfoViewHolder> {

    private final ArrayList<MedArchiviata.Info> lista;
    private final ArrayList<Boolean> expand;

    public ArcInfoListAdapter(ArrayList<MedArchiviata.Info> lista) {
        this.lista = lista;
        this.expand = new ArrayList<>();
        for (int i=0; i<lista.size(); i++) {
            expand.add(false);
        }
    }

    @NonNull
    @Override
    public ArcInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ArcInfoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArcInfoViewHolder holder, int position) {
        holder.bind(lista.get(position).getDataAgg(), lista.get(position).getDataArc(), lista.get(position).getNote());
        holder.itemView.setOnClickListener(v -> {
            if (holder.NoteLength() > 0) {
                boolean isExpanded = expand.get(position);
                if (!isExpanded) {
                    holder.Expand(lista.get(position).getNote());
                } else {
                    holder.Compress();
                }
                expand.add(position + 1, !isExpanded);
                expand.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
