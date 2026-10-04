package com.example.registrodecarreirafc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListaTemporadasActivity extends AppCompatActivity {

    private static final int REQUEST_CADASTRO_TEMPORADA = 1;
    private final ArrayList<Temporada> temporadas = new ArrayList<>();
    private TemporadaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_temporadas);

        View layoutListaTemporadas = findViewById(R.id.layoutListaTemporadas);
        int paddingEsquerdo = layoutListaTemporadas.getPaddingLeft();
        int paddingSuperior = layoutListaTemporadas.getPaddingTop();
        int paddingDireito = layoutListaTemporadas.getPaddingRight();
        int paddingInferior = layoutListaTemporadas.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(layoutListaTemporadas, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    paddingEsquerdo + systemBars.left,
                    paddingSuperior + systemBars.top,
                    paddingDireito + systemBars.right,
                    paddingInferior + systemBars.bottom
            );
            return insets;
        });

        ListView listViewTemporadas = findViewById(R.id.listViewTemporadas);
        Button buttonAdicionar = findViewById(R.id.buttonAdicionar);
        Button buttonSobre = findViewById(R.id.buttonSobre);

        adapter = new TemporadaAdapter(this, temporadas);
        listViewTemporadas.setAdapter(adapter);

        buttonAdicionar.setOnClickListener(view -> {
            Intent intentCadastro = new Intent(this, MainActivity.class);
            startActivityForResult(intentCadastro, REQUEST_CADASTRO_TEMPORADA);
        });

        buttonSobre.setOnClickListener(view -> {
            Intent intentSobre = new Intent(this, SobreActivity.class);
            startActivity(intentSobre);
        });

        listViewTemporadas.setOnItemClickListener((parent, view, position, id) -> {
            Temporada temporadaSelecionada = temporadas.get(position);
            String mensagem = temporadaSelecionada.getNomeTemporada()
                    + " - " + temporadaSelecionada.getClube();
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CADASTRO_TEMPORADA && resultCode == RESULT_OK && data != null) {
            Temporada temporada = new Temporada(
                    data.getStringExtra(MainActivity.EXTRA_TEMPORADA),
                    data.getStringExtra(MainActivity.EXTRA_CLUBE),
                    data.getIntExtra(MainActivity.EXTRA_PARTIDAS, 0),
                    data.getIntExtra(MainActivity.EXTRA_GOLS, 0),
                    data.getIntExtra(MainActivity.EXTRA_ASSISTENCIAS, 0)
            );

            temporadas.add(temporada);
            adapter.notifyDataSetChanged();
        }
    }
}
