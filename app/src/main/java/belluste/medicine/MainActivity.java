package belluste.medicine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

import belluste.medicine.model.Archivio;
import belluste.medicine.model.Armadietto;
import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.MedArchiviata;
import belluste.medicine.model.Medicina;

import static belluste.medicine.AddActivity.EXTRA_MEDICINA;

public class MainActivity extends AppCompatActivity {

    public static final String PREF = "belluste.medicine.preferences";
    public static final String ARMADIETTO = "belluste.medicine.armadietto";
    public static final String ARCHIVIO = "belluste.medicine.archivio";
    public static final int REQUEST_CODE = 1;

    private FragmentManager fragmentManager;
    private ArmadiettoViewModel viewModel;
    private SharedPreferences preferences;
    private Armadietto armadietto;
    private Archivio archivio;
    private Gson gson;

    private Button homeBtn, armadiettoBtn, archivioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        viewModel = new ViewModelProvider(this).get(ArmadiettoViewModel.class);
        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        gson = new Gson();

        initUI();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentHost, HomeFragment.class, null, "home")
                    .commit();
            homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        }

        //recupera armadietto salvato
        String armadiettoString = preferences.getString(ARMADIETTO, null);
        if (armadiettoString != null) {
            Type collectionType = new TypeToken<LinkedList<Medicina>>(){}.getType();
            armadietto = new Armadietto(gson.fromJson(armadiettoString, collectionType));
        } else {
            armadietto = new Armadietto();
        }
        viewModel.SetContenutoArmadietto(armadietto);

        //recupera archivio salvato
        String archivioString = preferences.getString(ARCHIVIO, null);
        if (archivioString != null) {
            Type collectionType = new TypeToken<LinkedList<MedArchiviata>>(){}.getType();
            archivio = new Archivio(gson.fromJson(archivioString, collectionType));
        } else {
            archivio = new Archivio();
        }
        viewModel.SetContenutoArchivio(archivio);
    }

    private void initUI() {
        homeBtn = findViewById(R.id.homeButton);
        homeBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("home") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, HomeFragment.class, null, "home").commit();
                InHome();
            }
        });
        armadiettoBtn = findViewById(R.id.armadiettoButton);
        armadiettoBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("armadietto") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArmadiettoFragment.class, null, "armadietto").commit();
                InArmadietto();
            }
        });
        archivioBtn = findViewById(R.id.archivioButton);
        archivioBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("archivio") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArchivioFragment.class, null, "archivio").commit();
                InArchivio();
            }
        });

        FloatingActionButton addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(i, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            Medicina medicina = data.getParcelableExtra(EXTRA_MEDICINA);
            if(armadietto.addMedicina(medicina)) {
                Toast.makeText(this, R.string.medicina_aggiunta, Toast.LENGTH_LONG).show();
                viewModel.MedicinaAggiunta();
                SalvaArmadietto();
            } else {
                Toast.makeText(this, R.string.medicina_gia_presente, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode==REQUEST_CODE && resultCode==RESULT_CANCELED) {
            Toast.makeText(this, R.string.annullato, Toast.LENGTH_LONG).show();
        }
    }

    public void SalvaArmadietto() {
        String daSalvare = gson.toJson(armadietto.getContenuto());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ARMADIETTO, daSalvare).apply();
    }

    public void SalvaArchivio() {
        String daSalvare = gson.toJson(archivio.getContenuto());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ARCHIVIO, daSalvare).apply();
    }

    public void InHome() {
        homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
    }

    public void InArmadietto() {
        homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
    }

    public void InArchivio() {
        homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
    }
}