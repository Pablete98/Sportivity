package pablo.armario.sportivity.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
//1
@Dao
public interface ActividadDAO {

    @Query("SELECT * from actividad_table ORDER BY nombreActividad ASC")
    LiveData<List<Actividad>> getAlphabetizedActivities();

    @Query("SELECT * from actividad_table WHERE tipo=1 ORDER BY nombreActividad ASC")
    LiveData<List<Actividad>> getAllpre();

    @Query("SELECT * from actividad_table WHERE tipo=0 ORDER BY nombreActividad ASC")
    LiveData<List<Actividad>> getAllNotpre();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Actividad actividad);

    @Query("DELETE FROM actividad_table")
    void deleteAll();

    @Query("DELETE FROM actividad_table WHERE tipo=0")
    void deleteAllNotPreActivities();


    @Query("SELECT * from actividad_table LIMIT 1")
    Actividad[] getAnyActivity();

    @Delete
    void deleteActivity(Actividad actividad);

}
