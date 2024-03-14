package pablo.armario.sportivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pablo.armario.sportivity.Database.Actividad;


public class ActividadListAdapter extends RecyclerView.Adapter<ActividadListAdapter.ActividadViewHolder> {

    private final LayoutInflater mInflater;
    private List<Actividad> mActividades;
    private static ClickListener clickListener;

    public ActividadListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_pre_cardview, parent, false);
        return new ActividadViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ActividadListAdapter.ActividadViewHolder holder, int position) {


        if (mActividades != null) {
            Actividad current = mActividades.get(position);
            holder.nombreActividad.setText(current.getNombreActividad());
            holder.zonaEntreno.setText(current.getZonaEntreno());
            if(current.getRepeticiones()==0){
                holder.tiempo_repeticiones.setText(current.getTiempo()+" Minutos");
            }else
                holder.tiempo_repeticiones.setText(current.getRepeticiones()+" repeticiones");

            if (current.getZonaEntreno().equals("Brazos")){
                holder.imageView.setImageResource(R.drawable.brazo);
            }
            if(current.getZonaEntreno().equals("Abdomen")){
                holder.imageView.setImageResource(R.drawable.abdomen);
            }
            if(current.getZonaEntreno().equals("Piernas")){
                holder.imageView.setImageResource(R.drawable.pierna);
            }
        } else {
            holder.nombreActividad.setText(R.string.sin_nombre);
            holder.zonaEntreno.setText(R.string.sin_zona);
            holder.tiempo_repeticiones.setText("0");
        }
    }
    void setmActividades(List<Actividad> actividad) {
        mActividades = actividad;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       if (mActividades != null)
           return mActividades.size();
       else return 0;
    }

    public Actividad getActividadAtPosition(int position) {
        return mActividades.get(position);
    }

    class ActividadViewHolder extends RecyclerView.ViewHolder {


        private final TextView nombreActividad;
        private final TextView zonaEntreno;
        private final TextView tiempo_repeticiones;
        private final ImageView imageView;

        private ActividadViewHolder(View mItemView) {
            super(mItemView);
            this.nombreActividad = mItemView.findViewById(R.id.nombreActividad);
            this.zonaEntreno = (TextView) mItemView.findViewById(R.id.zonaEntreno);
            this.tiempo_repeticiones = (TextView) mItemView.findViewById(R.id.tiempo_repeticion);
            this.imageView = mItemView.findViewById(R.id.imagenZonaBackground);

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        ActividadListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
