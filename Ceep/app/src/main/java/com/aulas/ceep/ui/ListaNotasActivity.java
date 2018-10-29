package com.aulas.ceep.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.aulas.ceep.R;
import com.aulas.ceep.dao.NotaDao;
import com.aulas.ceep.model.Nota;
import com.aulas.ceep.recyclerview.ListaNotasRecyclerViewAdapter;
import com.aulas.ceep.recyclerview.helper.callback.NotaItemTouchHelperCallback;
import com.aulas.ceep.recyclerview.listener.OnItemClicklistener;
import java.util.List;

import static com.aulas.ceep.util.NotasConstantes.CHAVE_NOTA;
import static com.aulas.ceep.util.NotasConstantes.CHAVE_POSICAO;
import static com.aulas.ceep.util.NotasConstantes.CODIGO_RESULTADO_NOTA_CRIADA;
import static com.aulas.ceep.util.NotasConstantes.REQUES_ALTERA_NOTA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_NOTAS = "Notas";
    private RecyclerView lista_notas_recyclerview;
    private ListaNotasRecyclerViewAdapter listaNotasRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_NOTAS);

        lista_notas_recyclerview = findViewById(R.id.lista_notas_recyclerview);
        lista_notas_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        final List<Nota> notas = pegarTodasNotas();

        configuraAdapter(notas);
    }

    private void configuraAdapter(List<Nota> notas) {
        listaNotasRecyclerViewAdapter = new ListaNotasRecyclerViewAdapter(this, notas);
        lista_notas_recyclerview.setAdapter(listaNotasRecyclerViewAdapter);

        listaNotasRecyclerViewAdapter.setOnItemClicklistener(new OnItemClicklistener() {
            @Override
            public void onClick(Nota nota, int position) {
                vaiParaFormularioActivityAltera(nota, position);
            }
        });

        configuraItemTouchHelper();

    }

    private void configuraItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(listaNotasRecyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(lista_notas_recyclerview);
    }

    private void vaiParaFormularioActivityAltera(Nota nota, int position) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra("position", position);
        startActivityForResult(abreFormularioComNota, REQUES_ALTERA_NOTA);
    }

    public List<Nota> pegarTodasNotas(){
        NotaDao notaDao = new NotaDao();
        for(int i = 0; i < 10; i++){
            notaDao.insere(new Nota("Titulo"+ (i+1), "Descrição"+(i+1)));
        }
        return notaDao.todos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add:{
                vaiParaFormuarioNotasActivityInsere();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void vaiParaFormuarioNotasActivityInsere() {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 2 && data.hasExtra(CHAVE_NOTA)){
            Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            listaNotasRecyclerViewAdapter.adiciona(nota);
        }

        if(requestCode == REQUES_ALTERA_NOTA && resultCode == CODIGO_RESULTADO_NOTA_CRIADA && data.hasExtra(CHAVE_NOTA)
                && data.hasExtra(CHAVE_POSICAO)){
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int position = data.getIntExtra(CHAVE_POSICAO, -1);
            if(position > -1) {
                new NotaDao().alterar(position, notaRecebida);
                listaNotasRecyclerViewAdapter.alterar(position, notaRecebida);
            }else{
                Toast.makeText(this, "Ocorreu um problema na alteração da nota", Toast.LENGTH_LONG).show();
            }
        }
    }
}
