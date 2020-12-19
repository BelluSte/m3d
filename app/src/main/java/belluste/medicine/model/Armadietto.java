package belluste.medicine.model;

import java.util.Set;
import java.util.TreeMap;

public class Armadietto {

    private final TreeMap<String,Medicina> contenuto;

    public Armadietto() {
        this.contenuto = new TreeMap<>();
    }

    //TODO: archivio return set con archiviato=true

    public Set<String> listaNomi() {
        return contenuto.keySet();
    }

    public boolean addMedicina(Medicina medicina) {
        if (contenuto.containsValue(medicina)) {
            return false;
        } else {
            String nome = medicina.getNome();
            Set<String> nomi = listaNomi();
            if (nomi.contains(nome)) {
                Medicina med = contenuto.remove(nome);
                if (med != null) {
                    String newName = nome + " " + med.getTipo();
                    contenuto.put(newName, med);
                }
                nome = nome + " " + medicina.getTipo();
                contenuto.put(nome, medicina);
                return true;
            } else {
                contenuto.put(nome, medicina);
                return true;
            }
        }
    }

    public void removeMedicina(String nome) {
        contenuto.remove(nome);
    }

    public void aggiungiTipoAlNome() {
        //TODO: valutare se aggiungere funzione di aggiunta del tipo di medicinale all'elenco dei nomi
    }
}
