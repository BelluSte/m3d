package belluste.medicine.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class Armadietto {

    private final LinkedList<Medicina> contenuto;

    public Armadietto() {
        this.contenuto = new LinkedList<>();
    }

    public Armadietto(LinkedList<Medicina> lista) {
        this.contenuto = new LinkedList<>(lista);
    }

    public LinkedList<Medicina> getContenuto() {
        return contenuto;
    }

    public boolean addMedicina(Medicina medicina) {
        if (contenuto.contains(medicina)) {
            return false;
        } else {
            contenuto.add(medicina);
            if (contenuto.size() > 1) {
                Collections.sort(contenuto, Medicina.medNameComparator);
            }
            return true;
        }
    }

    public void removeMedicina(Medicina medicina) {
        contenuto.remove(medicina);
    }

    public void removeAll() {
        contenuto.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armadietto that = (Armadietto) o;
        return (Objects.equals(contenuto, that.contenuto) && (contenuto.size() == that.contenuto.size()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenuto);
    }
}
