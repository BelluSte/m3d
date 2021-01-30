package belluste.medicine.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import belluste.medicine.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    final TextView tvNome, tvTot, tvTipo;
    final Button btnPrendi;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNome = itemView.findViewById(R.id.tv_home_nome);
        tvTot = itemView.findViewById(R.id.tv_home_tot);
        tvTipo = itemView.findViewById(R.id.tv_home_tipo);
        btnPrendi = itemView.findViewById(R.id.btn_home_prendi);
    }

    static HomeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_home, parent, false);
        return new HomeViewHolder(view);
    }
}
