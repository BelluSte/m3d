package belluste.medicine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.MedListAdapter;
import belluste.medicine.model.Medicina;


public class ArchivioFragment extends Fragment {

    private ArmadiettoViewModel viewModel;

    public ArchivioFragment() {
        super(R.layout.fragment_archivio);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archivio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView EmptyTV = view.findViewById(R.id.tv_empty_archivio);
        RecyclerView ArchivioRV = view.findViewById(R.id.rv_archivio);

        viewModel = new ViewModelProvider(requireActivity()).get(ArmadiettoViewModel.class);

        MedListAdapter adapter = new MedListAdapter();
        ArchivioRV.setLayoutManager(new LinearLayoutManager(getContext()));

        /*viewModel.contenuto.observe(getViewLifecycleOwner(), armadietto -> {
            adapter.submitList(armadietto.listaArchiviati());
        });*/

        final Observer<LinkedList<Medicina>> observer = new Observer<LinkedList<Medicina>>() {
            @Override
            public void onChanged(LinkedList<Medicina> lista) {
                adapter.submitList(lista);
                //Log.d("prova", "onChanged called");
            }
        };
        viewModel.getArchiviati().observe(getViewLifecycleOwner(), observer);

        ArchivioRV.setAdapter(adapter);
        if (viewModel.listaArchiviati().size()>0) {
            EmptyTV.setVisibility(View.GONE);
        }
    }

}