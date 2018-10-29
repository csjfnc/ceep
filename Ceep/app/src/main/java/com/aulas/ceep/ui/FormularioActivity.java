package com.aulas.ceep.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aulas.ceep.R;
import com.aulas.ceep.dao.NotaDao;
import com.aulas.ceep.model.Nota;

import java.io.Serializable;

import static com.aulas.ceep.util.NotasConstantes.CHAVE_NOTA;
import static com.aulas.ceep.util.NotasConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class FormularioActivity extends AppCompatActivity {

    public static final String ALTERAR_NOTAS = "Alterar Notas";
    public static final String NOVA_NOTA = "Nova Nota";
    private EditText edt_titulo, edt_descricao;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(NOVA_NOTA);

        inicializaCampos();

        Intent intent = getIntent();
        if(intent.hasExtra(CHAVE_NOTA) && intent.hasExtra("position")){
            setTitle(ALTERAR_NOTAS);
            Nota notaRecebida = (Nota) intent.getSerializableExtra(CHAVE_NOTA);
            position = intent.getIntExtra("position", -1);

            preencheCampos(notaRecebida);
        }
    }

    private void preencheCampos(Nota notaRecebida) {
        edt_titulo.setText(notaRecebida.getTitulo());
        edt_descricao.setText(notaRecebida.getDescricao());
    }

    private void inicializaCampos() {
        edt_titulo = findViewById(R.id.edt_titulo);
        edt_descricao = findViewById(R.id.edt_descricao);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
            case R.id.salvar:{
                Nota notacriada = criaNota();
                retornarNota(notacriada);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornarNota(Nota nota){
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra("position", position);
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao);
    }

    private Nota criaNota(){
        String titulo = edt_titulo.getText().toString();
        String descricao = edt_descricao.getText().toString();
        Nota nota = new Nota(titulo, descricao);
        return nota;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salvar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
