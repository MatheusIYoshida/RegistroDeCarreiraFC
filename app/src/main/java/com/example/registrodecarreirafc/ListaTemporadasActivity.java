package com.example.registrodecarreirafc;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListaTemporadasActivity extends AppCompatActivity {

    private final ArrayList<Temporada> temporadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_temporadas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listViewTemporadas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarTemporadas();

        ListView listViewTemporadas = findViewById(R.id.listViewTemporadas);
        TemporadaAdapter adapter = new TemporadaAdapter(this, temporadas);
        listViewTemporadas.setAdapter(adapter);

        listViewTemporadas.setOnItemClickListener((parent, view, position, id) -> {
            Temporada temporadaSelecionada = temporadas.get(position);
            String mensagem = temporadaSelecionada.getNomeTemporada()
                    + " - " + temporadaSelecionada.getClube();
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
        });
    }

    private void carregarTemporadas() {
        String[] nomesTemporadas = getResources().getStringArray(R.array.nomes_temporadas);
        String[] clubes = getResources().getStringArray(R.array.clubes);
        String[] partidas = getResources().getStringArray(R.array.partidas);
        String[] gols = getResources().getStringArray(R.array.gols);
        String[] assistencias = getResources().getStringArray(R.array.assistencias);

        for (int i = 0; i < nomesTemporadas.length; i++) {
            temporadas.add(new Temporada(
                    nomesTemporadas[i],
                    clubes[i],
                    Integer.parseInt(partidas[i]),
                    Integer.parseInt(gols[i]),
                    Integer.parseInt(assistencias[i])
            ));
        }
    }
}
