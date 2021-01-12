package belluste.medicine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.Medicina;


public class SchedaFragment extends Fragment {

    private static final String POS = "belluste.medicine.posizione";

    private int mPos, quantita, totale;
    private ArmadiettoViewModel viewModel;
    private Medicina medicina;

    private TextView mNome, mTipo, mQuantita, mScadenza, mTestoQuant, mTestoTot;
    private EditText mNote, mConfezioni, mTotale;
    private Button btnAddHome, btnEdit, btnDelete, btnPrendi, btnArchivia;

    public SchedaFragment() {
    }

    public static SchedaFragment newInstance(Integer posizione) {
        SchedaFragment fragment = new SchedaFragment();
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
        return inflater.inflate(R.layout.fragment_scheda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        clickListeners();

        viewModel = new ViewModelProvider(requireActivity()).get(ArmadiettoViewModel.class);
        medicina = viewModel.getMedicina(mPos);

        compilaScheda();
    }

    private void initUI(View v) {
        mNome = v.findViewById(R.id.tv_scheda_nome);
        mTipo = v.findViewById(R.id.tv_scheda_tipo);
        mQuantita = v.findViewById(R.id.tv_scheda_quantita);
        mScadenza = v.findViewById(R.id.tv_scheda_scadenza);
        mNote = v.findViewById(R.id.et_scheda_note);
        mConfezioni = v.findViewById(R.id.et_scheda_confezioni);
        mTotale = v.findViewById(R.id.ed_scheda_totale);
        btnAddHome = v.findViewById(R.id.btn_add_home);
        btnEdit = v.findViewById(R.id.btn_edit);
        btnDelete = v.findViewById(R.id.btn_delete);
        btnPrendi = v.findViewById(R.id.btn_prendi);
        mTestoQuant = v.findViewById(R.id.tv_scatola_da);
        mTestoTot = v.findViewById(R.id.tv_totale);
        btnArchivia = v.findViewById(R.id.btn_archivia);
    }

    private void compilaScheda() {
        mNome.setText(medicina.getNome());
        mTipo.setText(medicina.getTipo());
        quantita = medicina.getQuantita();
        if (quantita != -1) {
            mQuantita.setText(String.valueOf(quantita));
            totale = medicina.getConfezioni() * medicina.getQuantita();
            mTotale.setText(String.valueOf(totale));
            if (totale == 0) {
                btnPrendi.setVisibility(View.GONE);
            }
        } else {
            mTestoQuant.setVisibility(View.GONE);
            mTestoTot.setVisibility(View.GONE);
            mQuantita.setVisibility(View.GONE);
            btnPrendi.setVisibility(View.GONE);
            mTotale.setVisibility(View.GONE);
        }
        mScadenza.setText(medicina.getScadenza());
        if (medicina.getNote().length() > 0) {
            mNote.setText(medicina.getNote());
        }
        mConfezioni.setText(String.valueOf(medicina.getConfezioni()));

    }

    private void clickListeners() {
        btnAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnPrendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnArchivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: setta medicina come archiviate e apre fragmentArchivio
                //TODO: aggiornare viewholder o listadapter per archivio, deve aprire fragmentscheda differente
            }
        });
    }
}