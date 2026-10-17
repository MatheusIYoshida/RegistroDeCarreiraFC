package com.example.registrodecarreirafc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEMPORADA = "temporada";
    public static final String EXTRA_CLUBE = "clube";
    public static final String EXTRA_PARTIDAS = "partidas";
    public static final String EXTRA_GOLS = "gols";
    public static final String EXTRA_ASSISTENCIAS = "assistencias";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_TITULO = "titulo";
    public static final String EXTRA_POSICAO = "posicao";
    public static final String EXTRA_POSICAO_EDICAO = "posicao_edicao";

    private EditText editTextTemporada;
    private EditText editTextClube;
    private EditText editTextPartidas;
    private EditText editTextGols;
    private EditText editTextAssistencias;
    private RadioGroup radioGroupStatus;
    private CheckBox checkBoxTitulo;
    private Spinner spinnerPosicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTemporada = findViewById(R.id.editTextTemporada);
        editTextClube = findViewById(R.id.editTextClube);
        editTextPartidas = findViewById(R.id.editTextPartidas);
        editTextGols = findViewById(R.id.editTextGols);
        editTextAssistencias = findViewById(R.id.editTextAssistencias);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        checkBoxTitulo = findViewById(R.id.checkBoxTitulo);
        spinnerPosicao = findViewById(R.id.spinnerPosicao);

        String[] posicoes = getResources().getStringArray(R.array.player_positions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                posicoes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosicao.setAdapter(adapter);

        preencherCamposEdicao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_temporada, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSalvar) {
            salvarTemporada();
            return true;
        }

        if (item.getItemId() == R.id.menuLimpar) {
            limparCampos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    private void preencherCamposEdicao() {
        Intent intent = getIntent();

        if (!intent.hasExtra(EXTRA_POSICAO_EDICAO)) {
            return;
        }

        editTextTemporada.setText(intent.getStringExtra(EXTRA_TEMPORADA));
        editTextClube.setText(intent.getStringExtra(EXTRA_CLUBE));
        editTextPartidas.setText(String.valueOf(intent.getIntExtra(EXTRA_PARTIDAS, 0)));
        editTextGols.setText(String.valueOf(intent.getIntExtra(EXTRA_GOLS, 0)));
        editTextAssistencias.setText(String.valueOf(intent.getIntExtra(EXTRA_ASSISTENCIAS, 0)));

        String status = intent.getStringExtra(EXTRA_STATUS);
        if (getString(R.string.status_in_progress).equals(status)) {
            radioGroupStatus.check(R.id.radioButtonAndamento);
        } else if (getString(R.string.status_finished).equals(status)) {
            radioGroupStatus.check(R.id.radioButtonFinalizada);
        }

        checkBoxTitulo.setChecked(intent.getBooleanExtra(EXTRA_TITULO, false));

        String posicao = intent.getStringExtra(EXTRA_POSICAO);
        for (int i = 0; i < spinnerPosicao.getCount(); i++) {
            if (spinnerPosicao.getItemAtPosition(i).equals(posicao)) {
                spinnerPosicao.setSelection(i);
                break;
            }
        }
    }

    private void limparCampos() {
        editTextTemporada.setText("");
        editTextClube.setText("");
        editTextPartidas.setText("");
        editTextGols.setText("");
        editTextAssistencias.setText("");

        radioGroupStatus.clearCheck();
        checkBoxTitulo.setChecked(false);
        spinnerPosicao.setSelection(0);

        editTextTemporada.requestFocus();

        Toast.makeText(this, R.string.toast_fields_cleared, Toast.LENGTH_SHORT).show();
    }

    private void salvarTemporada() {
        String temporada = editTextTemporada.getText().toString().trim();
        String clube = editTextClube.getText().toString().trim();
        String partidas = editTextPartidas.getText().toString().trim();
        String gols = editTextGols.getText().toString().trim();
        String assistencias = editTextAssistencias.getText().toString().trim();

        int radioSelecionadoId = radioGroupStatus.getCheckedRadioButtonId();

        if (temporada.isEmpty()) {
            Toast.makeText(this, R.string.error_season_required, Toast.LENGTH_SHORT).show();
            editTextTemporada.requestFocus();
            return;
        }

        if (clube.isEmpty()) {
            Toast.makeText(this, R.string.error_club_required, Toast.LENGTH_SHORT).show();
            editTextClube.requestFocus();
            return;
        }

        if (partidas.isEmpty()) {
            Toast.makeText(this, R.string.error_matches_required, Toast.LENGTH_SHORT).show();
            editTextPartidas.requestFocus();
            return;
        }

        if (gols.isEmpty()) {
            Toast.makeText(this, R.string.error_goals_required, Toast.LENGTH_SHORT).show();
            editTextGols.requestFocus();
            return;
        }

        if (assistencias.isEmpty()) {
            Toast.makeText(this, R.string.error_assists_required, Toast.LENGTH_SHORT).show();
            editTextAssistencias.requestFocus();
            return;
        }

        if (radioSelecionadoId == -1) {
            Toast.makeText(this, R.string.error_status_required, Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioButtonStatus = findViewById(radioSelecionadoId);
        String status = radioButtonStatus.getText().toString();
        boolean conquistouTitulo = checkBoxTitulo.isChecked();
        String posicao = spinnerPosicao.getSelectedItem().toString();

        Intent dadosRetorno = new Intent();
        dadosRetorno.putExtra(EXTRA_TEMPORADA, temporada);
        dadosRetorno.putExtra(EXTRA_CLUBE, clube);
        dadosRetorno.putExtra(EXTRA_PARTIDAS, Integer.parseInt(partidas));
        dadosRetorno.putExtra(EXTRA_GOLS, Integer.parseInt(gols));
        dadosRetorno.putExtra(EXTRA_ASSISTENCIAS, Integer.parseInt(assistencias));
        dadosRetorno.putExtra(EXTRA_STATUS, status);
        dadosRetorno.putExtra(EXTRA_TITULO, conquistouTitulo);
        dadosRetorno.putExtra(EXTRA_POSICAO, posicao);

        if (getIntent().hasExtra(EXTRA_POSICAO_EDICAO)) {
            dadosRetorno.putExtra(
                    EXTRA_POSICAO_EDICAO,
                    getIntent().getIntExtra(EXTRA_POSICAO_EDICAO, -1)
            );
        }

        setResult(RESULT_OK, dadosRetorno);
        finish();
    }
}
