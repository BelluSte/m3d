package belluste.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import belluste.medicine.model.Medicina;

public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_MEDICINA = "belluste.medicine.medicina";

    private Calendar myCalendar;
    private EditText etScad, etNome, etNote, etNum, etCont;
    private RadioButton rbCompres, rbBust, rbCrema, rbScir, rbVari;
    private RadioGroup rgType;
    private Button btnAnnulla, btnOK;
    private boolean quantVer;
    private Intent replyIntent;
    private int t = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUI();
        replyIntent = new Intent();

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        etScad.setOnClickListener(v -> new DatePickerDialog(AddActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        rgType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId==rbCompres.getId()) {
                if (!etCont.isEnabled()) {
                    etCont.setEnabled(true);
                }
                t = 1;
                quantVer = true;
            } else if (checkedId==rbBust.getId()) {
                if (!etCont.isEnabled()) {
                    etCont.setEnabled(true);
                }
                t = 2;
                quantVer = true;
            } else if (checkedId==rbCrema.getId()) {
                if (etCont.isEnabled()) {
                    etCont.getText().clear();
                    etCont.setEnabled(false);
                }
                t = 3;
                quantVer = false;
            } else if (checkedId==rbScir.getId()) {
                if (etCont.isEnabled()) {
                    etCont.getText().clear();
                    etCont.setEnabled(false);
                }
                t = 4;
                quantVer = false;
            } else if (checkedId==rbVari.getId()) {
                if (!etCont.isEnabled()) {
                    etCont.setEnabled(true);
                }
                t = 5;
                quantVer = true;
            }
        });

        btnAnnulla.setOnClickListener(v -> {
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

        btnOK.setOnClickListener(v -> {
            if (etNome.getText().length()<=1) {
                incompleto();
            } else {
                String nome = etNome.getText().toString();

                if (t == -1) {
                    incompleto();
                } else {
                    String tipo = null;
                    switch (t) {
                        case 1:
                            tipo = getString(R.string.compresse);
                            break;
                        case 2:
                            tipo = getString(R.string.bustine);
                            break;
                        case 3:
                            tipo = getString(R.string.crema);
                            break;
                        case 4:
                            tipo = getString(R.string.sciroppo);
                            break;
                        case 5:
                            tipo = getString(R.string.vari);
                            break;
                    }

                    if (etNum.getText().length() == 0 || Integer.parseInt(etNum.getText().toString()) == 0) {
                        incompleto();
                    } else {
                        int confezioni = Integer.parseInt(etNum.getText().toString());

                        if ((t==1 || t==2) && (etCont.getText().length()==0 || Integer.parseInt(etCont.getText().toString())==0)) {
                            incompleto();
                        } else {
                            int quantita;
                            if (quantVer) {
                                if (t==5 && (etCont.getText().length()==0 || Integer.parseInt(etCont.getText().toString())==0)) {
                                    quantita = -1;
                                } else {
                                    quantita = Integer.parseInt(etCont.getText().toString());
                                }
                            } else {
                                quantita = -1;
                            }

                            if (etScad.getText().length() == 0) {
                                incompleto();
                            } else {
                                String scadenza = etScad.getText().toString();

                                String note;
                                if (etNote.getText().length() == 0) {
                                    note = "";
                                } else {
                                    note = etNote.getText().toString();
                                }

                                Date data = Calendar.getInstance().getTime();
                                java.text.DateFormat df = DateFormat.getDateFormat(this);
                                String dataAgg = df.format(data);
                                Medicina medicina = new Medicina(nome.trim(), tipo, confezioni, quantita, scadenza, note, dataAgg);
                                replyIntent.putExtra(EXTRA_MEDICINA, medicina);
                                setResult(RESULT_OK, replyIntent);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }

    private void initUI() {
        etScad = findViewById(R.id.et_scadenza);
        etNome = findViewById(R.id.et_nome);
        etNote = findViewById(R.id.et_note);
        etNum = findViewById(R.id.et_num1);
        etCont = findViewById(R.id.et_num2);
        rbBust = findViewById(R.id.rb_2);
        rbCompres = findViewById(R.id.rb_1);
        rbCrema = findViewById(R.id.rb_3);
        rbScir = findViewById(R.id.rb_4);
        rbVari = findViewById(R.id.rb_5);
        btnAnnulla = findViewById(R.id.btn_annulla);
        btnOK = findViewById(R.id.btn_ok);
        rgType = findViewById(R.id.radioGroup);
    }

    private void updateLabel() {
        java.text.DateFormat df = DateFormat.getDateFormat(this);
        etScad.setText(df.format(myCalendar.getTime()));
    }

    private void incompleto() {
        Toast toast = Toast.makeText(this, R.string.completa_tutti_campi, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}