package agentecoletor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Ambiente {

    private Nodo[][] m_matriz;
    private int tamanho, qtdLixeiras, qtdRecargas;
    private Agente jamesBond;

    public Ambiente(int tamanho, int[]posAgente, int qtdLixeiras, int qtdRecargas) {
        
       
        this.tamanho = tamanho;
        this.qtdLixeiras = qtdLixeiras;
        this.qtdRecargas = qtdRecargas;
        
        System.out.print("Total de Células: " + tamanho * tamanho + "\n");
        
        double qtdLixoPorcentagem = ((int) (Math.random() * (85 - 40))) + 40;
        System.out.print("Porcentagem de lixo: " + qtdLixoPorcentagem + "\n");
        
        int totalCelulasLixo = (int)( (tamanho*tamanho) * (qtdLixoPorcentagem/100));
        System.out.print("Células com Lixo: " + totalCelulasLixo + "\n");
        
        m_matriz = new Nodo[tamanho][tamanho];
        
        for(int i = 0; i < tamanho; i++) { //i: linha, j: coluna
            
            for(int j = 0; j < tamanho; j++) {
                //System.out.print("I: " + i + "    J:" + j+ "\n");
                 m_matriz[i][j] = new Nodo(EstadosNodo.celulaVazia);
                
                //desenhar parede da esquerda
                if(i == 2 && j == 2
                    || i == tamanho-3 && j == 2
                        || (i >= 2 && i <= tamanho-3) && j == 3)
                    m_matriz[i][j].setTipo(EstadosNodo.parede);
                
                //desenhar parede da direita
                else if(i == 3 && j == tamanho-4
                        || i == 2 && j == tamanho-3 
                            || i == tamanho-3 && j == tamanho-3
                                || (i >= 2 && i <= tamanho-3) && j == tamanho-4)
                    m_matriz[i][j].setTipo(EstadosNodo.parede);
                
                //colocar lixos
                
                else if((Math.random() * 1) > 0.5 && totalCelulasLixo > 0){
                    m_matriz[i][j].setTipo(EstadosNodo.celulaSuja);
                    totalCelulasLixo--;
                }
                                

                //aqui tinha um else pra botar celular vazias, mas agora elas são criadas já vazias, o que não alterado, é vazio
            }    
        }
        colocarLixeiras();
        colocarRecargas();
        
        jamesBond = new Agente(posAgente,100,100);
        m_matriz[posAgente[0]][posAgente[1]].setTipo(EstadosNodo.agente);
        
        desenhaAmbiente();
        
        
    }
    
    public void desenhaAmbiente(){
        for(int i = 0; i < tamanho; i++){
            System.out.print("\n");
            for(int j = 0; j < tamanho; j++) {
                System.out.print(m_matriz[i][j].getTipo());
            }
        }
        System.out.print("\n");
    }
    
    public void colocarLixeiras(){
       // System.out.print("Número de lixeiras: " + qtdLixeiras + "\n");
        while (qtdLixeiras > 0){
            for(int i = 0; i < tamanho; i++){
                for(int j = 0; j < tamanho; j++) {
                    //System.out.print("Colocar Lixeiras    I: " + i + "    J:" + j+ "\n");
                    if ((i > 2 && i <tamanho-3)//dentro das linhas das paredes 
                            && (j < 3 || j > tamanho-4)){//dentro das colunas das paredes
                        if((Math.random() * 1) > 0.7  //0,7 pra ficar mais espalhado
                                && m_matriz[i][j].estaVazio()//arrumar isso depois 
                                    && qtdLixeiras>0){  
                            m_matriz[i][j].setTipo(EstadosNodo.lixeira);
                            qtdLixeiras--;
                        }
                    }
                }
            }
        }
    }
    
    public void colocarRecargas(){
        //System.out.print("Número de recargas: " + qtdRecargas + "\n");
        while (qtdRecargas > 0){
            for(int i = 0; i < tamanho; i++){
                for(int j = 0; j < tamanho; j++) {
                    if ((i > 2 && i <tamanho-3)//dentro das linhas das paredes 
                            && (j < 3 || j > tamanho-4)){//dentro das colunas das paredes
                        if((Math.random() * 1) > 0.7  //0,7 pra ficar mais espalhado
                                && m_matriz[i][j].getTipo().equals("[ ]") //arrumar isso depois 
                                    && qtdRecargas>0){ 
                             m_matriz[i][j].setTipo(EstadosNodo.recarga);
                            qtdRecargas--;
                        }
                    }
                }
            }
        }
    }
    
    public List<Nodo> adjascentesDoAgente (){
        List<Nodo> nodosAdjascentes;
        nodosAdjascentes = new ArrayList<Nodo>();
        int posicaoI = jamesBond.getPosicaoI();
        int posicaoJ = jamesBond.getPosicaoJ();
        
        //nodos superiores
        if (posicaoI != 0) {//se não está na primeira linha, tem nodos em cima
            nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ]);
            if (posicaoJ != 0){//se não está primeira coluna, tem nodos em cima a esquerda 
                nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ-1]);
            }
            if (posicaoJ != tamanho){//se não está ultima coluna, tem nodos em cima a direita 
                nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ+1]);
            }
        }
        
        //nodos inferiores
        if (posicaoI != tamanho) {//se não está na ultima linha, tem nodos embaixo
            nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ]);
            if (posicaoJ != 0){//se não está primeira coluna, tem nodos embaixo a esquerda 
                nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ-1]);
            }
            if (posicaoJ != tamanho){//se não está ultima coluna, tem nodos embaixo a direita 
                nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ+1]);
            }
        }
        
        //nodos laterais
        if (posicaoJ != 0){//se não está primeira coluna, tem nodos a esquerda 
                nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ]);
        }
        if (posicaoJ != 0){//se não está ultima coluna, tem nodos a direita 
                nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ]);
        }
        
        return nodosAdjascentes;
    }
    
}
