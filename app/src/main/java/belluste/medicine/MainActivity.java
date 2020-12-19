package belluste.medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentHost, HomeFragment.class, null, "home")
                    .commit();
        }

        initUI();
    }

    private void initUI() {
        Button homeBtn = findViewById(R.id.homeButton);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("home") == null) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentHost, HomeFragment.class, null, "home").commit();
                }
            }
        });
        Button armadiettoBtn = findViewById(R.id.armadiettoButton);
        armadiettoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("armadietto") == null) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArmadiettoFragment.class, null, "armadietto").commit();
                }
            }
        });
        Button archivioBtn = findViewById(R.id.archivioButton);
        archivioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("archivio") == null) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentHost, ArchivioFragment.class, null, "archivio").commit();
                }
            }
        });

        FloatingActionButton addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
    }
}