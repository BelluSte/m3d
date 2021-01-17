package belluste.medicine.model;

import java.util.Collections;
import java.util.LinkedList;

public class Archivio {

    private final LinkedList<MedArchiviata> contenuto;

    public Archivio() {
        this.contenuto = new LinkedList<>();
    }

    public Archivio(LinkedList<MedArchiviata> lista) {
        this.contenuto = new LinkedList<>(lista);
    }

    public LinkedList<MedArchiviata> getContenuto() {
        return contenuto;
    }

    public void addMed(MedArchiviata med) {
        if (contenuto.contains(med)) {
            for (MedArchiviata m : contenuto) {
                if (m.equals(med)) {
                    m.AddInfo(med.getNote(0), med.getDataAgg(0), med.getDataArc(0));
                }
            }
        } else {
            contenuto.add(med);
        }
        if (contenuto.size() >1) {
            Collections.sort(contenuto, MedArchiviata.medNameComparator);
        }
    }

    public void removeMed() {

    }
}
