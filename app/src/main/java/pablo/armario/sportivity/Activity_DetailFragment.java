package pablo.armario.sportivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import pablo.armario.sportivity.Database.Actividad;
import pablo.armario.sportivity.Database.ActividadViewModel;

public class Activity_DetailFragment extends Fragment {

    public Actividad actividad;

    int idActividad;
    String nombreActividad;
    String ZonaEntreno;
    int Tiempo;
    int Repeticiones;

    private TextView NombreActividadInDetail;
    private TextView tiempo_repeticion_a_ejercitar;
    private TextView min_rep;
    private ImageView imageViewDetail;

    private TextView text_view_timer;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    ActividadViewModel actividadViewModel;

    public Activity_DetailFragment() {
    }

    public static Activity_DetailFragment newInstance(int id, String nombre, String zona, int tiempo, int repeticiones){
        Activity_DetailFragment fragment = new Activity_DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("nombre", nombre);
        bundle.putString("zona", zona);
        bundle.putInt("tiempo", tiempo);
        bundle.putInt("repeticiones", repeticiones);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actividadViewModel = new ActividadViewModel(getActivity().getApplication());

        if(getArguments().containsKey("id")){

            idActividad = getArguments().getInt("id");


            LiveData<List<Actividad>> ActvidadMutableData=actividadViewModel.getAllActivities();
            ActvidadMutableData.observe(this, new Observer<List<Actividad>>() {
                @Override
                public void onChanged(List<Actividad> actividads) {
                    Iterator it = actividads.iterator();
                    while (it.hasNext()){
                        Actividad actividad = (Actividad) it.next();
                        if (actividad.getId() == actividad.getId()){
                            nombreActividad = getArguments().getString("nombre");
                            ZonaEntreno = getArguments().getString("zona");
                            Tiempo = getArguments().getInt("tiempo");
                            Repeticiones = getArguments().getInt("repeticiones");
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

                            NombreActividadInDetail.setText(nombreActividad);


                            if (ZonaEntreno.equals("Brazos")){
                                imageViewDetail.setImageResource(R.drawable.brazo);
                            }
                            if(ZonaEntreno.equals("Abdomen")){
                                imageViewDetail.setImageResource(R.drawable.abdomen);
                            }
                            if(ZonaEntreno.equals("Piernas")){
                                imageViewDetail.setImageResource(R.drawable.pierna);
                            }

                            mTimeLeftInMillis=Tiempo* 60000L;

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
                    }
                }
            });
        }
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


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_detail_fragment, container, false);




        NombreActividadInDetail = rootView.findViewById(R.id.NombreActividadInDetail);
        tiempo_repeticion_a_ejercitar = rootView.findViewById(R.id.tiempo_repeticion_a_ejercitarInDetail);
        imageViewDetail = rootView.findViewById(R.id.imageViewDetailInDetail);
        min_rep = rootView.findViewById(R.id.min_repInDetail);

        //Temporizador
        text_view_timer = rootView.findViewById(R.id.text_view_timerInDetail);
        mButtonStartPause = rootView.findViewById(R.id.button_start_pauseInDetail);
        mButtonReset = rootView.findViewById(R.id.button_resetInDetail);
        return rootView;
    }
}
