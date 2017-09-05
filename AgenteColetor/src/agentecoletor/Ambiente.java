package agentecoletor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Ambiente {

  private Nodo[][] m_matriz;
  private int tamanho, qtdLixeiras, qtdRecargas;
  private Agente jamesBond;

  public Ambiente(Agente agente, int tamanho, int qtdLixeiras, int qtdRecargas) {

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
        m_matriz[i][j] = new Nodo(new int[] {i,j});

        //desenhar parede da esquerda
        if(i == 2 && j == 2
            || i == tamanho-3 && j == 2
                || (i >= 2 && i <= tamanho-3) && j == 3)
          m_matriz[i][j].SetEstado(Nodo.EstadosNodo.parede);

        //desenhar parede da direita
        else if(i == 3 && j == tamanho-4
                || i == 2 && j == tamanho-3 
                  || i == tamanho-3 && j == tamanho-3
                    || (i >= 2 && i <= tamanho-3) && j == tamanho-4)
          m_matriz[i][j].SetEstado(Nodo.EstadosNodo.parede);

        //colocar lixos

        else if((Math.random() * 1) > 0.5 && totalCelulasLixo > 0) {

          m_matriz[i][j].SetEstado(Nodo.EstadosNodo.celulaSuja);
          totalCelulasLixo--;
        }
        
        //aqui tinha um else pra botar celular vazias, mas agora elas são criadas já vazias, o que não alterado, é vazio
      }    
    }
    colocarLixeiras();
    colocarRecargas();

    jamesBond = agente;
    m_matriz[agente.getPosicao()[0]][agente.getPosicao()[1]].SetEstado(Nodo.EstadosNodo.agente);

    desenhaAmbiente();    
  }

  public void colocarLixeiras() {
    
    // System.out.print("Número de lixeiras: " + qtdLixeiras + "\n");
    while (qtdLixeiras > 0) {

      for(int i = 0; i < tamanho; i++) {
        
        for(int j = 0; j < tamanho; j++) {
          
          //System.out.print("Colocar Lixeiras    I: " + i + "    J:" + j+ "\n");
          if ((i > 2 && i <tamanho-3)          //dentro das linhas das paredes 
                && (j < 3 || j > tamanho-4)) { //dentro das colunas das paredes
            
            if((Math.random() * 1) > 0.7  //0,7 pra ficar mais espalhado
                  && m_matriz[i][j].estaVazio()
                    && qtdLixeiras > 0) {  
              
              m_matriz[i][j].SetEstado(Nodo.EstadosNodo.lixeira);
              qtdLixeiras--;
            }
          }
        }
      }
    }
  }

  public void colocarRecargas() {

    //System.out.print("Número de recargas: " + qtdRecargas + "\n");
    while (qtdRecargas > 0) {

      for(int i = 0; i < tamanho; i++) {

        for(int j = 0; j < tamanho; j++) {

          if ((i > 2 && i <tamanho-3)          //dentro das linhas das paredes 
                && (j < 3 || j > tamanho-4)) { //dentro das colunas das paredes

            if((Math.random() * 1) > 0.7       //0,7 pra ficar mais espalhado
                  && m_matriz[i][j].estaVazio()
                    && qtdRecargas>0) { 

              m_matriz[i][j].SetEstado(Nodo.EstadosNodo.recarga);
              qtdRecargas--;
            }
          }
        }
      }
    }
  }

  public void desenhaAmbiente() {

    for(int i = 0; i < tamanho; i++) {

      System.out.print("\n");
      for(int j = 0; j < tamanho; j++) {

        System.out.print(m_matriz[i][j].ToString());
      }
    }

    System.out.print("\n");
  }
  
  public List<Nodo> adjascentesDoAgente () {

    List<Nodo> nodosAdjascentes;
    nodosAdjascentes = new ArrayList<Nodo>();
    int posicaoI = jamesBond.getPosicaoI();
    int posicaoJ = jamesBond.getPosicaoJ();

    //nodos superiores
    if (posicaoI != 0) {//se não está na primeira linha, tem nodos em cima
      
      nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ]);

      if (posicaoJ != 0) {//se não está primeira coluna, tem nodos em cima a esquerda 

        nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ-1]);
      }
      if (posicaoJ != tamanho) {//se não está ultima coluna, tem nodos em cima a direita 
        
        nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ+1]);
      }
    }

    //nodos inferiores
    if (posicaoI != tamanho) {//se não está na ultima linha, tem nodos embaixo
      
      nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ]);
      
      if (posicaoJ != 0) {//se não está primeira coluna, tem nodos embaixo a esquerda 
        
          nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ-1]);
      }
      if (posicaoJ != tamanho) {//se não está ultima coluna, tem nodos embaixo a direita 
        
          nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ+1]);
      }
    }

    //nodos laterais
    if (posicaoJ != 0){ //se não está primeira coluna, tem nodos a esquerda 
      
      nodosAdjascentes.add(m_matriz[posicaoI-1][posicaoJ]);
    }
    if (posicaoJ != 0) { //se não está ultima coluna, tem nodos a direita 
      
      nodosAdjascentes.add(m_matriz[posicaoI+1][posicaoJ]);
    }

    return nodosAdjascentes;
  }
  
  //sem diagonal
  public List<Nodo> GetVizinhos(Nodo nodo) {
    
    List<Nodo> vizinhos = new ArrayList<Nodo>();
    int x = nodo.GetPos()[0];
    int y = nodo.GetPos()[1];
    
    if (x > 0 && m_matriz[x-1][y].ehParede() == false) // esquerda
      vizinhos.add(m_matriz[x-1][y]);
    if (x < (tamanho-1) && m_matriz[x+1][y].ehParede() == false) // direita
      vizinhos.add(m_matriz[x+1][y]);
    if (y > 0 && m_matriz[x][y-1].ehParede() == false) // cima
      vizinhos.add(m_matriz[x][y-1]);
    if (y < (tamanho-1) && m_matriz[x][y+1].ehParede() == false) // baixo
      vizinhos.add(m_matriz[x][y+1]);
    
    return vizinhos;
  }
}
