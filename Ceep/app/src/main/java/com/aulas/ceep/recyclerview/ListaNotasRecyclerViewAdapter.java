package com.aulas.ceep.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.aulas.ceep.R;
import com.aulas.ceep.model.Nota;
import com.aulas.ceep.recyclerview.listener.OnItemClicklistener;

import java.util.Collections;
import java.util.List;

/**
 * Created by fjesus on 11/10/2018.
 */

public class ListaNotasRecyclerViewAdapter extends RecyclerView.Adapter<ListaNotasRecyclerViewAdapter.ViewHolder> {

    private List<Nota> notas;
    private Context context;
    private OnItemClicklistener onItemClicklistener;

    public ListaNotasRecyclerViewAdapter(Context context, List<Nota> notas){
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemClicklistener(OnItemClicklistener onItemClicklistener) {
        this.onItemClicklistener = onItemClicklistener;
    }

    public void remove(int position) {
        notas.remove(position);
        notifyItemRemoved(position);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView descricao;
        private Nota nota;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            descricao = itemView.findViewById(R.id.descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicklistener.onClick(nota, getAdapterPosition());
                }
            });
        }

        public void vincula(Nota nota){
            this.nota = nota;
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void adiciona(Nota nota){
        notas.add(nota);
        notifyDataSetChanged();

    }

    public void alterar(int position, Nota nota){
        notas.set(position, nota);
        notifyDataSetChanged();
    }
}
