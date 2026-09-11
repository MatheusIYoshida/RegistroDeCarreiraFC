package com.example.registrodecarreirafc;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private EditText editTextTemporada;
    private EditText editTextClube;
    private EditText editTextPartidas;
    private EditText editTextGols;
    private EditText editTextAssistencias;
    private RadioGroup radioGroupStatus;
    private CheckBox checkBoxTitulo;
    private Spinner spinnerPosicao;
    private Button buttonLimpar;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
        buttonLimpar = findViewById(R.id.buttonLimpar);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        String[] posicoes = {"Goleiro", "Defensor", "Meio-campista", "Atacante"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                posicoes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosicao.setAdapter(adapter);

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarTemporada();
            }
        });
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

        Toast.makeText(this, "Campos limpos com sucesso.", Toast.LENGTH_SHORT).show();
    }

    private void salvarTemporada() {
        String temporada = editTextTemporada.getText().toString().trim();
        String clube = editTextClube.getText().toString().trim();
        String partidas = editTextPartidas.getText().toString().trim();
        String gols = editTextGols.getText().toString().trim();
        String assistencias = editTextAssistencias.getText().toString().trim();

        int radioSelecionadoId = radioGroupStatus.getCheckedRadioButtonId();

        if (temporada.isEmpty()) {
            Toast.makeText(this, "Informe a temporada.", Toast.LENGTH_SHORT).show();
            editTextTemporada.requestFocus();
            return;
        }

        if (clube.isEmpty()) {
            Toast.makeText(this, "Informe o clube.", Toast.LENGTH_SHORT).show();
            editTextClube.requestFocus();
            return;
        }

        if (partidas.isEmpty()) {
            Toast.makeText(this, "Informe o número de partidas.", Toast.LENGTH_SHORT).show();
            editTextPartidas.requestFocus();
            return;
        }

        if (gols.isEmpty()) {
            Toast.makeText(this, "Informe o número de gols.", Toast.LENGTH_SHORT).show();
            editTextGols.requestFocus();
            return;
        }

        if (assistencias.isEmpty()) {
            Toast.makeText(this, "Informe o número de assistências.", Toast.LENGTH_SHORT).show();
            editTextAssistencias.requestFocus();
            return;
        }

        if (radioSelecionadoId == -1) {
            Toast.makeText(this, "Selecione o status da temporada.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioButtonStatus = findViewById(radioSelecionadoId);
        String status = radioButtonStatus.getText().toString();
        String conquistouTitulo = checkBoxTitulo.isChecked() ? "Sim" : "Não";
        String posicao = spinnerPosicao.getSelectedItem().toString();

        String mensagem = "Temporada salva: " + temporada
                + " | Clube: " + clube
                + " | Posição: " + posicao
                + " | Status: " + status
                + " | Título: " + conquistouTitulo;

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}