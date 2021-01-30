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
import android.widget.TextView;

import belluste.medicine.model.ArcListAdapter;
import belluste.medicine.model.AppViewModel;


public class ArchivioFragment extends Fragment {

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

        AppViewModel viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        ArcListAdapter adapter = new ArcListAdapter(viewModel.listaArchiviati());
        ArchivioRV.setLayoutManager(new LinearLayoutManager(getContext()));
        ArchivioRV.setAdapter(adapter);

        if (viewModel.listaArchiviati().size()>0) {
            EmptyTV.setVisibility(View.GONE);
        }
    }

}