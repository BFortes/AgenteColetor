import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Stack;

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

  public void DFS_path(Ambiente ambiente) throws CloneNotSupportedException {
    
    Stack stack = new Stack();
    
    Nodo nodoRaiz = ambiente.GetNodo(m_pos);
		nodoRaiz.setVisitado();
    
    stack.push(nodoRaiz);
    
		while(!stack.isEmpty()) {
      
			Nodo nodoCur = (Nodo)stack.peek();
			Nodo nodoFilho = null;
      
      if(nodoCur.estaSujo())
        limpaLixo(ambiente);
      
      nodoCur.SetEstado(Nodo.EstadosNodo.agente);
      
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
      
      List<Nodo> vizinhos = ambiente.GetVizinhos(nodoCur, false);
      for(int n = 0; n < vizinhos.size(); n++) {
      
        Nodo vizinho = vizinhos.get(n);
        if(!vizinho.jaVisitado()) {
          
          nodoFilho = vizinho;
          break;
        }
      }
      
			if(nodoFilho != null) {
        
        SetPosicao(nodoCur, nodoFilho);
        
				nodoFilho.setVisitado();
				stack.push(nodoFilho);
			}
			else {
        
        nodoCur.SetEstado(Nodo.EstadosNodo.celulaVazia);
        
				stack.pop();
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

      // agente esta ao lado do destino
      if(manhattan_distance(curNodo.GetPos(), destino) == 1) {

        ambiente.GetCaminho(curNodo);

        return true;
      }

      for(Nodo n : ambiente.GetVizinhos(curNodo, false)) {
        
        // calcula o custo do movimento ate este vizinho
        int custoG = curNodo.GetCustoG() + manhattan_distance(curNodo.GetPos(), n.GetPos());

        // caso este vizinho ja tenha sido explorado, e ja faça parte de um caminho com custo menor, ignora este passo.
        if(listaFechada.contains(n) && custoG >= n.GetCustoG())
          continue;

        // se este vizinho nunca foi visitado ou seu custo de movimento for melhor que o já calculado antes, atualiza nodo e listaaberta
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
