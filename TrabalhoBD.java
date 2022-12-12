/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabalhobd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author julioc7r
 */
public class TrabalhoBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
       // String[] ma = new String[1];
        Scanner input = new Scanner(System.in);
        
        //EscolheArquivo tela1 = new EscolheArquivo(); Tentativa de fazer pela interface gráfica
        
        BancoDeDados Banco = new BancoDeDados(); //Cria um Banco de Dados
        
        
        System.out.println("Digite o local onde está o arquivo?"); // TESTE /Users/julioc7r/testecsv
        String caminhoArquivo = "/Users/julioc7r/testecsv";//input.nextLine(); // Fazer a busca por um repositório************
    
        File arquivoCSV = new File(caminhoArquivo);
        File [] lista_arquivosCSV = arquivoCSV.listFiles();
        System.out.println("Digite o local onde está o arquivo?"+lista_arquivosCSV.length + Arrays.toString(arquivoCSV.listFiles()));
        if(arquivoCSV.exists()==false){
            System.out.println("Arquivo não encontrado/Verifique novamento o caminho");
            return;
        }    
        int n_tabelas = 0;
        for(int t = 0; t < lista_arquivosCSV.length; t++){
            if(lista_arquivosCSV[t].getName().contains(".csv")){
                    BufferedReader br = new BufferedReader(new FileReader(lista_arquivosCSV[t]));
                    
                    int qtd_linhas;
                    String line = "not null";
                    for(qtd_linhas = -1; line != null; qtd_linhas++){// conta quantas linhas tem o arquivo
                        line = br.readLine();
                    }
                    //overwrite old buffered reader/ reseta o arquivo para a primeira posição
                    br = new BufferedReader(new FileReader(lista_arquivosCSV[t]));
                    

                    line = br.readLine ();
                    
                        String fator_divisor = ",";
                        if(line.contains(",")==true){
                            fator_divisor = ",";
                        }
                        else if(line.contains(";")==true){
                            fator_divisor = ";";
                        }
                        
                    String[] vect = line.split(fator_divisor); //Divide pela as colunas da tabela
                    String primeiroHeader = vect[0];
                    
                    System.out.println("nome:"+ lista_arquivosCSV[t].getName()+ " linhas:" + qtd_linhas + " colunas:" +vect.length);
                    Banco.lista_tabelas.add(new Tabela(lista_arquivosCSV[t].getName().replace(".csv", ""), qtd_linhas, vect.length));
                    
                    Banco.setN_tabelas(n_tabelas);//atualiza o numero de tabelas no banco
                    
                    
                    //Banco.lista_tabelas.get(t).toString();
                    //System.out.println(Banco.lista_tabelas.get(t).toString());

                    while (line != null) {
                        System.out.println(line);
                        
                        vect = line.split(fator_divisor); //divide no vetor apartir da virgula
                        if(vect[0].equals(primeiroHeader)){
                            for (int i = 0;i < vect.length;i++){
                                vect[i].toLowerCase();//NAOFUNCINOANSdkm
                            }
                        }
                        Banco.lista_tabelas.get(n_tabelas).adicionaElemento(vect);
                        line = br.readLine();
                    }
                    
                    System.out.println("Tabela:");
                    Banco.lista_tabelas.get(n_tabelas).printTabela();
                    n_tabelas++;
                
            }
        }
            int opção = 0;
            String pesquisa;
            char confirma = 'N';
            String elemento;
            
            do{
                System.out.println("TABELAS:");
                for(int i = 0 ; i < Banco.getN_tabelas();i++){
                    System.out.println("nome:"+ Banco.lista_tabelas.get(i).toString());
                }
                System.out.println("Bem-vindo!");
                System.out.println("==========");
                System.out.println("1 - Query");
                System.out.println("9 - Sair");
                System.out.println(":");
                opção = input.nextInt();

                switch(opção){
                    case 1:
                        System.out.println("\nDIGITE A SQL QUEUE");
                        elemento = input.nextLine();
                        elemento = input.nextLine();
                        
                        String[] vect = elemento.split(" ");
                        
                        Queue <String> query = new LinkedList();
                        int orderBy = 0; //Flag para se possuir o orderby        
                        
                        
                        // QUEUE TEST 1 | select emp_no birth_date from employees where // FUNCIONA 
                            // QUEUE TEST 2 | select first_name gender from employees1000 order by gender // FUNCIONA
                        
                        // CAMINHOTESTE /Users/julioc7r/testecsv
                    
                        // emp_no,birth_date,first_name,last_name,gender,hire_date
                        
                        
                        for(int i = 0; i<vect.length ;i++){ // coloca tudo em letra minuscula e retira a virgula e o 
                            System.out.println(vect[i]);    // ponto e virgula que fica na hora que a string é dividida no vetor vect[].
                            vect[i].toLowerCase();
                            
                            if(vect[i].contains(",")){
                                vect[i].replace(",", "");
                            }
                            if(vect[i].contains(";")){
                                vect[i].replace(";", "");
                            }
                            query.add(vect[i]); // Adiciona na fila Query 
                        }
                 
                        Banco.executaSQL(query);
                        /*
                        
                        for(int i = 0 ; i < vect.length ;i ++)
                        {
                            System.out.println("entrei"+ i);
                            if(i == 0 && vect[i].equals("select")){
                                i++;
                                query.poll();
                                int j = 1;
                                while(vect[i].equals("from")== false){//conta elementos do SELECT
                                    i++;
                                    j++;
                                }
                                i--;
                                selectList = new String[j];
                                j = 0;
                                do{
                                    System.out.println(vect[j+1]+" - "+selectList[j]+j);
                                    //selectList[j] = vect[j+1];
                                    selectList[j] = query.poll();
                                    System.out.println(vect[j+1]+" - "+selectList[j]+j);
                                    j++;
                                }while(vect[j+1].equals("from")== false);
                            }
                            
                            if(vect[i].equals("from")){
                                //i++;
                                int j = 0;
                                while((vect[i+1].equals("where")== false)){//||(vect[i+1].equals("order")== false)){//conta elementos do SELECT
                                    i++;
                                    j++;
                                    System.out.println("entrei"+ i + j);
                                }
                                i-=j-1;
                                fromList = new String[j];
                                j = 0;
                                do{
                                    System.out.println(vect[i]+" - "+fromList[j]+j);
                                    fromList[j] = vect[i];
                                    System.out.println(vect[i]+" - "+fromList[j]+j+"i "+i);
                                    j++;
                                    i++;
                                }while((vect[i].equals("where")== false));//||(vect[i].equals("order")== false));
                            }
                        
                            if(vect[i].equals("order") && vect[i+1].equals("by")){
                                orderBy=1;
                                i = i + 2;
                                System.out.println("oi"+i);
                                int k; // posição do atributo que vai ser usado para ordenar a tabela no selectList
                                if(vect[i].equals(selectList[0])== false){
                                    for(k = 1; k < selectList.length; k++){
                                        if(vect[i].equals(selectList[k]))
                                        break;
                                    }
                                    String swap = selectList[0];
                                    selectList[0] = selectList [k];
                                    selectList[k] = swap;
                                    System.out.println("oi");
                                }
                            }
                        
                        }
                        
                        int i;
                        
                        Tabela t = lista_tabelas.get(0);
                        t.resultadoQuery(selectList,orderBy);
                         */

                        
                    case 9:
                       System.out.println("Tem certeza que deseja sair?(S)para sair");
                       confirma = input.next().toUpperCase().charAt(0);
                       break;
                       
                }
            } while(confirma != 'S');
        
        input.close();
    }

}
