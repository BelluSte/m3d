package belluste.medicine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.Medicina;


public class SchedaArchivioFragment extends Fragment {

    private static final String POS = "belluste.medicine.posizione";

    private int mPos;
    private ArmadiettoViewModel viewModel;
    private Medicina medicina;

    private TextView mNome, mTipo, mQuantita, mDate;
    private EditText mNote;
    private Button btnEdit, btnDelete;

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

        viewModel = new ViewModelProvider(requireActivity()).get(ArmadiettoViewModel.class);
        medicina = viewModel.getMedicina(mPos);
    }

    private void initUI(View v) {
        mNome = v.findViewById(R.id.tv_scheda_arc_nome);
        mTipo = v.findViewById(R.id.tv_scheda_arc_tipo);
        mQuantita = v.findViewById(R.id.tv_scheda_arc_quantita);
        mNote = v.findViewById(R.id.et_scheda_arc_note);
        btnEdit = v.findViewById(R.id.btn_edit_arc);
        btnDelete = v.findViewById(R.id.btn_delete_arc);
        mDate = v.findViewById(R.id.tv_data_agg);
    }
}