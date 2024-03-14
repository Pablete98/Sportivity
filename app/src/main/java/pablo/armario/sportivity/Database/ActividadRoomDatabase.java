package pablo.armario.sportivity.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//2
@Database(entities = {Actividad.class}, version = 1, exportSchema = false)
public abstract class ActividadRoomDatabase extends RoomDatabase {

    public abstract ActividadDAO actividadDAO();

    private static ActividadRoomDatabase INSTANCE;

    static ActividadRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActividadRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActividadRoomDatabase.class, "activity_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ActividadDAO mDao;
        Actividad actividad = new Actividad("Buerpees", "Piernas", 2, 0, 1);
        Actividad actividad1 = new Actividad("Correr", "Piernas", 30, 0, 1);
        Actividad actividad2 = new Actividad("Sentadillas", "Piernas", 0, 10, 1);
        Actividad actividad3 = new Actividad("Abdominales", "Abdomen", 0, 15, 1);
        Actividad actividad4 = new Actividad("Mancuerna", "Brazos", 0, 20, 1);
        Actividad actividad5 = new Actividad("Plancha", "Abdomen", 1, 0, 1);
        Actividad actividad6 = new Actividad("Sit up", "Abdomen", 0, 25, 1);
        Actividad actividad7 = new Actividad("Sprint", "Piernas", 0, 5, 1);
        Actividad actividad8 = new Actividad("Zancada", "Piernas", 0, 25, 1);
        Actividad actividad9 = new Actividad("Crunch Invertido", "Abdomen", 2, 0, 1);
        Actividad actividad10 = new Actividad("Curl de biceps con Barra", "Brazos", 0, 20, 1);
        Actividad actividad11 = new Actividad("Extensión concentrada", "Brazos", 0, 20, 1);
        Actividad actividad12 = new Actividad("Dip de triceps", "Brazos", 0, 20, 1);
        Actividad actividad13 = new Actividad("Remo", "Brazos", 0, 20, 1);





        Actividad [] actividades = {actividad, actividad1, actividad12, actividad2, actividad9, actividad3, actividad13, actividad4, actividad10, actividad5, actividad6, actividad11, actividad7, actividad8, actividad9};

        PopulateDbAsync(ActividadRoomDatabase db) {
            mDao = db.actividadDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyActivity().length < 1) {
                for (int i = 0; i <= actividades.length - 1; i++) {
                    mDao.insert(actividades[i]);
                }
            }
            return null;
        }
    }
}
