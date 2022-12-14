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
    Boolean orderBy;
    String chaveOrder;

    public SQL(Queue<String> query,List<Tabela> lista_tabelas) {
        this.query = query;
        this.selectList = new LinkedList();
        this.fromList = new LinkedList();
        this.whereList = new LinkedList();
        this.joinList = new LinkedList();
        this.orderBy = false;
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

    public Boolean getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Boolean orderBy) {
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
            
            if(this.query.peek()!=null && this.query.peek().equals("on")){ //SE ENTROU salva na fromList os atributos requiridos
                i++;
                this.query.poll();
                int j=0;
                while(isCommand(query.peek())== false){
                    System.out.println(this.query.peek()+" - "+"LISTA VAZIA" +j);
                    System.out.println("Fila["+i +"]  = "+ this.query.peek());
                    i++;
                    this.joinList.add(this.query.poll());
                    System.out.println(this.query.peek()+" - "+this.joinList.get(j));
                    j++;
                }
            }
            
            if(this.query.peek()!=null && this.query.peek().equals("where")){  //SE ENTROU salva na whereList os atributos requiridos
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
            if(this.query.peek()!=null && this.query.peek().equals("order")){  //SE ENTROU define o elemento pelo qual a lista ser?? ordenada
                i+=2;
                this.query.poll();// elimina o "ORDER" da fila
                this.query.poll();// elimina o "BY" da fila
                this.orderBy = true;
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
        System.out.println("LAOSLKDOAKSDOAKS"+this.lista_tabelas.size());
        for(int i = 0; i<this.lista_tabelas.size();i++){
            int j = 0;
            //FAZ A COMPARA????O ENCONTRANDO AS TABELAS ESCOLHIDAS NO FROM E ADICIONANDO NA LISTA TABELAS
            if(this.fromList.isEmpty() && this.fromList.get(j).equals(this.lista_tabelas.get(i).getNomeTabela())){
                tabelas.add(this.lista_tabelas.get(i));
                j++;
            }
        }
        System.out.println("LAOSLKDOAKSDOAKS"+this.lista_tabelas.size());
        //FAZ A SELECTION(WHERE),ENCONTRA EM PRIMEIRA PARTE OS ATRIBUTOS QUE CORRESPONDE A CONDI????O REQUIRIDA
        //Busca em qual tabela est?? o elemento
        for(int i = 0; i<tabelas.size();i++){
            if(whereFlag && tabelas.get(i).getColunaPeloNome(this.whereList.get(i))<99){
                whereTabelaPos = i;
            }
        }


        int n_elementos = 0; // n??mero de colunas que a tabela resultado ter??
        int[] pos_tabela = new int[this.fromList.size()]; // guarda a posi????o da tabela usada na lista_tabela 
       
        int n_linhas = 0;
        int n_colunas_total = 0;
        int m;
        boolean encontrouTabela = false;
        
        int[] quantidadePorTabela = new int[this.fromList.size()];//Guarda a quantidade de colunas usadas por tabela
        for(int i = 0 ; i < this.fromList.size();i++){
            quantidadePorTabela[i] = 0;
        }
        
        if(this.selectList.get(0).contains("*")){
            for(int i = 0; i< this.fromList.size();i++){
                for( m = 0; m < this.lista_tabelas.size(); m++ ){
                    System.out.println(this.fromList.get(i)+" COMPARA????O "+ this.lista_tabelas.get(m).getNomeTabela());
                    //System.out.println(lista_tabelas.toString());
                    if(this.fromList.get(i).equals(this.lista_tabelas.get(m).getNomeTabela())){
                       
                        pos_tabela[i] = m; 
                        encontrouTabela = true;
                    }
                }
                if( encontrouTabela == false && m == this.lista_tabelas.size()){
                    System.out.println("TABELA N??O ENCONTRADA");
                    return;
                }
                System.out.println("JO"+this.fromList.get(i)+this.lista_tabelas.indexOf(this.fromList.get(i))+ pos_tabela[i]);
                n_elementos += this.lista_tabelas.get(pos_tabela[i]).getColunas();
                n_linhas += this.lista_tabelas.get(pos_tabela[i]).getLinhas();
                n_colunas_total += this.lista_tabelas.get(pos_tabela[i]).getColunas();
                quantidadePorTabela[i] =  this.lista_tabelas.get(pos_tabela[i]).getColunas(); 
            }
        }else{
            n_elementos = this.selectList.size(); // n??mero de colunas que a tabela resultado ter??
            for(int i = 0; i< this.fromList.size();i++){
                for(int j = 0; j <this.lista_tabelas.size();j++){
                    if(this.lista_tabelas.get(j).nomeTabela.contentEquals(this.fromList.get(i))){
                    pos_tabela[i] = j; //FALTA IMPLEMENTAR CASO OCORRA O JOIN
                     System.out.println("entrei");
                    quantidadePorTabela[i] += 1;
                    }
                    System.out.println("JO"+this.fromList.get(i) + this.lista_tabelas.get(j).nomeTabela);
                }
                n_linhas += this.lista_tabelas.get(pos_tabela[i]).getLinhas();
                n_colunas_total += this.lista_tabelas.get(pos_tabela[i]).getColunas(); // FAZER IGUAL o tam[] e  o pos tabela[] pra dar o JOINs
                
            }
        }

        List<String> tabelaResultado = new LinkedList<>();//n_elementos*n_linhas);
                        
        
        int[] tam = new int[n_elementos];
        
        System.out.println( "QQQQ "+ n_elementos);
        //n_elementos--;
        
        
        boolean joinONList = false;
        if(this.selectList.get(0).contains("*") && fromList.size() == 1){
            for(int j = 0; j < n_elementos ; j++){
                tam[j] = j;
            }
        }
        else if(this.selectList.get(0).contains("*") && fromList.size() > 1){
            int k = 0;
            for(int i = 0; i < fromList.size();i++){
                for(int j = 0; j < this.lista_tabelas.get(pos_tabela[i]).getColunas() ; j++){
                    tam[k] = j;
                    k++;
                }
            }
        }
        else{
            for (int i = 0; i < pos_tabela.length ; i++){
                for(int j = 0; j < n_elementos ; j++){
                    tam[j] = this.lista_tabelas.get(pos_tabela[i]).getColunaPeloNome(this.selectList.get(j));
                    System.out.println(tam[j] + "QQQ "+ n_elementos);
                            if(tam[j]==99){// PERCORREU A TABELA E N??O ACHOU
                                System.out.println(" ELEMENTO N??O ENCONTRADO!!");
                                return;
                            }
                    if((whereFlag==true) && (this.selectList.get(j).contains(whereList.get(0)))){
                        whereTabelaPos = j;
                    }
                }
            }
        }
        
        
        if(fromList.size()>1){ // FAZ O JOIN SE A FROMLIST POSSUIR MAIS DE DOIS ELEMENTOS(IMPLEMENTAMOS PARA FUNCIONAR COM 2 ELEMENTOS)         
            tabelaResultado = nestedLoopJoin(tabelaResultado,pos_tabela,tam,quantidadePorTabela);
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
            else if(joinONList == true){
                
                for(int k = 0; k < pos_tabela.length ; k++){
                    for(int i = 0 ; i < n_linhas;i++){
                        for(int j = 0 ; j < n_elementos ; j++){
                            tabelaResultado.add(this.lista_tabelas.get(pos_tabela[k]).getElemento(tam[j]));
                            tam[j] += n_colunas_total; //Faz com que os elementos das colunas desejadas sejam achados.
                        }
                    }
                }   
            }
            tabelaResultado.add("fim");
        }
        
        else{ 
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
            
            tabelaResultado.add("fim");
        }
        
        // select * from employees1000,livros-db
        // select first_name gender from employees1000, livros-db
        // select first_name gender from employees1000 where gender = F _ FUNCIONA
        // select first_name gender from employees1000 where first_name = Mary NAO FUNCIONA
        
        if(this.orderBy == true){
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
        
            for(int j = 0; j < tcolunas ; j++){
                System.out.print(" | " + tabelaResultado.get(tam));
                tam++;
            }
            System.out.print(" | ");
            System.out.println("\n  --------------------------------------------------------------");
            if(tabelaResultado.get(tam).contains("fim")){
                return;
            }
        }
    }
    
    private int[] maiorTabela(int[] pos_tabela){ //VERIFICA A MENOR TABELA E COLOCA NA POSI????O CENTRAL DO LOOP
        
        if(this.lista_tabelas.get(pos_tabela[0]).getLinhas() > this.lista_tabelas.get(pos_tabela[1]).getLinhas()){
            int aux = pos_tabela[0];
            pos_tabela[0] = pos_tabela[1];
            pos_tabela[1] = aux;
        }
        
        return pos_tabela;
        
    }
    
    public List<String> nestedLoopJoin(List<String> tabelaResultado, int[] pos_tabela,int[] select_pos,int[] coluna_select){
        //TRABALHO 2
        pos_tabela = maiorTabela(pos_tabela); // A MAIOR TABELA RODA DENTRO DO LOOP DA MENOR TABELA
       /* for each tuple t.r in r do begin
            for each tuple t.s in s do begin
                test pair (t.r,t.s) to see if they satisfy the join condition ??
                if they do, add t.r ??? t.s to the result.
            end
        end
        r is called the outer relation and s the inner relation of the join.
        */
       
        //select name from pessoa trabalho on cpf
               
        for(int i = 0; i< this.joinList.size() ; i++){
       
            System.out.println("JOIN ON" +this.joinList.get(i));
        }
       
        int[] pos_join = new int[2]; // GUARDA A POSI????) DOS ELEMENTO CHAVE EM SUAS RESPECTIVAS TABELAS [0] = tabela1 ||||| [1] = tabela2
        
        // pos_tabela -> posi????o da tabela na lista tabela
        
        for (int i = 0; i < pos_tabela.length ; i++){
            for(int j = 0; j < 2 ; j++){
                
                pos_join[j] = this.lista_tabelas.get(pos_tabela[i]).getColunaPeloNome(this.joinList.get(0));
                
                System.out.println(pos_join[j] + "LOOPcompareID TABELA - "+ i);
                
                        if(pos_join[j]==99){// PERCORREU A TABELA E N??O ACHOU
                            System.out.println(" ELEMENTO N??O ENCONTRADO!!");
                            return null;
                        }
            }
        }
        
        
        //ADICIONANDO OS HEADERS
        int p = 0;
        for(int k = 0; k < select_pos.length; k++){
            System.out.println(pos_join[p] + "DENTRO DO LOOP K - " + k + " COLUNA "+ select_pos[k]+" P - " +p + this.lista_tabelas.get(pos_tabela[p]).getElemento(select_pos[k]));
                if(k == (coluna_select[p]-1)){
                                
                    p = 1;
                }
            tabelaResultado.add(this.lista_tabelas.get(pos_tabela[p]).getElemento(select_pos[k]));
            select_pos[k] = select_pos[k] + this.lista_tabelas.get(pos_tabela[p]).getColunas();
        }
        
        
        pos_join[0] = pos_join[0] + this.lista_tabelas.get(pos_tabela[0]).getColunas();
        pos_join[1] = pos_join[1] + this.lista_tabelas.get(pos_tabela[1]).getColunas();
        int aux = pos_join[0];
        int auxb = pos_join[1];
        
        
        int[] auxSelect = select_pos;
        
        for (int i = 0; i < select_pos.length ; i++){
           System.out.println(i + " DENTRO DO SELECT - "+ select_pos[i]);
        }
        
        for(int i = 0 ; i < this.lista_tabelas.get(pos_tabela[0]).getLinhas();i++){
            //System.out.println( " i " + i );
            if(pos_join[0] > this.lista_tabelas.get(pos_tabela[0]).getTamanho()){
                break;
            }
            if(i>=1){
                pos_join[0] = aux +( i * this.lista_tabelas.get(pos_tabela[0]).getColunas());
            }
            //System.out.println( " POSI??AO DO JOIN i " + pos_join[0] );
            
                for(int j = 0 ; j < this.lista_tabelas.get(pos_tabela[1]).getLinhas() ; j++){
                    //System.out.println( " i " + i + " j " + j);
                    // System.out.println( " POSI??AO DO JOIN j " + pos_join[1] );
                    if(this.lista_tabelas.get(pos_tabela[0]).getElemento(pos_join[0]).contentEquals(this.lista_tabelas.get(pos_tabela[1]).getElemento(pos_join[1]))){
                        p = 0;
                        
                        for(int k = 0; k < select_pos.length; k++){
                            System.out.println(pos_join[p] + "DENTRO DO LOOP K - " + k + " COLUNA "+ select_pos[k]+" P - " +p + this.lista_tabelas.get(pos_tabela[p]).getElemento(select_pos[k]));
                            if(k == (coluna_select[p]-1)){
                                
                                p = 1;
                                //System.out.println(pos_join[j] + "DENTRO DO LOOP K - " + k + " COLUNA "+ select_pos[k]+" P - " +p);
                            }
                            tabelaResultado.add(this.lista_tabelas.get(pos_tabela[p]).getElemento(select_pos[k]));
                            if(p==1){
                                select_pos[k] = select_pos[k] + this.lista_tabelas.get(pos_tabela[p]).getColunas();
                            }
                            
                        }
                    }
                    else{
                        for(int k = 0; k < select_pos.length; k++){
                            select_pos[k] = select_pos[k] + this.lista_tabelas.get(pos_tabela[p]).getColunas();
                        }
                    }
                    
                    //Faz com que os elementos das colunas desejadas sejam achados.
                    pos_join[1] = pos_join[1] + this.lista_tabelas.get(pos_tabela[1]).getColunas();
                    if(pos_join[1] > this.lista_tabelas.get(pos_tabela[1]).getTamanho()){
                        break;
                    }
                    
                }
            for(int k = 0; k < coluna_select[0]; k++){
                select_pos[k] = select_pos[k] + this.lista_tabelas.get(pos_tabela[p]).getColunas();
            }
            for(int k = coluna_select[0]; k < select_pos.length; k++){
                select_pos[k] = auxSelect[k];
            }
            pos_join[1] = auxb;
        }
        return tabelaResultado;
        
       /* select * from trabalho pessoa on cpf TESTE 
    
    DUAS FORMAS DE LER 
    
    SELECT *
    FROM tabela1, tabela2 
    WHERE tabela1.id=tabela2.id
    
    SELECT *
    FROM tabela1
    JOIN tabela2
    ON tabela1.Key = tabela2.Key
    */
    }
    
    /*

    public void selection(List<String> whereList,Tabela tabela){//PROCESSAMENTO DO WHERE
        //{=, <, ???, >, ???, ???} OPERADORES PARA TABELAS ORDENADAS
        //{=, ???} OPERADORES PARA VALORES DESORDENADOS
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
    */

    private boolean isCommand(String elemento){
        if(elemento == null){
            return true;
        }
        else if(elemento.equals("where")){
            return true;
        }
        else if(elemento.equals("order")){
            return true;
        }
        else if(elemento.equals("on")){
            return true;
        }
        else if(elemento.equals("from")){
            return true;
        }
        else{return false;}
    }
    
    
    
    private boolean executaWhere(String elemento){
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
        
        //boolean resultado = comparaWhere();
        
        return false;
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
