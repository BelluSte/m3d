package belluste.medicine.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import belluste.medicine.R;

public class ArcInfoViewHolder extends RecyclerView.ViewHolder {

    private int mPosition;
    private final TextView mDataAgg, mDataArc, mNote;

    public ArcInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            mPosition = getAdapterPosition();
            //TODO: click listener info
        });
        mDataAgg = itemView.findViewById(R.id.tv_arc_data_agg);
        mDataArc = itemView.findViewById(R.id.tv_arc_data_arc);
        mNote = itemView.findViewById(R.id.tv_arc_note);
    }

    public void bind(String dataAgg, String dataArc, String note) {
        mDataAgg.setText(dataAgg);
        mDataArc.setText(dataArc);
        if (note.length() > 0) {
            mNote.setText(note);
        }
    }

    static ArcInfoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_info_arc, parent, false);
        return new ArcInfoViewHolder(view);
    }
}
