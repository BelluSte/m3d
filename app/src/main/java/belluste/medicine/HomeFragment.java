package belluste.medicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import belluste.medicine.model.AppViewModel;
import belluste.medicine.model.HomeListAdapter;


public class HomeFragment extends Fragment {

    public static boolean salva = false;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdView adView = view.findViewById(R.id.adView);
        TextView EmptyTV = view.findViewById(R.id.tv_empty_home);
        RecyclerView HomeRV = view.findViewById(R.id.rv_home);
        TextView HomeAlert = view.findViewById(R.id.tv_home_alert);

        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        AppViewModel viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        HomeRV.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeListAdapter adapter = new HomeListAdapter(viewModel.listaHome());
        HomeRV.setAdapter(adapter);

        if (adapter.getItemCount() > 0) {
            EmptyTV.setVisibility(View.GONE);
        }

        if (MainActivity.scadenze.length() > 0) {
            HomeAlert.setText(MainActivity.scadenze);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (salva) {
            ((MainActivity)requireActivity()).SalvaHome();
            ((MainActivity)requireActivity()).SalvaArmadietto();
            salva = false;
        }
    }
}