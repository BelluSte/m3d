package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class MedListAdapter extends ListAdapter<Medicina, MedViewHolder> {

    public MedListAdapter(@NonNull DiffUtil.ItemCallback<Medicina> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MedViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        Medicina med = getItem(position);
        holder.bind(med.getNome());
    }

    public static class MedDiff extends DiffUtil.ItemCallback<Medicina> {

        @Override
        public boolean areItemsTheSame(@NonNull Medicina oldItem, @NonNull Medicina newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Medicina oldItem, @NonNull Medicina newItem) {
            boolean verifica;
            verifica = oldItem.getNome().equals(newItem.getNome()) && oldItem.getTipo().equals(newItem.getTipo());
            return verifica;
        }
    }
}
