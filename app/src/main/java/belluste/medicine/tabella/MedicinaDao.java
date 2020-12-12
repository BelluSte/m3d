package belluste.medicine.tabella;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicinaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Medicina medicina);

    @Query("DELETE FROM tab_medicine WHERE nome=:mNome")
    void deleteMedicina(String mNome);

    @Query("SELECT nome FROM tab_medicine ORDER BY nome ASC")
    LiveData<List<Medicina>> listaMedicine();

}
