package belluste.medicine.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;


public class ArmadiettoViewModel extends ViewModel {

    private final MutableLiveData<LinkedList<Medicina>> contenutoAttivi = new MutableLiveData<>();
    private final MutableLiveData<LinkedList<Medicina>> contenutoArchiviati = new MutableLiveData<>();

    private Armadietto armadietto;

    public void SetContenuto(Armadietto armadietto) {
        contenutoAttivi.setValue(armadietto.listaAttivi());
        contenutoArchiviati.setValue(armadietto.listaArchiviati());
        this.armadietto = armadietto;
    }

    public LiveData<LinkedList<Medicina>> getAttivi() {
        return contenutoAttivi;
    }

    public LiveData<LinkedList<Medicina>> getArchiviati() {
        return contenutoArchiviati;
    }

    public LinkedList<Medicina> listaAttivi() {
        return contenutoAttivi.getValue();
    }

    public LinkedList<Medicina> listaArchiviati() {
        return contenutoArchiviati.getValue();
    }

    public Medicina getMedicina(int posizione) {
        return armadietto.getContenuto().get(posizione);
    }
}
