package belluste.medicine;

import android.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.Medicina;


public class SchedaFragment extends Fragment {

    private static final String POS = "belluste.medicine.posizione";

    private int mPos, totale, confezioni;
    private ArmadiettoViewModel viewModel;
    private Medicina medicina;
    private String myFormat;

    private TextView mNome, mTipo, mQuantita, mScadenza, mTestoQuant, mTestoTot;
    private EditText mNote, mConfezioni, mTotale;
    private Button btnAddHome, btnEdit, btnDelete, btnPrendi, btnArchivia, btnOK, btnAnnulla;

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

        myFormat = getString(R.string.day_format);
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
        btnOK = v.findViewById(R.id.btn_scheda_ok);
        btnAnnulla = v.findViewById(R.id.btn_scheda_annulla);
    }

    private void compilaScheda() {
        mNome.setText(medicina.getNome());
        mTipo.setText(medicina.getTipo());
        totale = medicina.getTotale();
        int quantita = medicina.getQuantita();
        if (quantita != -1) {
            mQuantita.setText(String.valueOf(quantita));
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
        confezioni = medicina.getConfezioni();
        mConfezioni.setText(String.valueOf(confezioni));
    }

    private void clickListeners() {
        btnAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: aggiunge a lista home
            }
        });

        btnEdit.setOnClickListener(v -> {
            mNote.setEnabled(true);
            mConfezioni.setEnabled(true);
            mTotale.setEnabled(true);
            btnAddHome.setVisibility(View.GONE);
            btnArchivia.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnOK.setVisibility(View.VISIBLE);
            btnAnnulla.setVisibility(View.VISIBLE);
        });

        btnAnnulla.setOnClickListener(v -> {
            mNote.setEnabled(false);
            mConfezioni.setEnabled(false);
            mTotale.setEnabled(false);
            btnAddHome.setVisibility(View.VISIBLE);
            btnArchivia.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnOK.setVisibility(View.GONE);
            btnAnnulla.setVisibility(View.GONE);
        });

        btnOK.setOnClickListener(v -> {
            medicina.setNote(mNote.getText().toString());
            medicina.setConfezioni(confezioni);
            medicina.setTotale(totale);
            mNote.setEnabled(false);
            mConfezioni.setEnabled(false);
            mTotale.setEnabled(false);
            btnAddHome.setVisibility(View.VISIBLE);
            btnArchivia.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnOK.setVisibility(View.GONE);
            btnAnnulla.setVisibility(View.GONE);
            ((MainActivity) requireActivity()).SalvaArmadietto();
        });

        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sei_sicuro)
                    .setCancelable(false)
                    .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel())
                    .setPositiveButton(R.string.si, (dialog, which) -> {
                        CancellaMedicina();
                        dialog.dismiss();
                    });
            builder.create().show();
        });

        btnPrendi.setOnClickListener(v -> {
            totale--;
            mTotale.setText(String.valueOf(totale));
            medicina.setTotale(totale);
            if (totale == 0) {
                btnPrendi.setVisibility(View.GONE);
                mConfezioni.setText(String.valueOf(0));
                medicina.setConfezioni(0);
            } else {
                if (totale % medicina.getQuantita() == 0) {
                    confezioni--;
                    mConfezioni.setText(String.valueOf(confezioni));
                    medicina.setConfezioni(confezioni);
                }
            }
            ((MainActivity) requireActivity()).SalvaArmadietto();
        });

        btnArchivia.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sei_sicuro)
                    .setCancelable(false)
                    .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel())
                    .setPositiveButton(R.string.si, (dialog, which) -> {
                        ArchiviaMedicina();
                        dialog.dismiss();
                    });
            builder.create().show();
        });
    }

    public void CancellaMedicina() {
        viewModel.RemoveMedicina(medicina);
        ((MainActivity) requireActivity()).SalvaArmadietto();
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentHost, ArmadiettoFragment.class, null).commit();
    }

    public void ArchiviaMedicina() {
        Date data = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
        String dataArc = sdf.format(data);
        viewModel.ArchiviaMedicina(medicina, dataArc);
        ((MainActivity) requireActivity()).SalvaArmadietto();
        ((MainActivity) requireActivity()).SalvaArchivio();
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentHost, ArchivioFragment.class, null).commit();
    }
}