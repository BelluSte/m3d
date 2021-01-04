package belluste.medicine.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import belluste.medicine.R;

public class MedViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvNome;

    public MedViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNome = itemView.findViewById(R.id.tv_item);
    }

    public void bind(String nome) {
        tvNome.setText(nome);
    }

    static MedViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MedViewHolder(view);
    }

}
