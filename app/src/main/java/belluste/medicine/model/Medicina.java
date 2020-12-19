package belluste.medicine.model;

import java.util.Objects;

public class Medicina {

    private final String mNome;
    private final String mTipo;          //1=compresse; 2=bustine; 3=crema; 4=sciroppo 5=altro
    private int mConfezioni;
    private int mQuantita;      //-1=indefinita
    private String mScadenza;
    private String mNote;
    private boolean mArchiviato;

    public Medicina (String nome, String tipo, int confezioni, int quantita, String scadenza, String note) {
        this.mNome = nome;
        this.mTipo = tipo;
        this.mConfezioni = confezioni;
        this.mQuantita = quantita;
        this.mScadenza = scadenza;
        this.mNote = note;
        this.mArchiviato = false;
    }

    public String getNome() {
        return mNome;
    }

    public String getTipo() {
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
        return mTipo.equals(medicina.mTipo) &&
                mNome.equals(medicina.mNome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNome, mTipo);
    }
}
