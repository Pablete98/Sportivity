package pablo.armario.sportivity.Database;
//4
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ActividadViewModel extends AndroidViewModel {

    private ActividadRepository mRepository;
    private LiveData<List<Actividad>> mAllActivities;


    public ActividadViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ActividadRepository(application);
        mAllActivities = mRepository.getmAllActivities();
    }

    public LiveData<List<Actividad>> getAllActivities() { return mAllActivities; }

    public LiveData<List<Actividad>> getAllpre() {
        return mRepository.getAllpre();
    }

    public LiveData<List<Actividad>> getAllNotpre() {
        return mRepository.getAllNotpre();
    }

    public void insert(Actividad actividad) { mRepository.insert(actividad); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteAllNotPreActivities() {mRepository.deleteAllNotPreActivities();}

    public void deleteWord(Actividad actividad) {mRepository.deleteActivity(actividad);}

}
