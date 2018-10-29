package com.aulas.ceep.recyclerview.helper.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.aulas.ceep.dao.NotaDao;
import com.aulas.ceep.model.Nota;
import com.aulas.ceep.recyclerview.ListaNotasRecyclerViewAdapter;

/**
 * Created by fjesus on 16/10/2018.
 */

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasRecyclerViewAdapter listaNotasRecyclerViewAdapter;

    public NotaItemTouchHelperCallback(ListaNotasRecyclerViewAdapter listaNotasRecyclerViewAdapter) {
        this.listaNotasRecyclerViewAdapter = listaNotasRecyclerViewAdapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);

        return true;
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        new NotaDao().troca(posicaoInicial, posicaoFinal);
        listaNotasRecyclerViewAdapter.troca(posicaoInicial,posicaoFinal);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        removeNota(position);

    }

    private void removeNota(int position) {
        new NotaDao().remover(position);
        listaNotasRecyclerViewAdapter.remove(position);
    }
}
