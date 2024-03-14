package pablo.armario.sportivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Activity_Detail extends AppCompatActivity {

    String nombreActividad;
    String ZonaEntreno;
    int Tiempo;
    int Repeticiones;

    TextView NombreActividadInDetail;
    TextView tiempo_repeticion_a_ejercitar;
    TextView min_rep;
    ImageView imageViewDetail;

    private TextView text_view_timer;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Textos
        NombreActividadInDetail = findViewById(R.id.NombreActividadInDetail);
        tiempo_repeticion_a_ejercitar = findViewById(R.id.tiempo_repeticion_a_ejercitar);
        imageViewDetail = findViewById(R.id.imageViewDetail);
        min_rep = findViewById(R.id.min_rep);

        //Temporizador
        text_view_timer = findViewById(R.id.text_view_timer);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });



        nombreActividad = getIntent().getStringExtra("Nombre");
        NombreActividadInDetail.setText(nombreActividad);

        ZonaEntreno = getIntent().getStringExtra("ZonaEntreno");

        if (ZonaEntreno.equals("Brazos")){
            imageViewDetail.setImageResource(R.drawable.brazo);
        }
        if(ZonaEntreno.equals("Abdomen")){
            imageViewDetail.setImageResource(R.drawable.abdomen);
        }
        if(ZonaEntreno.equals("Piernas")){
            imageViewDetail.setImageResource(R.drawable.pierna);
        }
        Tiempo = getIntent().getIntExtra("Tiempo", 0);
        mTimeLeftInMillis=Tiempo* 60000L;
        Repeticiones = getIntent().getIntExtra("Repeticiones", 0);

        if(Repeticiones==0){
            tiempo_repeticion_a_ejercitar.setText(String.valueOf(Tiempo));
            min_rep.setText(R.string.minutos);
            text_view_timer.setVisibility(View.VISIBLE);
            mButtonStartPause.setVisibility(View.VISIBLE);

        }else{
            tiempo_repeticion_a_ejercitar.setText(String.valueOf(Repeticiones));
            min_rep.setText(R.string.repeticiones);
        }

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText(R.string.Start);
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText(R.string.Pause);
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText(R.string.Start);
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = Tiempo* 60000L;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        text_view_timer.setText(timeLeftFormatted);
    }

}