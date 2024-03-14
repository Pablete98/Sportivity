package pablo.armario.sportivity.Database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividad_table")
public class Actividad {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;
    private String nombreActividad;
    private String zonaEntreno;
    private int tiempo;
    private int repeticiones;
    private int tipo;

    public Actividad(String nombreActividad, String zonaEntreno, int tiempo, int repeticiones, int tipo) {
        this.nombreActividad = nombreActividad;
        this.zonaEntreno = zonaEntreno;
        this.tiempo = tiempo;
        this.repeticiones = repeticiones;
        this.tipo = tipo;
    }

    @Ignore
    public Actividad(String nombreActividad, String zonaEntreno, int tiempo, int repeticiones) {
        this.nombreActividad = nombreActividad;
        this.zonaEntreno = zonaEntreno;
        this.tiempo = tiempo;
        this.repeticiones = repeticiones;
        this.tipo = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getZonaEntreno() {
        return zonaEntreno;
    }

    public void setZonaEntreno(String zonaEntreno) {
        this.zonaEntreno = zonaEntreno;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id=" + id +
                ", nombreActividad='" + nombreActividad + '\'' +
                ", zonaEntreno='" + zonaEntreno + '\'' +
                ", tiempo=" + tiempo +
                ", repeticiones=" + repeticiones +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
