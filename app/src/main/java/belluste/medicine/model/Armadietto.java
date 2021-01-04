package belluste.medicine.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;

public class Armadietto {

    private final LinkedList<Medicina> contenuto;

    public Armadietto() {
        this.contenuto = new LinkedList<>();
    }

    public Armadietto(LinkedList<Medicina> lista) {
        this.contenuto = new LinkedList<>(lista);
    }

    public void setContenuto(Armadietto armadietto) {
        contenuto.clear();
        contenuto.addAll(armadietto.getContenuto());
    }

    public LinkedList<Medicina> getContenuto() {
        Collections.sort(contenuto, Medicina.medNameComparator);
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
            String nome = medicina.getNome();
            for (Medicina m : contenuto) {
                if (m.getNome().equals(nome)) {
                    String nuovoNome = m.getNome() + " - " + m.getTipo();
                    Medicina nuova1 = new Medicina(m, nuovoNome);
                    contenuto.add(nuova1);
                    contenuto.remove(m);
                    nuovoNome = medicina.getNome() + " - " + medicina.getTipo();
                    Medicina nuova2 = new Medicina(medicina, nuovoNome);
                    contenuto.add(nuova2);
                    return true;
                }
            }
            contenuto.add(medicina);
            return true;
        }
    }

    public void removeMedicina(Medicina medicina) {
        contenuto.remove(medicina);
    }

    @Override
    public String toString() {
        return "Armadietto{" +
                "contenuto=" + contenuto.toString() +
                "}";
    }
}
