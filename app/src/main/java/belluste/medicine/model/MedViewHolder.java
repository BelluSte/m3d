package belluste.medicine.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import belluste.medicine.R;
import belluste.medicine.SchedaFragment;

public class MedViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvNome, tvTipo;
    private int mPosition;

    public MedViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            mPosition = getAdapterPosition();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            SchedaFragment scheda = SchedaFragment.newInstance(mPosition);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHost, scheda).commit();
        });
        tvNome = itemView.findViewById(R.id.tv_item_nome);
        tvTipo = itemView.findViewById(R.id.tv_item_tipo);
    }

    public void bind(String nome, String tipo) {
        tvNome.setText(nome);
        tvTipo.setText(tipo);
    }

    static MedViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MedViewHolder(view);
    }

}
