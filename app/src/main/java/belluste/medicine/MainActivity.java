package belluste.medicine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import belluste.medicine.model.AppViewModel;
import belluste.medicine.model.MedArchiviata;
import belluste.medicine.model.Medicina;

import static android.graphics.Typeface.BOLD;
import static belluste.medicine.AddActivity.EXTRA_MEDICINA;

public class MainActivity extends AppCompatActivity {

    public static final String PREF = "belluste.medicine.preferences";
    public static final String ARMADIETTO = "belluste.medicine.armadietto";
    public static final String ARCHIVIO = "belluste.medicine.archivio";
    public static final String HOME = "belluste.medicine.home";
    public static final int REQUEST_CODE = 1;
    public static SpannableStringBuilder sb;

    private FragmentManager fragmentManager;
    private AppViewModel viewModel;
    private SharedPreferences preferences;
    private Gson gson;
    private boolean doubleBack;
    private boolean scadenzeControllate;
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
            scadenzeControllate = false;
        } else {
            selected = savedInstanceState.getInt("selected");
            if (selected == 1) {
                homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            } else if (selected == 2) {
                armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            } else if (selected == 3) {
                archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }
            scadenzeControllate = savedInstanceState.getBoolean("scadenze");
        }

        //recupera armadietto salvato
        String armadiettoString = preferences.getString(ARMADIETTO, null);
        LinkedList<Medicina> armadietto;
        if (armadiettoString != null) {
            Type collectionType = new TypeToken<LinkedList<Medicina>>(){}.getType();
            armadietto = new LinkedList<>(gson.fromJson(armadiettoString, collectionType));
        } else {
            armadietto = new LinkedList<>();
        }
        viewModel.SetContenutoArmadietto(armadietto);

        //recupera archivio salvato
        String archivioString = preferences.getString(ARCHIVIO, null);
        LinkedList<MedArchiviata> archivio;
        if (archivioString != null) {
            Type collectionType = new TypeToken<LinkedList<MedArchiviata>>(){}.getType();
            archivio = new LinkedList<>(gson.fromJson(archivioString, collectionType));
        } else {
            archivio = new LinkedList<>();
        }
        viewModel.SetContenutoArchivio(archivio);

        //recupera home
        String homeString = preferences.getString(HOME, null);
        ArrayList<Integer> home;
        if (homeString != null) {
            Type collectionType = new TypeToken<ArrayList<Integer>>(){}.getType();
            home = new ArrayList<>(gson.fromJson(homeString, collectionType));
        } else {
            home = new ArrayList<>();
        }
        viewModel.SetShortcutHome(home);

        if (!scadenzeControllate) {
            ControlloScadenze();
            scadenzeControllate = true;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected", selected);
        outState.putBoolean("scadenze", scadenzeControllate);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.findFragmentByTag("scheda_medicina") != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArmadiettoFragment.class, null, "armadietto").commit();
        } else if (fragmentManager.findFragmentByTag("scheda_archivio") != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArchivioFragment.class, null, "archivio").commit();
        } else {
            if (doubleBack) {
                super.onBackPressed();
            } else {
                doubleBack = true;
                Toast.makeText(this, R.string.doppio_back, Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBack = false, 2000);
            }
        }
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
            int result = viewModel.AddMedicina(medicina);
            if(result != -1) {
                viewModel.UnisciMedicine(medicina, result);
            }
            Toast.makeText(this, R.string.operazione_completata, Toast.LENGTH_LONG).show();
            viewModel.AggiornaArmadietto();
            SalvaArmadietto();
            SalvaHome();
        } else if (requestCode==REQUEST_CODE && resultCode==RESULT_CANCELED) {
            Toast.makeText(this, R.string.annullato, Toast.LENGTH_LONG).show();
        }
    }

    public void SalvaArmadietto() {
        String daSalvare = gson.toJson(viewModel.listaAttivi());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ARMADIETTO, daSalvare).apply();
    }

    public void SalvaArchivio() {
        String daSalvare = gson.toJson(viewModel.listaArchiviati());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ARCHIVIO, daSalvare).apply();
    }

    public void SalvaHome() {
        String daSalvare = gson.toJson(viewModel.listaIndexHome());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HOME, daSalvare).apply();
    }

    public void InHome() {
        homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        selected = 1;
    }

    public void InArmadietto() {
        homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        selected = 2;
    }

    public void InArchivio() {
        homeBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        armadiettoBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        archivioBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
        selected = 3;
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

    public void ControlloScadenze() {
        java.text.DateFormat df = DateFormat.getDateFormat(this);
        sb = new SpannableStringBuilder();
        long oggi = Calendar.getInstance(getResources().getConfiguration().locale).getTimeInMillis();
        int start = 0;
        for (Medicina m : viewModel.listaAttivi()) {
            try {
                Date scadenza = df.parse(m.getScadenza());
                assert scadenza != null;
                long diff = scadenza.getTime() - oggi;
                if (diff <= 0) {
                    sb.append(m.getNome()).append(" ").append(m.getTipo())
                            .setSpan(new ForegroundColorSpan(Color.RED), start, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(BOLD), start, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.append(getString(R.string.scaduto)).append("\n");
                    start = sb.length();
                } else if (diff < 2629800000L) {
                    sb.append(m.getNome()).append(" ").append(m.getTipo())
                            .setSpan(new StyleSpan(BOLD), start, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.append(getString(R.string.in_scadenza)).append("\n");
                    start = sb.length();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}