package pablo.armario.sportivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pablo.armario.sportivity.Database.Actividad;
import pablo.armario.sportivity.Database.ActividadViewModel;

public class NewActivity extends AppCompatActivity {

    private static final int TEXT_REQUEST = 1;
    private ActividadViewModel mActividadModel;
    private Spinner spinnerZona;
    private Spinner spinnerTiempo;
    private String repeticion_tiempo=null;
    private String zonaEntreno=null;
    private EditText nombreActivity;
    private EditText repActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        nombreActivity = findViewById(R.id.editTextTextNombre);
        repActivity = findViewById(R.id.editTextTextTiempo);
        spinnerZona = findViewById(R.id.spinnerZona);
        spinnerTiempo = findViewById(R.id.spinnerRep_Tiempo);
        mActividadModel = ViewModelProviders.of(this).get(ActividadViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.zonas_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerZona.setAdapter(adapter);
        spinnerZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                String selected = arg0.getItemAtPosition(arg2).toString();
                zonaEntreno=selected;
                //displayToast(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.tiempo_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTiempo.setAdapter(adapter1);
        spinnerTiempo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                String selected = arg0.getItemAtPosition(arg2).toString();
                repeticion_tiempo=selected;
                //displayToast(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void AñadirActividad(View view) {
        boolean añado=true;
        String nombre="";
        int rep_min=0;
        int min=0;
        int rep=0;
      if(!nombreActivity.getText().toString().trim().equals("")){
          nombre = nombreActivity.getText().toString();
      }else añado=false;

      if(!repActivity.getText().toString().trim().equals("")){
          rep_min = Integer.parseInt(repActivity.getText().toString());
      }else añado=false;

      if(zonaEntreno==null){
          añado=false;
      }

      if(repeticion_tiempo==null){
          añado=false;
      }else{
          if(repeticion_tiempo.equals("Min")){
              min=rep_min;
          }else
              rep=rep_min;
      }

      if(añado){
          System.out.println(nombre);
          System.out.println(zonaEntreno);
          System.out.println(min);
          System.out.println(rep);
          Actividad actividad = new Actividad(nombre, zonaEntreno, min, rep);
          mActividadModel.insert(actividad);
          displayToast("Añadido correctamente");
          Intent intent = new Intent(this, ActivityListNotPre.class);
          startActivityForResult(intent, TEXT_REQUEST);
      }else
          displayToast("No ha sido posible añadir, revisa los valores");
    }
}
