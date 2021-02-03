package belluste.medicine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import belluste.medicine.model.Archivio;
import belluste.medicine.model.Armadietto;
import belluste.medicine.model.AppViewModel;
import belluste.medicine.model.MedArchiviata;
import belluste.medicine.model.Medicina;

import static belluste.medicine.AddActivity.EXTRA_MEDICINA;

public class MainActivity extends AppCompatActivity {

    public static final String PREF = "belluste.medicine.preferences";
    public static final String ARMADIETTO = "belluste.medicine.armadietto";
    public static final String ARCHIVIO = "belluste.medicine.archivio";
    public static final String HOME = "belluste.medicine.home";
    public static final int REQUEST_CODE = 1;
    public static String scadenze;

    private FragmentManager fragmentManager;
    private AppViewModel viewModel;
    private SharedPreferences preferences;
    private Armadietto armadietto;
    private Archivio archivio;
    private ArrayList<Medicina> shortcutHome;
    private Gson gson;
    private boolean doubleBack;
    private int selected;

    private Button homeBtn, armadiettoBtn, archivioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        gson = new Gson();

        MobileAds.initialize(this, initializationStatus -> {
        });

        initUI();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentHost, HomeFragment.class, null, "home")
                    .commit();
            homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            selected = 1;
        } else {
            selected = savedInstanceState.getInt("selected");
            if (selected == 1) {
                homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            } else if (selected == 2) {
                armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            } else if (selected == 3) {
                archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }
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

        //recupera home
        String homeString = preferences.getString(HOME, null);
        if (homeString != null) {
            Type collectionType = new TypeToken<ArrayList<Medicina>>(){}.getType();
            shortcutHome = new ArrayList<>(gson.fromJson(homeString, collectionType));
        } else {
            shortcutHome = new ArrayList<>();
        }
        viewModel.SetShortcutHome(shortcutHome);

        scadenze = ControlloScadenze();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected", selected);
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
        } else {
            doubleBack = true;
            Toast.makeText(this, R.string.doppio_back, Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBack = false, 2000);
        }
    }

    private void initUI() {
        homeBtn = findViewById(R.id.homeButton);
        homeBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("home") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, HomeFragment.class, null, "home").commit();
                InHome();
                selected = 1;
            }
        });
        armadiettoBtn = findViewById(R.id.armadiettoButton);
        armadiettoBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("armadietto") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArmadiettoFragment.class, null, "armadietto").commit();
                InArmadietto();
                selected = 2;
            }
        });
        archivioBtn = findViewById(R.id.archivioButton);
        archivioBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("archivio") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArchivioFragment.class, null, "archivio").commit();
                InArchivio();
                selected = 3;
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
                Toast.makeText(this, R.string.operazione_completata, Toast.LENGTH_LONG).show();
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

    public void SalvaHome() {
        String daSalvare = gson.toJson(shortcutHome);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HOME, daSalvare).apply();
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

    public void OpenAppInfo(View view) {
        if (fragmentManager.findFragmentByTag("info") == null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHost, InfoAppFragment.class, null, "info").commit();
            homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
            armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
            archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
            selected = 0;
        }
    }

    public String ControlloScadenze() {
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.data_format), getResources().getConfiguration().locale);
        StringBuilder sb = new StringBuilder();
        long oggi = Calendar.getInstance(getResources().getConfiguration().locale).getTimeInMillis();
        for (Medicina m : armadietto.getContenuto()) {
            try {
                Date scadenza = df.parse(m.getScadenza());
                assert scadenza != null;
                long diff = scadenza.getTime() - oggi;
                if (diff <= 0) {
                    sb.append(m.getNome()).append(" ").append(m.getTipo()).append(getString(R.string.scaduto)).append("\n");
                } else if (diff < 2629800000L) {
                    sb.append(m.getNome()).append(" ").append(m.getTipo()).append(getString(R.string.in_scadenza)).append("\n");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                break;
            }
        }
        return sb.toString();
    }
}