package belluste.medicine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import belluste.medicine.model.ArcInfoListAdapter;
import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.MedArchiviata;


public class SchedaArchivioFragment extends Fragment {

    private static final String POS = "belluste.medicine.posizione";

    private int mPos;
    private ArmadiettoViewModel viewModel;
    private MedArchiviata medicina;

    private TextView mNome, mTipo, mQuantita;
    private Button btnDelete;
    private RecyclerView rvInfo;

    public SchedaArchivioFragment() {
    }

    public static SchedaArchivioFragment newInstance(int posizione) {
        SchedaArchivioFragment fragment = new SchedaArchivioFragment();
        Bundle args = new Bundle();
        args.putInt(POS, posizione);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPos = getArguments().getInt(POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scheda_archivio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: cancellare medarchiviata
            }
        });

        viewModel = new ViewModelProvider(requireActivity()).get(ArmadiettoViewModel.class);
        medicina = viewModel.getMedArchiviata(mPos);

        mNome.setText(medicina.getNome());
        mTipo.setText(medicina.getTipo());
        mQuantita.setText(medicina.getQuantita());

        rvInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        ArcInfoListAdapter adapter = new ArcInfoListAdapter(medicina.getInfo());
        rvInfo.setAdapter(adapter);
    }

    private void initUI(View v) {
        mNome = v.findViewById(R.id.tv_scheda_arc_nome);
        mTipo = v.findViewById(R.id.tv_scheda_arc_tipo);
        mQuantita = v.findViewById(R.id.tv_scheda_arc_quantita);
        btnDelete = v.findViewById(R.id.btn_delete_arc);
        rvInfo = v.findViewById(R.id.rv_lista_info_med_archiviata);
    }
}