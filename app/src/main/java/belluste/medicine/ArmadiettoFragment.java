package belluste.medicine;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.MedListAdapter;
import belluste.medicine.model.Medicina;


public class ArmadiettoFragment extends Fragment {

    private ArmadiettoViewModel viewModel;

    public ArmadiettoFragment() {
        super(R.layout.fragment_armadietto);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_armadietto, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView EmptyTV = view.findViewById(R.id.tv_empty_armadietto);
        RecyclerView ArmadiettoRV = view.findViewById(R.id.rv_armadietto);

        viewModel = new ViewModelProvider(requireActivity()).get(ArmadiettoViewModel.class);

        MedListAdapter adapter = new MedListAdapter();
        ArmadiettoRV.setLayoutManager(new LinearLayoutManager(getContext()));

        //viewModel.getCon.observe(getViewLifecycleOwner(), armadietto -> adapter.submitList(armadietto.listaAttivi()));

        final Observer<LinkedList<Medicina>> observer = new Observer<LinkedList<Medicina>>() {
            @Override
            public void onChanged(LinkedList<Medicina> lista) {
                adapter.submitList(lista);
                //Log.d("prova", "onChanged called");
            }
        };
        viewModel.getAttivi().observe(getViewLifecycleOwner(), observer);

        ArmadiettoRV.setAdapter(adapter);

        DividerItemDecoration divider = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            divider.setDrawable(requireContext().getDrawable(R.drawable.divider));
        }
        ArmadiettoRV.addItemDecoration(divider);
        if (viewModel.listaAttivi().size() > 0) {
            EmptyTV.setVisibility(View.GONE);
        }

    }

}