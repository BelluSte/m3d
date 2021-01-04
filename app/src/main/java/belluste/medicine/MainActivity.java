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

import belluste.medicine.model.Armadietto;
import belluste.medicine.model.ArmadiettoViewModel;
import belluste.medicine.model.Medicina;

import static belluste.medicine.AddActivity.EXTRA_MEDICINA;

public class MainActivity extends AppCompatActivity {

    public static final String PREF = "belluste.medicine.preferences";
    public static final String ARMADIETTO = "belluste.medicine.armadietto";
    public static final int REQUEST_CODE = 1;

    private FragmentManager fragmentManager;
    private ArmadiettoViewModel viewModel;
    private SharedPreferences preferences;
    private Armadietto armadietto;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        viewModel = new ViewModelProvider(this).get(ArmadiettoViewModel.class);
        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        gson = new Gson();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentHost, HomeFragment.class, null, "home")
                    .commit();
        }

        initUI();

        String armadiettoString = preferences.getString(ARMADIETTO, null);
        if (armadiettoString != null) {
            Type collectionType = new TypeToken<LinkedList<Medicina>>(){}.getType();
            armadietto = new Armadietto(gson.fromJson(armadiettoString, collectionType));
        } else {
            armadietto = new Armadietto();
        }
        viewModel.SetContenuto(armadietto);
    }

    private void initUI() {
        Button homeBtn = findViewById(R.id.homeButton);
        homeBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("home") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, HomeFragment.class, null, "home").commit();
            }
        });
        Button armadiettoBtn = findViewById(R.id.armadiettoButton);
        armadiettoBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("armadietto") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArmadiettoFragment.class, null, "armadietto").commit();
            }
        });
        Button archivioBtn = findViewById(R.id.archivioButton);
        archivioBtn.setOnClickListener(v -> {
            if (fragmentManager.findFragmentByTag("archivio") == null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArchivioFragment.class, null, "archivio").commit();
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
            if(viewModel.AddMedicina(medicina)) {
                Toast.makeText(this, R.string.medicina_aggiunta, Toast.LENGTH_LONG).show();
                Salva();
            } else {
                Toast.makeText(this, R.string.medicina_gia_presente, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode==REQUEST_CODE && resultCode==RESULT_CANCELED) {
            Toast.makeText(this, R.string.annullato, Toast.LENGTH_LONG).show();
        }
    }

    private void Salva() {
        String daSalvare = gson.toJson(armadietto.getContenuto());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ARMADIETTO, daSalvare).apply();
    }
}