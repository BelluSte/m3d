package belluste.medicine;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import belluste.medicine.model.Medicina;

public class AddActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText etScad, etNome, etNote, etNum, etCont;
    private CheckBox cbCompres, cbBust, cbCrema, cbScir, cbVari;
    private Button btnAnnulla, btnOK;
    private boolean quantVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUI();
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etScad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {new DatePickerDialog(AddActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNome.getText().length()<=1) {
                    incompleto();
                    return;
                }
                String nome = etNome.getText().toString();

                int tipo = checkTipo();
                if (tipo == -1) {
                    incompleto();
                    return;
                }

                if (etNum.getText().length()==0) {
                    incompleto();
                    return;
                }
                int confezioni = Integer.parseInt(etNum.toString());

                int quantita;
                if (quantVer) {
                    if (etCont.getText().length()!=0) {
                        quantita = Integer.parseInt(etCont.getText().toString());
                    } else if (etCont.getText().length()==0 && tipo==5) {
                        quantita = -1;
                    } else {
                        incompleto();
                        return;
                    }
                } else {
                    quantita = -1;
                }

                if (etScad.getText().length()==0) {
                    incompleto();
                    return;
                }
                String scadenza = etScad.getText().toString();

                String note;
                if (etNote.getText().length()==0) {
                    note = getString(R.string.nessuna_nota);
                } else {
                    note = etNote.getText().toString();
                }

                //Medicina medicina = new Medicina();
            }
        });
    }

    private void initUI() {
        etScad = findViewById(R.id.et_scadenza);
        etNome = findViewById(R.id.et_nome);
        etNote = findViewById(R.id.et_note);
        etNum = findViewById(R.id.et_num1);
        etCont = findViewById(R.id.et_num2);
        cbBust = findViewById(R.id.cb_2);
        cbCompres = findViewById(R.id.cb_1);
        cbCrema = findViewById(R.id.cb_3);
        cbScir = findViewById(R.id.cb_4);
        cbVari = findViewById(R.id.cb_5);
        btnAnnulla = findViewById(R.id.btn_annulla);
        btnOK = findViewById(R.id.btn_ok);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
        etScad.setText(sdf.format(myCalendar.getTime()));
    }

    private int checkTipo() {
        if (cbCompres.isChecked()) {
            quantVer = true;
            return 1;
        } else if (cbBust.isChecked()) {
            quantVer = true;
            return 2;
        } else if (cbCrema.isChecked()) {
            quantVer = false;
            return 3;
        } else if (cbScir.isChecked()) {
            quantVer = false;
            return 4;
        } else if (cbVari.isChecked()){
            quantVer = true;
            return 5;
        } else {
            return -1;
        }
    }

    private void incompleto() {
        Toast.makeText(this, R.string.completa_tutti_campi, Toast.LENGTH_LONG).show();
    }

    public void clickOnCheckbox(View view) {
        int[] daControllare = new int[5];
        daControllare[0] = cbCompres.getId();
        daControllare[1] = cbBust.getId();
        daControllare[2] = cbCrema.getId();
        daControllare[3] = cbScir.getId();
        daControllare[4] = cbVari.getId();
        if (((CheckBox)view).isChecked()) {
            for (int i = 0; i < 5; i++) {
                if (view.getId() != daControllare[i]) {
                    switch (i) {
                        case 0:
                            if (cbCompres.isChecked()) {
                                cbCompres.setChecked(false);
                                break;
                            }
                        case 1:
                            if (cbBust.isChecked()) {
                                cbBust.setChecked(false);
                                break;
                            }
                        case 2:
                            if (cbCrema.isChecked()) {
                                cbCrema.setChecked(false);
                                break;
                            }
                        case 3:
                            if (cbScir.isChecked()) {
                                cbScir.setChecked(false);
                                break;
                            }
                        case 4:
                            if (cbVari.isChecked()) {
                                cbVari.setChecked(false);
                                break;
                            }
                    }
                }
            }
        }
    }
}