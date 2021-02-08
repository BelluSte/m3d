package belluste.medicine.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.LinkedList;


public class AppViewModel extends ViewModel {

    private final MutableLiveData<LinkedList<Medicina>> contenutoAttivi = new MutableLiveData<>();

    private Armadietto armadietto;
    private Archivio archivio;
    private ArrayList<Integer> listaIndexHome;

    public void SetContenutoArmadietto(LinkedList<Medicina> armadiettoSalvato) {
        this.armadietto = new Armadietto(armadiettoSalvato);
        AggiornaArmadietto();
    }

    public void SetContenutoArchivio(LinkedList<MedArchiviata> archivioSalvato) {
        this.archivio = new Archivio(archivioSalvato);
    }

    public void SetShortcutHome(ArrayList<Integer> home) {
        this.listaIndexHome = new ArrayList<>(home);
    }

    public void AggiornaArmadietto() {
        contenutoAttivi.setValue(armadietto.getContenuto());
    }

    public LiveData<LinkedList<Medicina>> getAttivi() {
        return contenutoAttivi;
    }

    public LinkedList<Medicina> listaAttivi() {
        return contenutoAttivi.getValue();
    }

    public LinkedList<MedArchiviata> listaArchiviati() {
        return archivio.getContenuto();
    }

    public ArrayList<Integer> listaIndexHome() {
        return listaIndexHome;
    }

    public Medicina getMedicina(int posizione) {
        return armadietto.getContenuto().get(posizione);
    }

    public MedArchiviata getMedArchiviata(int posizione) {
        return archivio.getContenuto().get(posizione);
    }

    public boolean AddMedicina(Medicina medicina) {
        boolean result = armadietto.addMedicina(medicina);
        if (result) {
            AggiornaArmadietto();
        }
        return result;
    }

    public void RemoveMedicina(Medicina medicina) {
        armadietto.removeMedicina(medicina);
        AggiornaArmadietto();
    }

    public void ArchiviaMedicina(Medicina medicina, String dataArc) {
        MedArchiviata med = new MedArchiviata(medicina, dataArc);
        RemoveMedicina(medicina);
        AggiornaArmadietto();
        archivio.addMed(med);
    }

    public void RemoveMedArchiviata(MedArchiviata med) {
        archivio.removeMed(med);
    }

    public void SvuotaArmadietto() {
        armadietto.removeAll();
        listaIndexHome.clear();
        AggiornaArmadietto();
    }

    public void SvuotaArchivio() {
        archivio.removeAll();
    }

    public void AddToHome(int posizione) {
        listaIndexHome.add(posizione);
    }

    public void RemoveFromHome(int posizione) {
        listaIndexHome.remove(Integer.valueOf(posizione));
    }
}
