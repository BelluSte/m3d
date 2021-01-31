package belluste.medicine.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class MedArchiviata implements Parcelable {

    private final String mNome;
    private final String mTipo;
    private final int mQuantita;
    private ArrayList<Info> mInfo;

    public MedArchiviata(Medicina med, String dataArc) {
        this.mNome = med.getNome();
        this.mTipo = med.getTipo();
        this.mQuantita = med.getQuantita();
        mInfo = new ArrayList<>();
        mInfo.add(new Info(med, dataArc));
    }

    protected MedArchiviata(Parcel in) {
        mNome = in.readString();
        mTipo = in.readString();
        mQuantita = in.readInt();
        in.readTypedList(mInfo, Info.CREATOR);
    }

    public void AddInfo(String note, String dataAgg, String dataArc) {
        mInfo.add(new Info(note, dataAgg, dataArc));
    }

    public static final Creator<MedArchiviata> CREATOR = new Creator<MedArchiviata>() {
        @Override
        public MedArchiviata createFromParcel(Parcel in) {
            return new MedArchiviata(in);
        }

        @Override
        public MedArchiviata[] newArray(int size) {
            return new MedArchiviata[size];
        }
    };

    public String getNome() {
        return mNome;
    }

    public String getTipo() {
        return mTipo;
    }

    public int getQuantita() {
        return mQuantita;
    }

    public ArrayList<Info> getInfo() {
        return mInfo;
    }

    public String getNote(int pos) {
        return mInfo.get(pos).getNote();
    }

    public String getDataAgg(int pos) {
        return mInfo.get(pos).getDataAgg();
    }

    public String getDataArc(int pos) {
        return mInfo.get(pos).getDataArc();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNome);
        dest.writeString(mTipo);
        dest.writeInt(mQuantita);
        dest.writeTypedList(mInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedArchiviata that = (MedArchiviata) o;
        return mQuantita == that.mQuantita &&
                mNome.equals(that.mNome) &&
                mTipo.equals(that.mTipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNome, mTipo, mQuantita);
    }

    //comparator per ordinare per nome
    public static final Comparator<MedArchiviata> medNameComparator = (o1, o2) -> {
        String name1 = o1.getNome().toUpperCase();
        String name2 = o2.getNome().toUpperCase();
        return name1.compareTo(name2);
    };


    // info delle medicine archiviate
    public static class Info implements Parcelable {

        final String mNote;
        final String mDataAgg;
        final String mDataArc;

        public Info(Medicina med, String dataArc) {
            this.mNote = med.getNote();
            this.mDataAgg = med.getDataAgg();
            this.mDataArc = dataArc;
        }

        public Info(String mNote, String mDataAgg, String mDataArc) {
            this.mNote = mNote;
            this.mDataAgg = mDataAgg;
            this.mDataArc = mDataArc;
        }

        protected Info(Parcel in) {
            mNote = in.readString();
            mDataAgg = in.readString();
            mDataArc = in.readString();
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel in) {
                return new Info(in);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
            }
        };

        public String getNote() {
            return mNote;
        }

        public String getDataAgg() {
            return mDataAgg;
        }

        public String getDataArc() {
            return mDataArc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mNote);
            dest.writeString(mDataAgg);
            dest.writeString(mDataArc);
        }
    }
}
