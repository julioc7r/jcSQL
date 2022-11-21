/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhobd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author julioc7r
 */
public class SQL {
    Queue <String> query;
    List<Tabela> lista_tabelas;
    List<String> selectList;
    List<String> fromList;
    List<String> whereList;
    List<String> joinList;
    Boolean whereFlag;
    int orderBy;
    String chaveOrder;

    public SQL(Queue<String> query,List<Tabela> lista_tabelas) {
        this.query = query;
        this.selectList = new LinkedList();
        this.fromList = new LinkedList();
        this.whereList = new LinkedList();
        this.joinList = new LinkedList();
        this.orderBy = 0;
        this.whereFlag = false;
        this.chaveOrder = null;
        this.lista_tabelas = lista_tabelas;
        
    }

    public Queue<String> getQuery() {
        return query;
    }

    public void setQuery(Queue<String> query) {
        this.query = query;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }
    
    public void processamentoQuery() {
        
        for(int i = 0;this.query.isEmpty() == false; i++)
        {
            System.out.println("Fila["+i +"]  = "+ this.query.peek());
            if(this.query.peek().equals("select")){ //SE ENTROU salva na selectList os atributos requiridos
                i++;//para printar a ordem dos elementos
                this.query.poll();
                int j=0;
                while(isCommand(query.peek())== false){
                    System.out.println(this.query.peek()+" - "+ "LISTA VAZIA" +j);
                    System.out.println("Fila["+i +"]  = "+ this.query.peek());
                    i++;
                    this.selectList.add(this.query.poll());
                    System.out.println(this.query.peek()+" - "+this.selectList.get(j));
                    j++;
                }
            }
            if(this.query.peek().equals("from")){ //SE ENTROU salva na fromList os atributos requiridos
                i++;
                this.query.poll();
                int j=0;
                while(isCommand(query.peek())== false){
                    System.out.println(this.query.peek()+" - "+"LISTA VAZIA" +j);
                    System.out.println("Fila["+i +"]  = "+ this.query.peek());
                    i++;
                    this.fromList.add(this.query.poll());
                    System.out.println(this.query.peek()+" - "+this.fromList.get(j));
                    j++;
                }
            }
            if(this.query.peek().equals("where")){  //SE ENTROU salva na whereList os atributos requiridos
                this.whereFlag = true;
                i++;
                this.query.poll();
                int j=0;
                while(isCommand(query.peek())== false){
                    System.out.println(this.query.peek()+" - "+"LISTA VAZIA" +j);
                    System.out.println("Fila["+i +"]  = "+ this.query.peek());
                    i++;
                    this.whereList.add(this.query.poll());
                    //System.out.println(this.query.peek()+" - "+this.whereList.get(j));
                    j++;
                    if(query.isEmpty()){
                        return;}
                    }
            }              
            if(this.query.peek().equals("order")){  //SE ENTROU define o elemento pelo qual a lista será ordenada
                i+=2;
                this.query.poll();// elimina o "ORDER" da fila
                this.query.poll();// elimina o "BY" da fila
                this.orderBy = 1;
                this.chaveOrder = this.query.poll();
                System.out.println("Fila["+i +"]  = "+"CHAVE  = "+ this.chaveOrder);
            }
        }
    }
   
