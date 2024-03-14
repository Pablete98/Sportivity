package pablo.armario.sportivity.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//3
public class ActividadRepository {
    private ActividadDAO mActividadDAO;
    private LiveData<List<Actividad>> mAllActivities;


    ActividadRepository(Application application){
        ActividadRoomDatabase db = ActividadRoomDatabase.getDatabase(application);
        mActividadDAO = db.actividadDAO();
        mAllActivities = mActividadDAO.getAlphabetizedActivities();
    }



    LiveData<List<Actividad>> getmAllActivities() {
        return mAllActivities;
    }

    LiveData<List<Actividad>> getAllpre() {
        return mActividadDAO.getAllpre();
    }

    LiveData<List<Actividad>> getAllNotpre() {
        return mActividadDAO.getAllNotpre();
    }



    public void insert (Actividad actividad){
        new insertAsyncTask(mActividadDAO).execute(actividad);
    }

    private static class insertAsyncTask extends AsyncTask<Actividad, Void, Void> {

        private ActividadDAO mAsyncTaskDao;

        insertAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Actividad... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllActivityAsyncTask(mActividadDAO).execute();
    }




    private static class deleteAllActivityAsyncTask extends AsyncTask<Void, Void, Void> {
        private ActividadDAO mAsyncTaskDao;

        deleteAllActivityAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    public void deleteAllNotPreActivities()  { new deleteAllNotPreActivityAsyncTask(mActividadDAO).execute(); }

    private static class deleteAllNotPreActivityAsyncTask extends AsyncTask<Void, Void, Void> {
        private ActividadDAO mAsyncTaskDao;

        deleteAllNotPreActivityAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllNotPreActivities();
            return null;
        }
    }

    public void deleteActivity(Actividad actividad) {
        new deleteActivityAsyncTask(mActividadDAO).execute(actividad);

    }

    private static class deleteActivityAsyncTask extends AsyncTask<Actividad, Void, Void> {
        private ActividadDAO mAsyncTaskDao;

        deleteActivityAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Actividad... params) {
            mAsyncTaskDao.deleteActivity(params[0]);
            return null;
        }
    }
}
