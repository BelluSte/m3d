package belluste.medicine.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;


public class ArmadiettoViewModel extends ViewModel {

    private final MutableLiveData<LinkedList<Medicina>> contenutoAttivi = new MutableLiveData<>();
    private final MutableLiveData<LinkedList<MedArchiviata>> contenutoArchiviati = new MutableLiveData<>();

    private Armadietto armadietto;
    private Archivio archivio;

    public void SetContenutoArmadietto(Armadietto armadietto) {
        contenutoAttivi.setValue(armadietto.getContenuto());
        this.armadietto = armadietto;
    }

    public void SetContenutoArchivio(Archivio archivio) {
        contenutoArchiviati.setValue(archivio.getContenuto());
        this.archivio = archivio;
    }

    public LiveData<LinkedList<Medicina>> getAttivi() {
        return contenutoAttivi;
    }

    public LiveData<LinkedList<MedArchiviata>> getArchiviati() {
        return contenutoArchiviati;
    }

    public LinkedList<Medicina> listaAttivi() {
        return contenutoAttivi.getValue();
    }

    public LinkedList<MedArchiviata> listaArchiviati() {
        return contenutoArchiviati.getValue();
    }

    public Medicina getMedicina(int posizione) {
        return armadietto.getContenuto().get(posizione);
    }

    public MedArchiviata getMedArchiviata(int posizione) {
        return archivio.getContenuto().get(posizione);
    }

    public void RemoveMedicina(Medicina medicina) {
        armadietto.removeMedicina(medicina);
        contenutoAttivi.setValue(armadietto.getContenuto());
    }

    public void ArchiviaMedicina(Medicina medicina, String dataArc) {
        MedArchiviata med = new MedArchiviata(medicina, dataArc);
        armadietto.removeMedicina(medicina);
        contenutoAttivi.setValue(armadietto.getContenuto());
        archivio.addMed(med);
        contenutoArchiviati.setValue(archivio.getContenuto());
    }

    public void RemoveMedArchiviata(MedArchiviata med) {
        archivio.removeMed(med);
        contenutoArchiviati.setValue(archivio.getContenuto());
    }
}
