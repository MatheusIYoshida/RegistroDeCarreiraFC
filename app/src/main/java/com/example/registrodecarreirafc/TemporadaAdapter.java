package com.example.registrodecarreirafc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TemporadaAdapter extends ArrayAdapter<Temporada> {

    public TemporadaAdapter(Context context, List<Temporada> temporadas) {
        super(context, 0, temporadas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_temporada,
                    parent,
                    false
            );
        }

        Temporada temporada = getItem(position);

        TextView textViewTitulo = itemView.findViewById(R.id.textViewTituloTemporada);
        TextView textViewEstatisticas = itemView.findViewById(R.id.textViewEstatisticasTemporada);

        if (temporada != null) {
            textViewTitulo.setText(temporada.getNomeTemporada() + " - " + temporada.getClube());
            textViewEstatisticas.setText(
                    "Partidas: " + temporada.getPartidas()
                            + " | Gols: " + temporada.getGols()
                            + " | Assistências: " + temporada.getAssistencias()
            );
        }

        return itemView;
    }
}
