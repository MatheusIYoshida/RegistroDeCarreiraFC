package com.example.registrodecarreirafc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListaTemporadasActivity extends AppCompatActivity {

    private static final int REQUEST_NOVA_TEMPORADA = 1;
    private static final int REQUEST_EDITAR_TEMPORADA = 2;
    private static final String PREFERENCIAS_APP = "configuracoes_app";
    private static final String CHAVE_ORDENACAO = "ordenacao";
    private static final String ORDENAR_POR_TEMPORADA = "temporada";
    private static final String ORDENAR_POR_CLUBE = "clube";
    private static final String ORDENAR_POR_GOLS = "gols";
    private final ArrayList<Temporada> temporadas = new ArrayList<>();
    private TemporadaAdapter adapter;
    private int posicaoSelecionada = -1;
    private String ordenacaoSelecionada;

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

        ListView listViewTemporadas = findViewById(R.id.listViewTemporadas);
        adapter = new TemporadaAdapter(this, temporadas);
        listViewTemporadas.setAdapter(adapter);
        carregarOrdenacao();
        ordenarTemporadas();

        listViewTemporadas.setOnItemClickListener((parent, view, position, id) -> {
            Temporada temporadaSelecionada = temporadas.get(position);
            Toast.makeText(this, getString(
                    R.string.season_item_title,
                    temporadaSelecionada.getNomeTemporada(),
                    temporadaSelecionada.getClube()
            ), Toast.LENGTH_SHORT).show();
        });

        listViewTemporadas.setOnItemLongClickListener((parent, view, position, id) -> {
            posicaoSelecionada = position;
            startActionMode(menuContextualCallback);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_temporadas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAdicionar) {
            startActivityForResult(new Intent(this, MainActivity.class), REQUEST_NOVA_TEMPORADA);
            return true;
        }

        if (item.getItemId() == R.id.menuSobre) {
            startActivity(new Intent(this, SobreActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.menuOrdenar) {
            mostrarDialogoOrdenacao();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        if (requestCode == REQUEST_NOVA_TEMPORADA) {
            Temporada temporada = new Temporada(
                    data.getStringExtra(MainActivity.EXTRA_TEMPORADA),
                    data.getStringExtra(MainActivity.EXTRA_CLUBE),
                    data.getIntExtra(MainActivity.EXTRA_PARTIDAS, 0),
                    data.getIntExtra(MainActivity.EXTRA_GOLS, 0),
                    data.getIntExtra(MainActivity.EXTRA_ASSISTENCIAS, 0),
                    data.getStringExtra(MainActivity.EXTRA_STATUS),
                    data.getBooleanExtra(MainActivity.EXTRA_TITULO, false),
                    data.getStringExtra(MainActivity.EXTRA_POSICAO)
            );

            temporadas.add(temporada);
            ordenarTemporadas();
            adapter.notifyDataSetChanged();
        }

        if (requestCode == REQUEST_EDITAR_TEMPORADA) {
            int posicaoEdicao = data.getIntExtra(MainActivity.EXTRA_POSICAO_EDICAO, -1);

            if (posicaoEdicao >= 0 && posicaoEdicao < temporadas.size()) {
                Temporada temporada = temporadas.get(posicaoEdicao);
                temporada.setNomeTemporada(data.getStringExtra(MainActivity.EXTRA_TEMPORADA));
                temporada.setClube(data.getStringExtra(MainActivity.EXTRA_CLUBE));
                temporada.setPartidas(data.getIntExtra(MainActivity.EXTRA_PARTIDAS, 0));
                temporada.setGols(data.getIntExtra(MainActivity.EXTRA_GOLS, 0));
                temporada.setAssistencias(data.getIntExtra(MainActivity.EXTRA_ASSISTENCIAS, 0));
                temporada.setStatus(data.getStringExtra(MainActivity.EXTRA_STATUS));
                temporada.setTituloConquistado(data.getBooleanExtra(MainActivity.EXTRA_TITULO, false));
                temporada.setPosicao(data.getStringExtra(MainActivity.EXTRA_POSICAO));
                ordenarTemporadas();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void abrirCadastroEdicao() {
        Temporada temporada = temporadas.get(posicaoSelecionada);
        Intent intentEdicao = new Intent(this, MainActivity.class);
        intentEdicao.putExtra(MainActivity.EXTRA_POSICAO_EDICAO, posicaoSelecionada);
        intentEdicao.putExtra(MainActivity.EXTRA_TEMPORADA, temporada.getNomeTemporada());
        intentEdicao.putExtra(MainActivity.EXTRA_CLUBE, temporada.getClube());
        intentEdicao.putExtra(MainActivity.EXTRA_PARTIDAS, temporada.getPartidas());
        intentEdicao.putExtra(MainActivity.EXTRA_GOLS, temporada.getGols());
        intentEdicao.putExtra(MainActivity.EXTRA_ASSISTENCIAS, temporada.getAssistencias());
        intentEdicao.putExtra(MainActivity.EXTRA_STATUS, temporada.getStatus());
        intentEdicao.putExtra(MainActivity.EXTRA_TITULO, temporada.isTituloConquistado());
        intentEdicao.putExtra(MainActivity.EXTRA_POSICAO, temporada.getPosicao());
        startActivityForResult(intentEdicao, REQUEST_EDITAR_TEMPORADA);
    }

    private final ActionMode.Callback menuContextualCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_contextual_temporada, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menuEditar) {
                abrirCadastroEdicao();
                mode.finish();
                return true;
            }

            if (item.getItemId() == R.id.menuExcluir) {
                temporadas.remove(posicaoSelecionada);
                adapter.notifyDataSetChanged();
                mode.finish();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            posicaoSelecionada = -1;
        }
    };

    private void carregarOrdenacao() {
        SharedPreferences preferencias = getSharedPreferences(PREFERENCIAS_APP, MODE_PRIVATE);
        ordenacaoSelecionada = preferencias.getString(CHAVE_ORDENACAO, ORDENAR_POR_TEMPORADA);
    }

    private void mostrarDialogoOrdenacao() {
        String[] opcoes = getResources().getStringArray(R.array.sort_options);
        int opcaoMarcada = obterIndiceOrdenacao();

        new AlertDialog.Builder(this)
                .setTitle(R.string.sort_dialog_title)
                .setSingleChoiceItems(opcoes, opcaoMarcada, (dialog, which) -> {
                    if (which == 0) {
                        ordenacaoSelecionada = ORDENAR_POR_TEMPORADA;
                    } else if (which == 1) {
                        ordenacaoSelecionada = ORDENAR_POR_CLUBE;
                    } else {
                        ordenacaoSelecionada = ORDENAR_POR_GOLS;
                    }

                    getSharedPreferences(PREFERENCIAS_APP, MODE_PRIVATE)
                            .edit()
                            .putString(CHAVE_ORDENACAO, ordenacaoSelecionada)
                            .apply();
                    ordenarTemporadas();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                })
                .show();
    }

    private int obterIndiceOrdenacao() {
        if (ORDENAR_POR_CLUBE.equals(ordenacaoSelecionada)) {
            return 1;
        }

        if (ORDENAR_POR_GOLS.equals(ordenacaoSelecionada)) {
            return 2;
        }

        return 0;
    }

    private void ordenarTemporadas() {
        if (ORDENAR_POR_CLUBE.equals(ordenacaoSelecionada)) {
            Collections.sort(temporadas, Comparator.comparing(
                    Temporada::getClube,
                    String.CASE_INSENSITIVE_ORDER
            ));
        } else if (ORDENAR_POR_GOLS.equals(ordenacaoSelecionada)) {
            Collections.sort(temporadas, (temporada1, temporada2) ->
                    Integer.compare(temporada2.getGols(), temporada1.getGols()));
        } else {
            Collections.sort(temporadas, Comparator.comparing(Temporada::getNomeTemporada));
        }
    }
}
