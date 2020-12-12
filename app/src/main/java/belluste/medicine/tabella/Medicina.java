package belluste.medicine.tabella;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "tab_medicine")
public class Medicina {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome")
    private final String mNome;
    @ColumnInfo(name = "tipo")
    private final int mTipo;          //1=compresse; 2=bustine; 3=crema; 4=sciroppo
    @ColumnInfo(name = "confezioni")
    private int mConfezioni;
    @ColumnInfo(name = "quantita")
    private int mQuantita;      //-1=indefinita
    @ColumnInfo(name = "scadenza")
    private String mScadenza;
    @ColumnInfo(name = "note")
    private String mNote;
    @ColumnInfo(name = "archiviato")
    private boolean mArchiviato;

    public Medicina (@NonNull String nome, int tipo, int confezioni, int quantita, String scadenza, String note) {
        this.mNome = nome;
        this.mTipo = tipo;
        this.mConfezioni = confezioni;
        this.mQuantita = quantita;
        this.mScadenza = scadenza;
        this.mNote = note;
        this.mArchiviato = false;
    }

    @NonNull
    public String getNome() {
        return mNome;
    }

    public int getTipo() {
        return mTipo;
    }

    public int getConfezioni() {
        return mConfezioni;
    }

    public void setConfezioni(int mConfezioni) {
        this.mConfezioni = mConfezioni;
    }

    public int getQuantita() {
        return mQuantita;
    }

    public void setQuantita(int mQuantita) {
        this.mQuantita = mQuantita;
    }

    public String getScadenza() {
        return mScadenza;
    }

    public void setScadenza(String mScadenza) {
        this.mScadenza = mScadenza;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public boolean getArchiviato() {
        return mArchiviato;
    }

    public void setArchiviato(boolean mArchiviato) {
        this.mArchiviato = mArchiviato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicina medicina = (Medicina) o;
        return mTipo == medicina.mTipo &&
                mNome.equals(medicina.mNome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNome, mTipo);
    }
}