    public void resultadoQuery(){
        //Pensar em como fazer
        processamentoQuery();
        int whereTabelaPos = -1;

        //ENCONTRA AS TABELAS USADAS
        List<Tabela> tabelas = new LinkedList();
        for(int i = 0; i<this.lista_tabelas.size();i++){
            int j = 0;
            //FAZ A COMPARAÇÃO ENCONTRANDO AS TABELAS ESCOLHIDAS NO FROM E ADICIONANDO NA LISTA TABELAS
            if(this.fromList.isEmpty() && this.fromList.get(j).equals(this.lista_tabelas.get(i).getNomeTabela())){
                tabelas.add(this.lista_tabelas.get(i));
                j++;
            }
        }
        //FAZ A SELECTION(WHERE),ENCONTRA EM PRIMEIRA PARTE OS ATRIBUTOS QUE CORRESPONDE A CONDIÇÃO REQUIRIDA
        //Busca em qual tabela está o elemento
        for(int i = 0; i<tabelas.size();i++){
            if(whereFlag && tabelas.get(i).getColunaPeloNome(this.whereList.get(i))<99){
                whereTabelaPos = i;
            }
        }


        int n_elementos = 0; // número de colunas que a tabela resultado terá
        int[] pos_tabela = new int[this.fromList.size()]; // guarda a posição da tabela usada na lista_tabela 
        
        int n_linhas = 0;
        int n_colunas_total = 0;
        
        if(this.selectList.get(0).contains("*")){
            for(int i = 0; i< this.fromList.size();i++){
                pos_tabela[i] = this.lista_tabelas.indexOf(this.fromList.get(i)); //FALTA IMPLEMENTAR CASO OCORRA O JOIN
                n_elementos += this.lista_tabelas.get(pos_tabela[i]).getColunas();
                n_linhas += this.lista_tabelas.get(pos_tabela[i]).getLinhas();
                n_colunas_total += this.lista_tabelas.get(pos_tabela[i]).getColunas();
            }
        }else{
            n_elementos = this.selectList.size(); // número de colunas que a tabela resultado terá
            for(int i = 0; i< this.fromList.size();i++){
                for(int j = 0; j <this.lista_tabelas.size();j++){
                    if(this.lista_tabelas.get(j).nomeTabela.contentEquals(this.fromList.get(i))){
                    pos_tabela[i] = i; //FALTA IMPLEMENTAR CASO OCORRA O JOIN
                    }
                }
                n_linhas += this.lista_tabelas.get(pos_tabela[i]).getLinhas();
                n_colunas_total += this.lista_tabelas.get(pos_tabela[i]).getColunas(); // FAZER IGUAL o tam[] e  o pos tabela[] pra dar o JOINs
                
            }
        }

        List<String> tabelaResultado = new ArrayList<>(n_elementos*n_linhas);
                        
        
        int[] tam = new int[n_elementos];
        
        System.out.println( "QQQQ "+ n_elementos);
        //n_elementos--;
        
        
        // select first_name gender from employees1000 order by gender
        // select first_name gender from employees1000 where gender = F
        
        for (int i = 0; i < pos_tabela.length ; i++){
            for(int j = 0; j < n_elementos ; j++){
                tam[j] = this.lista_tabelas.get(pos_tabela[i]).getColunaPeloNome(this.selectList.get(j));
                System.out.println(tam[j] + "QQQQ "+ n_elementos);
                if((whereFlag==true) && (this.selectList.get(j).contains(whereList.get(0)))){
                    whereTabelaPos = j;
                }
            }
        }
        
        if(whereFlag==true){
            for(int k = 0; k < pos_tabela.length ; k++){
                for(int i = 0 ; i < n_linhas;i++){
                    for(int j = 0 ; j < n_elementos ; j++){
                        if(comparaWhere(this.lista_tabelas.get(pos_tabela[k]).getElemento(tam[whereTabelaPos]))==true){
                            tabelaResultado.add(this.lista_tabelas.get(pos_tabela[k]).getElemento(tam[j]));
                        }
                        else if(tabelaResultado.size()<n_elementos){//ADICIONA OS PRIMEIROS ELEMENTOS
                            tabelaResultado.add(this.lista_tabelas.get(pos_tabela[k]).getElemento(tam[j]));
                        }
                        tam[j] += n_colunas_total; //Faz com que os elementos das colunas desejadas sejam achados.
                    }
                }
            }   
        }
        else{
            for(int k = 0; k < pos_tabela.length ; k++){
                for(int i = 0 ; i < n_linhas;i++){
                    for(int j = 0 ; j < n_elementos ; j++){
                        tabelaResultado.add(this.lista_tabelas.get(pos_tabela[k]).getElemento(tam[j]));
                        tam[j] += n_colunas_total; //Faz com que os elementos das colunas desejadas sejam achados.
                    }
                }
            }   
        }
        if(this.orderBy == 1){
            orderBy(this.chaveOrder,tabelaResultado,n_elementos);
        }
            System.out.println("aaaaaaaaa  "+tabelaResultado.size());
        printTabelaResultado(tabelaResultado,n_elementos,tabelaResultado.size());
    } 
    
    public void orderBy(String elemento, List<String> tabela, int tam_colunas){
        int tam = tam_colunas;
        int ordenado = 0;
        while(ordenado==1){
            ordenado = 1;
            for(int i = 0 ; i < tabela.size();i = i + tam_colunas){
                if(tabela.get(i).compareTo(tabela.get(i+tam_colunas))<0){
                    for(int j = 0 ; j < tam_colunas ; j++){
                        String swap = tabela.get(i+j);
                        tabela.set(i+j,tabela.get(i+j+tam_colunas));
                        tabela.set(i+j+tam_colunas,swap);
                    }
                    ordenado = 0;
                }
            }
        }
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
    }/*

    public void nestedLoopJoin(){
        //TRABALHO 2
        /*
        To compute the theta join r ⨝ θ s
        
        for each tuple t.r in r do begin
            for each tuple t.s in s do begin
                test pair (t.r,t.s) to see if they satisfy the join condition θ
                if they do, add t.r • t.s to the result.
            end
        end

        r is called the outer relation and s the inner relation of the join.
        *//*
    }

    public void selection(List<String> whereList,Tabela tabela){//PROCESSAMENTO DO WHERE
        //{=, <, ≤, >, ≥, ≠} OPERADORES PARA TABELAS ORDENADAS
        //{=, ≠} OPERADORES PARA VALORES DESORDENADOS
        int i = 0;

        if(whereList.get(i).contentEquals("=")){
            tabela = tabela.comparaIgual(tabela,elemento,chave);
        }
        else if(whereList.get(i).contentEquals("!=")){
            tabela = tabela.comparaDiferente(tabela,elemento,chave);
        }
        else if(whereList.get(i).contentEquals(">")){
            tabela = tabela.comparaMaior(tabela,elemento,chave);
        }
        else if(whereList.get(i).contentEquals("<")){
            tabela = tabela.comparaMenor(tabela,elemento,chave);
        }

        
    }
    
    public void projection(){//PROCESSAMENTO DO SELECT
        
    }*/

    private boolean isCommand(String elemento){
        if(elemento.equals("from")){
            return true;
        }
        else if(elemento.equals("where")){
            return true;
        }
        else if(elemento.equals("order")){
            return true;
        }
        else if(elemento == null){
            return true;
        }
        else{return false;}
    }
    private boolean comparaWhere(String elemento){
        if(whereList.get(1).contains("=")){
            if(elemento.equals(this.whereList.get(2))){ //IGUAL
            return true;}
        }
        else if(whereList.get(1).contains("!=")){
            if(!elemento.equals(this.whereList.get(2))){ //DIFERENTE
            return true;}
        }
        else if(whereList.get(1).contains(">")){
            if(elemento.compareTo(this.whereList.get(2))>0){ //MAIOR
            return true;}
        }
        else if(whereList.get(1).contains("<")){
            if(elemento.compareTo(this.whereList.get(2))<0){ //MENOR
            return true;}
        }
        else{return false;}
        return false;
    }
}
