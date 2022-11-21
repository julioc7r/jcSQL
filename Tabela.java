/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhobd;


import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author julioc7r
 */
public class Tabela {
    
    String nomeTabela;
    
    int tamanho;
    
    private final int linhas;
    private final int colunas;
    private  List<String> elementos; // mudar para qualquer tipo de variavel
    
    
    public Tabela(String nomeTabela,int linhas, int colunas) {
        this.nomeTabela = nomeTabela;
        this.tamanho = 0;
        this.linhas = linhas;
        this.colunas = colunas;
        elementos = new ArrayList<>(linhas * colunas);
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public int getColunas() {
        return colunas;
    }

    public int getLinhas() {
        return linhas;
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    public String getElemento(int posicao) {
        return elementos.get(posicao);
    }
    
    public void projecao(String elemento){
        
        if(elemento.compareTo("*")==0){
            printTabela();
        }
        else{
            int i = 0;
            while(i < colunas){
                System.out.println(i + " - "+ elemento + "=" + elementos.get(i));
                if(elemento.compareTo(elementos.get(i))==0){
                    break;
                }
                i++;
            }
            if(i == colunas){
                System.out.println(" não foi encontrado ");
                return;}
            else{
                printTabelaColuna(i);
            }
        }
    }
    
    public int getColunaPeloNome(String elemento){
        int i = 0;
            while(i < colunas){
                System.out.println(i + " - "+ elemento + "=" + elementos.get(i));
                if(elemento.compareTo(elementos.get(i))==0){
                    return i;
                }
                i++;
            }
        return 99;
    }

    /*
    public String get(int linha, int coluna) {
        if (!posicaoValida(linha, coluna)) throw new IllegalArgumentException();
        return elementos.get(posicaoNaLista(linha, coluna));
    }*/
    
    public void adicionaElemento(String[] elemento) {
        for(int i = 0; i<elemento.length ;i++){
            elementos.add(elemento[i]);
            this.tamanho = this.tamanho + 1;
        }
    }
    
    public void printTabela(){
        System.out.println("  --------------------------------------------------------------");
        int tam = 0;
        for(int i = 0; i < linhas ; i++){
            
            for(int j = 0; j<colunas ; j++){
                System.out.print(" | " + elementos.get(tam));
                tam++;
            }
            System.out.print(" | ");
            System.out.println("\n  --------------------------------------------------------------");
        
        }
        
    }
    
    public void printTabelaColuna(int coluna){
        System.out.println("  ------------------------");
        int tam = coluna;
        for(int i = 0; i < linhas ; i++){
            
            System.out.print(" | " + elementos.get(tam));
            tam = tam + this.colunas;
            System.out.print(" | ");
            System.out.println("\n  --------------------------");
        
        }
        
    }
    
    @Override
    public String toString() {
        return "Tabela{" + "nomeTabela=" + nomeTabela + ", linhas=" + linhas + ", tamanho=" + tamanho + ", colunas=" + colunas + '}';
    }

    private void printTabelaResultado(List<String> tabelaResultado, int tcolunas, int tlinhas) {
        int tam = 0;
        System.out.println("  --------------------------------------------------------------");
        for(int i = 0; i < tlinhas ; i++){
            
            for(int j = 0; j<tcolunas ; j++){
                System.out.print(" | " + tabelaResultado.get(tam));
                tam++;
            }
            System.out.print(" | ");
            System.out.println("\n  --------------------------------------------------------------");
        
        }
    }
    /*
    
    private void ordenaTabela(List<String> tabelaResultado, int tcolunas, int tlinhas) { // IMPLEMENTAR O MERGE SORT
        int tam = 0;
        System.out.println("  --------------------------------------------------------------");
        for(int i = 0; i < tlinhas ; i++){
            
            for(int j = 0; j<tcolunas ; j++){
                System.out.print(" | " + tabelaResultado.get(tam));
                tam++;
            }
            System.out.print(" | ");
            System.out.println("\n  --------------------------------------------------------------");
        
        }
    }
    
    public List<String> comparaIgual(List<String> tabela,String elemento,String chave){
        int n_elementos = selectFila.length;
        int n_linhas = this.linhas;
        List<String> tabelaResultado = new ArrayList<>(n_elementos*n_linhas);
        return tabela;
    }
    public List<String> comparaDiferente(List<String> tabela,String elemento,String chave){
        return tabela;
    }
    public List<String> comparaMaior(List<String> tabela,String elemento,String chave){
        return tabela;
    }
    public boolean comparaMenor(List<String> tabela,String elemento,String chave){
        return false;
    }*/
    
}
