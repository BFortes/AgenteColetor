import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Ambiente {

  private Nodo[][] m_matriz;
  private int tamanho, qtdLixeiras, qtdRecargas, totalLixo;

  public Ambiente(int tamanho, int qtdLixeiras, int qtdRecargas) {

    this.tamanho = tamanho;
    this.qtdLixeiras = qtdLixeiras;
    this.qtdRecargas = qtdRecargas;

    totalLixo = 0;

    System.out.print("Total de Células: " + tamanho * tamanho + "\n");

    double qtdLixoPorcentagem = ((int) (Math.random() * (85 - 40))) + 40;
    System.out.print("Porcentagem de lixo: " + qtdLixoPorcentagem + "\n");

    int totalCelulasLixo = (int)( (tamanho*tamanho) * (qtdLixoPorcentagem/100));

    m_matriz = new Nodo[tamanho][tamanho];

    for(int i = 0; i < tamanho; i++) { //i: coluna, j: linha

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

          totalLixo++;
        }
      }
    }

    System.out.print("Células com Lixo: " + totalLixo + "\n");

    colocarLixeiras();
    colocarRecargas();

    desenhaAmbiente();
  }

  public boolean AmbienteLimpo() {
  
    return totalLixo == 0;
  }
  
  public void LimparCelula(int[] pos) {
  
    m_matriz[pos[0]][pos[1]].limpar();
    
    totalLixo--;
  }

  public void colocarLixeiras() {

    // System.out.print("Número de lixeiras: " + qtdLixeiras + "\n");
    while (qtdLixeiras > 0) {

      for(int i = 0; i < tamanho; i++) {

        for(int j = 0; j < tamanho; j++) {

          //System.out.print("Colocar Lixeiras    I: " + i + "    J:" + j+ "\n");
          if ((i > 2 && i <tamanho-3)          //dentro das linhas das paredes
                && (j < 3 || j > tamanho-4)) { //dentro das colunas das paredes

            // tenta evitar colocar lixeiras perto de outras lixeiras e recargas
            boolean vizinhosVazio = true;
            for(Nodo v : GetTodosVizinhos(m_matriz[i][j])) {

              if(v.GetEstado() == Nodo.EstadosNodo.recarga || v.GetEstado() == Nodo.EstadosNodo.lixeira) {

                vizinhosVazio = false;

                break;
              }
            }

            if((Math.random() * 1) > 0.7  //0,7 pra ficar mais espalhado
                  && m_matriz[i][j].estaVazio()
                    && vizinhosVazio
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

            // tenta evitar colocar recargas perto de outras lixeiras e recargas
            boolean vizinhosVazio = true;
            for(Nodo v : GetTodosVizinhos(m_matriz[i][j])) {

              if(v.GetEstado() == Nodo.EstadosNodo.recarga || v.GetEstado() == Nodo.EstadosNodo.lixeira) {

                vizinhosVazio = false;

                break;
              }
            }

            if((Math.random() * 1) > 0.7       //0,7 pra ficar mais espalhado
                  && m_matriz[i][j].estaVazio()
                    && vizinhosVazio
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
  
  public Nodo GetNodo(int[] pos) {
  
    return m_matriz[pos[0]][pos[1]];
  }

  // pega os vizinhos que podem ser acessados
  public List<Nodo> GetVizinhos(Nodo nodo, boolean diagonal) {
    
    List<Nodo> vizinhos = new ArrayList<>();
    int i = nodo.GetPos()[0]; // y
    int j = nodo.GetPos()[1]; // x

    if (j < (tamanho-1) && !m_matriz[i][j+1].bloqueado()) // direita
      vizinhos.add(m_matriz[i][j+1]);

    if (i < (tamanho-1) && !m_matriz[i+1][j].bloqueado()) // baixo
      vizinhos.add(m_matriz[i+1][j]);

    if (j > 0 && !m_matriz[i][j-1].bloqueado()) // esquerda
      vizinhos.add(m_matriz[i][j-1]);

    if (i > 0 && !m_matriz[i-1][j].bloqueado()) // cima
      vizinhos.add(m_matriz[i-1][j]);

    if(diagonal) {

      if (i > 0 && j < (tamanho-1) && !m_matriz[i-1][j+1].bloqueado()) // direita cima
        vizinhos.add(m_matriz[i-1][j+1]);

      if (i < (tamanho-1) && j < (tamanho-1) && !m_matriz[i+1][j+1].bloqueado()) // direita baixo
        vizinhos.add(m_matriz[i+1][j+1]);

      if (i > 0 && j > 0 && !m_matriz[i-1][j-1].bloqueado()) // esquerda cima
        vizinhos.add(m_matriz[i-1][j-1]);

      if (i > (tamanho-1) && j > 0 && !m_matriz[i+1][j-1].bloqueado()) // esquerda baixo
        vizinhos.add(m_matriz[i+1][j-1]);
    }

    return vizinhos;
  }

  // pega todos os vizinhos sem excecao
  public List<Nodo> GetTodosVizinhos(Nodo nodo) {

    List<Nodo> vizinhos = new ArrayList<>();
    int i = nodo.GetPos()[0]; // y
    int j = nodo.GetPos()[1]; // x

    if (j < (tamanho-1)) // direita
      vizinhos.add(m_matriz[i][j+1]);

    if (i < (tamanho-1)) // baixo
      vizinhos.add(m_matriz[i+1][j]);

    if (j > 0) // esquerda
      vizinhos.add(m_matriz[i][j-1]);

    if (i > 0) // cima
      vizinhos.add(m_matriz[i-1][j]);

    if (i > 0 && j < (tamanho-1)) // direita cima
      vizinhos.add(m_matriz[i-1][j+1]);

    if (i < (tamanho-1) && j < (tamanho-1)) // direita baixo
      vizinhos.add(m_matriz[i+1][j+1]);

    if (i > 0 && j > 0) // esquerda cima
      vizinhos.add(m_matriz[i-1][j-1]);

    if (i < (tamanho-1) && j > 0) // esquerda baixo
      vizinhos.add(m_matriz[i+1][j-1]);

    return vizinhos;
  }
}
