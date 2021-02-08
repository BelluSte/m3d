package belluste.medicine.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Objects;

public class Medicina implements Parcelable {

    private final String mNome;
    private final String mTipo;          //1=compresse; 2=bustine; 3=crema; 4=sciroppo 5=altro
    private int mConfezioni;
    private final int mQuantita;      //-1=indefinita
    private final String mScadenza;
    private String mNote;
    private final String mDataAgg;
    private int mTotale = 0;

    public Medicina(String nome, String tipo, int confezioni, int quantita, String scadenza, String note, String dataAgg) {
        this.mNome = nome;
        this.mTipo = tipo;
        this.mConfezioni = confezioni;
        this.mQuantita = quantita;
        this.mScadenza = scadenza;
        this.mNote = note;
        this.mDataAgg = dataAgg;
        if (quantita != -1) {
            this.mTotale = confezioni * quantita;
        }
    }

    protected Medicina(Parcel in) {
        mNome = in.readString();
        mTipo = in.readString();
        mConfezioni = in.readInt();
        mQuantita = in.readInt();
        mScadenza = in.readString();
        mNote = in.readString();
        mDataAgg = in.readString();
        mTotale = in.readInt();
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

    public String getScadenza() {
        return mScadenza;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public String getDataAgg() {
        return mDataAgg;
    }

    public int getTotale() {
        return mTotale;
    }

    public void setTotale(int mTotale) {
        this.mTotale = mTotale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicina medicina = (Medicina) o;
        return mTipo.equals(medicina.mTipo) &&
                mNome.equals(medicina.mNome) &&
                mScadenza.equals(medicina.mScadenza);
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
        dest.writeString(mDataAgg);
        dest.writeInt(mTotale);
    }

    //comparator per ordinare per nome
    public static final Comparator<Medicina> medNameComparator = (o1, o2) -> {
        String name1 = o1.getNome().toUpperCase();
        String name2 = o2.getNome().toUpperCase();
        return name1.compareTo(name2);
    };

}
