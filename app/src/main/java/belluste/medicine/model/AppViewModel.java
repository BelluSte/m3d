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

    public int AddMedicina(Medicina medicina) {
        int result = armadietto.addMedicina(medicina);
        if (result == -1) {
            int pos = armadietto.getContenuto().indexOf(medicina);
            for (int i=0; i<listaIndexHome.size(); i++) {
                int value = listaIndexHome.get(i);
                if (value >= pos) {
                    listaIndexHome.set(i, value+1);
                }
            }
        }
        return result;
    }

    public void RemoveMedicina(Medicina medicina) {
        armadietto.removeMedicina(medicina);
        AggiornaArmadietto();
    }

    public void UnisciMedicine(Medicina medicina, int posizione) {
        Medicina origine = getMedicina(posizione);
        origine.setConfezioni(origine.getConfezioni() + medicina.getConfezioni());
        if (origine.getQuantita() != -1) {
            origine.setTotale(origine.getTotale() + medicina.getTotale());
        }
        if (origine.getNote().length() > 0 && medicina.getNote().length() > 0) {
            origine.setNote(origine.getNote() + "\n\n" + medicina.getNote());
        } else if (origine.getNote().length() == 0 && medicina.getNote().length() > 0) {
            origine.setNote(medicina.getNote());
        }
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
