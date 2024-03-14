package pablo.armario.sportivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import pablo.armario.sportivity.Database.ActividadViewModel;


public class MainActivity extends AppCompatActivity {

    private static final int TEXT_REQUEST = 1;
    private AppBarConfiguration appBarConfiguration;
    private ActividadViewModel mActividadModel;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private int currentSongIndex = 0;  // Índice de la canción actual

    private int[] songs = {
            R.raw.cancion1,
            R.raw.cancion2,
            R.raw.cancion3,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActividadModel = ViewModelProviders.of(this).get(ActividadViewModel.class);

        final Button buttonActPre = findViewById(R.id.buttonActivitiesPre);
        buttonActPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityListPre.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        final Button buttonActNotPre = findViewById(R.id.buttonYourActivities);
        buttonActNotPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityListNotPre.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        final Button buttonWeather = findViewById(R.id.buttonWeather);
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        // Inicializa el reproductor de música con la primera canción
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);

        // Configura el botón de reproducción/pausa
        final ImageButton playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            }
        });

        // Configura el botón de siguiente
        final ImageButton nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNextSong();
            }
        });

        // Configura el botón de selección de canciones
        final Button selectSongButton = findViewById(R.id.selectSongButton);
        selectSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSongSelectionDialog();
            }
        });
    }

    private void playMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            // Actualiza la interfaz de usuario según sea necesario (por ejemplo, cambia el icono del botón)
        }
    }

    private void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            // Actualiza la interfaz de usuario según sea necesario (por ejemplo, cambia el icono del botón)
        }
    }

    private void playNextSong() {
        // Cambia a la siguiente canción (circular, vuelve a la primera si es la última)
        currentSongIndex = (currentSongIndex + 1) % songs.length;
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        playMusic();
    }

    private void playSpecificSong(int songIndex) {
        // Reproduce una canción específica
        if (songIndex >= 0 && songIndex < songs.length) {
            currentSongIndex = songIndex;
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
            playMusic();
        }
    }

    // Ejemplo de un diálogo de selección simple
    private void showSongSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una canción");
        builder.setItems(R.array.song_names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playSpecificSong(which);
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Libera recursos cuando la actividad se destruye
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Reset_Activities) {
            Toast.makeText(this, "Tu lista de actividades ha sido borrada", Toast.LENGTH_SHORT).show();
            mActividadModel.deleteAllNotPreActivities();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_controller_view_tag);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
