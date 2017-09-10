import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Ambiente implements Cloneable {

  private Nodo[][] m_matriz;
  private int tamanho, qtdLixeiras, qtdRecargas, totalLixo;

  private List<Point> m_locRecargas;
  private List<Point> m_locLixeiras;

  public Ambiente(int tamanho, int qtdLixeiras, int qtdRecargas) {

    this.tamanho = tamanho;
    this.qtdLixeiras = qtdLixeiras;
    this.qtdRecargas = qtdRecargas;

    m_locLixeiras = new ArrayList<>();
    m_locRecargas = new ArrayList<>();

    totalLixo = 0;

    System.out.print("Total de Células: " + tamanho * tamanho + "\n");

    double qtdLixoPorcentagem = ((int) (Math.random() * (85 - 40))) + 40;
    System.out.print("Porcentagem de lixo: " + qtdLixoPorcentagem + "\n");

    int totalCelulasLixo = (int)( (tamanho*tamanho) * (qtdLixoPorcentagem/100));

    m_matriz = new Nodo[tamanho][tamanho];

    for(int i = 0; i < tamanho; i++) { //i: coluna, j: linha

      for(int j = 0; j < tamanho; j++) {
        
        //System.out.print("I: " + i + "    J:" + j+ "\n");
        m_matriz[i][j] = new Nodo(new Point(i,j));

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
  
  public void LimparCelula(Point pos) {
  
    m_matriz[pos.i][pos.j].limpar();
    
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
            int qtdParede = 0;
            for(Nodo v : GetTodosVizinhos(m_matriz[i][j], true)) {

              if(v.GetEstado() == Nodo.EstadosNodo.parede)
                qtdParede++;

              if(v.GetEstado() == Nodo.EstadosNodo.recarga || v.GetEstado() == Nodo.EstadosNodo.lixeira) {

                vizinhosVazio = false;

                break;
              }
            }

            if((Math.random() * 1) > 0.7  //0,7 pra ficar mais espalhado
                  && m_matriz[i][j].estaVazio()
                    && vizinhosVazio
                      && qtdParede != 1
                        && qtdLixeiras > 0) {

              m_locLixeiras.add(new Point(i,j));

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
            int qtdParede = 0;
            for(Nodo v : GetTodosVizinhos(m_matriz[i][j], true)) {

              if(v.GetEstado() == Nodo.EstadosNodo.parede)
                qtdParede++;

              if(v.GetEstado() == Nodo.EstadosNodo.recarga || v.GetEstado() == Nodo.EstadosNodo.lixeira) {

                vizinhosVazio = false;

                break;
              }
            }

            if((Math.random() * 1) > 0.7       //0,7 pra ficar mais espalhado
                  && m_matriz[i][j].estaVazio()
                    && vizinhosVazio
                      && qtdParede != 1
                        && qtdRecargas>0) {

              m_locRecargas.add(new Point(i,j));

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
  
  public Nodo GetNodo(Point pos) {
  
    return m_matriz[pos.i][pos.j];
  }

  public Point GetLixeiraProxima(Point agentePos) {

    int minDist = 99;
    Point ponto = agentePos;

    for(Point p : m_locLixeiras) {

      int dist = (int) Math.sqrt(Math.pow((agentePos.j - p.j), 2) + Math.pow((agentePos.i - p.i), 2));

      if(dist < minDist) {

        minDist = dist;

        ponto = p;
      }
    }

    return ponto;
  }

  public Point GetRecargaProxima(Point agentePos) {

    int minDist = 99;
    Point ponto = agentePos;

    for(Point p : m_locRecargas) {

      int dist = (int) Math.sqrt(Math.pow((agentePos.j - p.j), 2) + Math.pow((agentePos.i - p.i), 2));

      if(dist < minDist) {

        minDist = dist;

        ponto = p;
      }
    }

    return ponto;
  }

  // pega os vizinhos que podem ser acessados
  public List<Nodo> GetVizinhos(Nodo nodo, boolean diagonal) {
    
    List<Nodo> vizinhos = new ArrayList<>();
    int i = nodo.GetPos().i; // y
    int j = nodo.GetPos().j; // x

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
  public List<Nodo> GetTodosVizinhos(Nodo nodo, boolean diagonal) {

    List<Nodo> vizinhos = new ArrayList<>();
    int i = nodo.GetPos().i; // y
    int j = nodo.GetPos().j; // x

    if (j < (tamanho-1)) // direita
      vizinhos.add(m_matriz[i][j+1]);

    if (i < (tamanho-1)) // baixo
      vizinhos.add(m_matriz[i+1][j]);

    if (j > 0) // esquerda
      vizinhos.add(m_matriz[i][j-1]);

    if (i > 0) // cima
      vizinhos.add(m_matriz[i-1][j]);

    if(diagonal) {

      if (i > 0 && j < (tamanho-1)) // direita cima
        vizinhos.add(m_matriz[i-1][j+1]);

      if (i < (tamanho-1) && j < (tamanho-1)) // direita baixo
        vizinhos.add(m_matriz[i+1][j+1]);

      if (i > 0 && j > 0) // esquerda cima
        vizinhos.add(m_matriz[i-1][j-1]);

      if (i < (tamanho-1) && j > 0) // esquerda baixo
        vizinhos.add(m_matriz[i+1][j-1]);
    }

    return vizinhos;
  }

  public String GetDir(Point p1, Point p2) {

    if(p1.j < p2.j)
      return ">";
    else if (p1.j > p2.j)
      return "<";
    else if (p1.i < p2.i)
      return "v";
    else if (p1.i > p2.i)
      return "^";

    return "0";
  }

  public List<String> GetCaminho(Nodo curNodo) {

    List<String> caminho = new ArrayList<>();
    List<Nodo> nodos = new ArrayList<>();

    Nodo proxNodo;
    while((proxNodo = curNodo.origem) != null) {

      nodos.add(curNodo);

      caminho.add(0, GetDir(proxNodo.GetPos(), curNodo.GetPos()));
      curNodo = proxNodo;
    }

    System.out.print("\n****** Passos: " + caminho.size());

    for(int i = 0; i < tamanho; i++) {

      System.out.print("\n");
      for(int j = 0; j < tamanho; j++) {

        if(nodos.contains(m_matriz[i][j]))
          System.out.print("["+ GetDir(m_matriz[i][j].origem.GetPos(), m_matriz[i][j].GetPos()) +"]");
        else
          System.out.print(m_matriz[i][j].ToString());
      }
    }

    System.out.print("\n");

    return caminho;
  }

  public void ResetarNodos() {

    for(int i = 0; i < tamanho; i++) {

      for(int j = 0; j < tamanho; j++) {

        m_matriz[i][j].ResetNodo();
      }
    }
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {

    Ambiente objClone = (Ambiente)super.clone();
    objClone.ResetarNodos();
    return objClone;
  }
}
