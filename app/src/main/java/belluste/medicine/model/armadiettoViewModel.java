package belluste.medicine.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class armadiettoViewModel extends ViewModel {

    public MutableLiveData<Armadietto> getContenutoArmadietto(Armadietto armadietto) {
        MutableLiveData<Armadietto> contenutoArmadietto;
        if (armadietto == null) {
            Armadietto newArmad = new Armadietto();
            contenutoArmadietto = new MutableLiveData<>(newArmad);
        } else {
            contenutoArmadietto = new MutableLiveData<>(armadietto);
        }
        return contenutoArmadietto;
    }

}
