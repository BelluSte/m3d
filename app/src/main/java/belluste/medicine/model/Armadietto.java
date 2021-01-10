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

    public LinkedList<Medicina> listaAttivi() {
        LinkedList<Medicina> lista = new LinkedList<>();
        for (Medicina m : contenuto) {
            if (!m.getArchiviato()) {
                lista.add(m);
            }
        }
        return lista;
    }

    public LinkedList<Medicina> listaArchiviati() {
        LinkedList<Medicina> lista = new LinkedList<>();
        for (Medicina m : contenuto) {
            if (m.getArchiviato()) {
                lista.add(m);
            }
        }
        return lista;
    }

    public boolean addMedicina(Medicina medicina) {
        if (contenuto.contains(medicina)) {
            return false;
        } else {
            contenuto.add(medicina);
            //Collections.sort(contenuto, Medicina.medNameComparator);
            return true;
        }
    }

    public void removeMedicina(Medicina medicina) {
        contenuto.remove(medicina);
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
