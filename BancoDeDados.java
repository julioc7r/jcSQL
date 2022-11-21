/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhobd;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author julioc7r
 */
public class BancoDeDados {
    List <Tabela> lista_tabelas;
    int n_tabelas;
    SQL pesquisa;

    public BancoDeDados() {
        this.lista_tabelas = new ArrayList<Tabela>();
        this.n_tabelas = 0;
    }

    public List<Tabela> getLista_tabelas() {
        return lista_tabelas;
    }

    public void setLista_tabelas(List<Tabela> lista_tabelas) {
        this.lista_tabelas = lista_tabelas;
    }

    public int getN_tabelas() {
        return n_tabelas;
    }

    public void setN_tabelas(int n_tabelas) {
        this.n_tabelas = n_tabelas;
    }

    public SQL getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(Queue<String> query,List<Tabela> lista_tabelas) {
        this.pesquisa = new SQL(query,lista_tabelas);
    }

    public void executaSQL(Queue<String> query){
        setPesquisa(query, this.lista_tabelas);
        pesquisa.resultadoQuery();
    }
    
}
