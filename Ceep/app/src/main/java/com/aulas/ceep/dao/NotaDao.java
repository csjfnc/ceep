package com.aulas.ceep.dao;

import com.aulas.ceep.model.Nota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by fjesus on 11/10/2018.
 */

public class NotaDao {

    private final static ArrayList<Nota> notas = new ArrayList<>();


    public List<Nota> todos() {
        return (List<Nota>) notas.clone();
    }

    public void insere(Nota... notas ){
        NotaDao.notas.addAll(Arrays.asList(notas));
    }

    public void alterar(int position, Nota nota){
        notas.set(position, nota);
    }

    public void remover(int position){
        notas.remove(position);
    }

    public void troca(int posicaoInicio, int posicaoFim){
        Collections.swap(notas, posicaoInicio, posicaoFim);
    }

    public void removerTodos(){
        notas.clear();
    }

}
