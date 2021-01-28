package belluste.medicine;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import belluste.medicine.model.ArmadiettoViewModel;


public class InfoAppFragment extends Fragment {

    public InfoAppFragment() {
        super(R.layout.fragment_info_app);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_app, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView versioneTv = view.findViewById(R.id.tv_version);
        Button resetArmadiettoBtn = view.findViewById(R.id.btn_reset_armadietto);
        Button resetArchivioBtn = view.findViewById(R.id.btn_reset_archivio);

        ArmadiettoViewModel viewModel = new ViewModelProvider(requireActivity()).get(ArmadiettoViewModel.class);

        resetArmadiettoBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sei_sicuro)
                    .setMessage(R.string.reset_armadietto)
                    .setCancelable(false)
                    .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel())
                    .setPositiveButton(R.string.si, (dialog, which) -> {
                        viewModel.SvuotaArmadietto();
                        Toast.makeText(getContext(), R.string.operazione_completata, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    });
            builder.create().show();
        });

        resetArchivioBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sei_sicuro)
                    .setMessage(R.string.reset_archivio)
                    .setCancelable(false)
                    .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel())
                    .setPositiveButton(R.string.si, (dialog, which) -> {
                        viewModel.SvuotaArchivio();
                        Toast.makeText(getContext(), R.string.operazione_completata, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    });
            builder.create().show();
        });

        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            String appVersion = pInfo.versionName;
            versioneTv.setText(appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}