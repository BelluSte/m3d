package belluste.medicine.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ArcInfoListAdapter extends ListAdapter<MedArchiviata.Info, ArcInfoViewHolder> {

    public ArcInfoListAdapter() {
        super(INFO_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public ArcInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ArcInfoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArcInfoViewHolder holder, int position) {
        holder.bind(getItem(position).getDataAgg(), getItem(position).getDataArc(), getItem(position).getNote());
    }

    public static final DiffUtil.ItemCallback<MedArchiviata.Info> INFO_ITEM_CALLBACK = new DiffUtil.ItemCallback<MedArchiviata.Info>() {
        @Override
        public boolean areItemsTheSame(@NonNull MedArchiviata.Info oldItem, @NonNull MedArchiviata.Info newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MedArchiviata.Info oldItem, @NonNull MedArchiviata.Info newItem) {
            return false;
        }
    };

}
