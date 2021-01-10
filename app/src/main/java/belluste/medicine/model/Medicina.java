package belluste.medicine.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Objects;

public class Medicina implements Parcelable {

    private final String mNome;
    private final String mTipo;          //1=compresse; 2=bustine; 3=crema; 4=sciroppo 5=altro
    private int mConfezioni;
    private int mQuantita;      //-1=indefinita
    private String mScadenza;
    private String mNote;
    private boolean mArchiviato;

    public Medicina(String nome, String tipo, int confezioni, int quantita, String scadenza, String note) {
        this.mNome = nome;
        this.mTipo = tipo;
        this.mConfezioni = confezioni;
        this.mQuantita = quantita;
        this.mScadenza = scadenza;
        this.mNote = note;
        this.mArchiviato = false;
    }

    public Medicina(Medicina m, String nuovoNome) {
        this.mNome = nuovoNome;
        this.mTipo = m.getTipo();
        this.mConfezioni = m.getConfezioni();
        this.mQuantita = m.getQuantita();
        this.mScadenza = m.getScadenza();
        this.mNote = m.getNote();
        this.mArchiviato = m.getArchiviato();
    }

    protected Medicina(Parcel in) {
        mNome = in.readString();
        mTipo = in.readString();
        mConfezioni = in.readInt();
        mQuantita = in.readInt();
        mScadenza = in.readString();
        mNote = in.readString();
        mArchiviato = in.readByte() != 0;
    }

    public static final Creator<Medicina> CREATOR = new Creator<Medicina>() {
        @Override
        public Medicina createFromParcel(Parcel in) {
            return new Medicina(in);
        }

        @Override
        public Medicina[] newArray(int size) {
            return new Medicina[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNome);
        dest.writeString(mTipo);
        dest.writeInt(mConfezioni);
        dest.writeInt(mQuantita);
        dest.writeString(mScadenza);
        dest.writeString(mNote);
        dest.writeByte((byte) (mArchiviato ? 1 : 0));
    }

    //comparator per ordinare per nome
    public static Comparator<Medicina> medNameComparator = (o1, o2) -> {
        String name1 = o1.getNome().toUpperCase();
        String name2 = o2.getNome().toUpperCase();
        return name1.compareTo(name2);
    };

}
