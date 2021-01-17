package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ArcListAdapter extends ListAdapter<MedArchiviata, ArcViewHolder> {

    public ArcListAdapter() {
        super(MEDARC_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public ArcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ArcViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArcViewHolder holder, int position) {
        holder.bind(getItem(position).getNome(), getItem(position).getTipo());
    }

    public static final DiffUtil.ItemCallback<MedArchiviata> MEDARC_ITEM_CALLBACK = new DiffUtil.ItemCallback<MedArchiviata>() {
        @Override
        public boolean areItemsTheSame(@NonNull MedArchiviata oldItem, @NonNull MedArchiviata newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MedArchiviata oldItem, @NonNull MedArchiviata newItem) {
            return oldItem.equals(newItem);
        }
    };

}
