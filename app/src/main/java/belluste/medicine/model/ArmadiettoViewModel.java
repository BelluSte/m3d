package belluste.medicine.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ArmadiettoViewModel extends AndroidViewModel {

    public final MutableLiveData<Armadietto> contenuto;

    public ArmadiettoViewModel(@NonNull Application application) {
        super(application);
        contenuto = new MutableLiveData<>();
    }

    public void SetContenuto(Armadietto armadietto) {
        contenuto.setValue(armadietto);
    }

    public boolean AddMedicina(Medicina medicina) {
        return contenuto.getValue().addMedicina(medicina);
    }
}
