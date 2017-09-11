import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Agente {
  
  private Point m_pos;
  private int m_repositorio;
  private int m_bateria;

  private final int m_totalRepositorio;
  private final int m_totalBateria;

  private final int MIN_BATERIA = 20;

  public Agente(Point pos, int tamRepositorio, int tamBateria) {

    m_pos = pos;

    m_totalRepositorio = tamRepositorio;
    m_totalBateria = tamBateria;
    m_repositorio = 0;
    m_bateria     = tamBateria;
  }

  public Point getPosicao() {
    
    return m_pos;
  }

  public int getPosicaoI() {

    return m_pos.i;
  }

  public int getPosicaoJ() {

    return m_pos.j;
  }

  public void limpaLixo(Ambiente ambiente) {

    ambiente.LimparCelula(m_pos);

    m_bateria--;
    m_repositorio++;
  }

  public void SetPosicao (Nodo nodoCur, Nodo nodoNovo) {
    
    nodoCur.SetEstado(Nodo.EstadosNodo.celulaVazia);
    
    m_pos = nodoNovo.GetPos();

    m_bateria--;
  }

  public void Random_path(Ambiente ambiente) throws CloneNotSupportedException{
  
    List<String> movimentos = new ArrayList<>();

    Nodo ultimoNodo = null;   // ultimo nodo visitado;

    while(!ambiente.AmbienteLimpo()) {
    
      Nodo curNodo = ambiente.GetNodo(m_pos);
      curNodo.setVisitado();

      if(curNodo.estaSujo())
        limpaLixo(ambiente);

      curNodo.SetEstado(Nodo.EstadosNodo.agente);

      ambiente.desenhaAmbiente();

      if(ambiente.AmbienteLimpo())
        break;

      // A*
      if(m_bateria <= MIN_BATERIA) {

        System.out.println("\n****** Recarregar bateria: " + m_bateria + " ******");

        Point destino = ambiente.GetRecargaProxima(m_pos);
        if(A_Star_path((Ambiente)ambiente.clone(), m_pos, destino)) {

          m_bateria = m_totalBateria;
        }
        else {

          System.out.println("ERRO A*: SOLUÇAO NAO ENCONTRADA" );

          System.exit(0);
        }
      }

      if(m_repositorio == m_totalRepositorio) {

        System.out.println("\n****** Depositar lixo: " + m_repositorio + " ******");

        Point destino = ambiente.GetLixeiraProxima(m_pos);
        if(A_Star_path((Ambiente)ambiente.clone(), m_pos, destino)) {

          m_repositorio = 0;
        }
        else {

          System.out.println("ERRO A*: SOLUÇAO NAO ENCONTRADA" );

          System.exit(0);
        }
      }

      List<Nodo> vizinhos = ambiente.GetVizinhos(curNodo, false);
      Nodo destino = null;

      for(int n = 0; n < vizinhos.size(); n++) {
        
        Nodo vizinho = vizinhos.get(n);

        if(vizinhos.size() == 1) { // nao tem escolha

          destino = vizinho;

          if(!vizinho.jaVisitado())
            break;
        }
        else { // escolhe um nodo diferente do anterior

          if(ultimoNodo != null && vizinho.m_posI == ultimoNodo.m_posI && vizinho.m_posJ == ultimoNodo.m_posJ)
            continue;

          if(vizinho.m_posJ > curNodo.m_posJ && vizinho.m_posI == curNodo.m_posI) { // direita

            destino = vizinho;

            if(!vizinho.jaVisitado())
              break;
          }
          else if(vizinho.m_posI > curNodo.m_posI && vizinho.m_posJ == curNodo.m_posJ) { // baixo

            destino = vizinho;

            if(!vizinho.jaVisitado())
              break;
          }
          else if(vizinho.m_posJ < curNodo.m_posJ && vizinho.m_posI == curNodo.m_posI) { // esquerda

            destino = vizinho;

            if(!vizinho.jaVisitado())
              break;
          }
          else if(vizinho.m_posI < curNodo.m_posI && vizinho.m_posJ == curNodo.m_posJ) { // cima

            destino = vizinho;

            if(!vizinho.jaVisitado())
              break;
          }
        }
      }

      if(destino != null) { // executa uma acao se ela existir

        ultimoNodo = curNodo;

        SetPosicao(curNodo, destino);

        //ambiente.desenhaAmbiente();
      }
    }
  }

  public boolean A_Star_path(Ambiente ambiente, Point origem, Point destino) {

    System.out.print("****** Origem: " + origem.ToString() + " Destino: " + destino.ToString() + " ******");

    // a ordena de acordo com a prioridade de cada nodo, que nao foram visitados ainda
    PriorityQueue<Nodo> listaAberta = new PriorityQueue<Nodo>(new Comparator<Nodo>() {
      @Override
      public int compare(Nodo n1, Nodo n2) {
        return n1.GetCustoF() < n2.GetCustoF() ? -1 : n1.GetCustoF() > n2.GetCustoF() ? 1 : 0;
      }
    });

    // nodos ja visitados
    List<Nodo> listaFechada = new ArrayList<>();

    Nodo nodoOrigem = ambiente.GetNodo(origem);
    nodoOrigem.SetCusto(1, manhattan_distance(origem, destino));

    listaAberta.add(nodoOrigem);

    while(!listaAberta.isEmpty()) {

      Nodo curNodo = listaAberta.poll();
      listaFechada.add(curNodo);

      if(manhattan_distance(curNodo.GetPos(), destino) == 1) {

        ambiente.GetCaminho(curNodo);

        return true;
      }

      for(Nodo n : ambiente.GetVizinhos(curNodo, false)) {

        int custoG = curNodo.GetCustoG() + manhattan_distance(curNodo.GetPos(), n.GetPos());

        if(listaFechada.contains(n) && custoG >= n.GetCustoG())
          continue;

        if(!listaFechada.contains(n) || custoG < n.GetCustoG()) {

          n.origem = curNodo;
          n.SetCusto(custoG, manhattan_distance(n.GetPos(), destino));
          if(!listaAberta.contains(n))
            listaAberta.add(n);
        }
      }
    }

    return false;
  }

  public int manhattan_distance(Point p1, Point p2) {

    return Math.abs(p1.j - p2.j) + Math.abs(p1.i - p2.i);
  }
}
