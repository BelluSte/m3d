package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class MedListAdapter extends ListAdapter<Medicina, MedViewHolder> {

    public MedListAdapter() {
        super(MEDICINA_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MedViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        holder.bind(getItem(position).getNome(), getItem(position).getTipo());
    }

    public static final DiffUtil.ItemCallback<Medicina> MEDICINA_ITEM_CALLBACK = new DiffUtil.ItemCallback<Medicina>() {
        @Override
        public boolean areItemsTheSame(@NonNull Medicina oldItem, @NonNull Medicina newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Medicina oldItem, @NonNull Medicina newItem) {
            return oldItem.equals(newItem);
        }
    };

}
