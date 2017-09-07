import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Agente {
    
  public enum EstadosAgente { 
                              Ocioso, Andando, AndandoUltimaCelulaVisitada, 
                              VisitandoCelula, RecolhendoLixo, JogandoLixoFora, 
                              Recarregando
                            }

  private EstadosAgente m_estadoAtual;

  private int[] m_pos;
  private int m_repositorio;
  private int m_bateria;

  public Agente(int[] pos, int tamRepositorio, int tamBateria) {

    m_pos         = pos;
    m_repositorio = tamRepositorio;
    m_bateria     = tamBateria;

    EntrarEstado(EstadosAgente.Ocioso);
  }

  public void EntrarEstado(EstadosAgente novoEstado) {

    m_estadoAtual = novoEstado;

    switch(m_estadoAtual) {

      case Ocioso: {

      }
      break;

      case Andando: {

      }
      break;
    }
  }

  public int[] getPosicao() {
    
    return m_pos;
  }

  public int getPosicaoI() {

    return m_pos[0];
  }

  public int getPosicaoJ() {

    return m_pos[1];
  }
  
  public void SetPosicao (Nodo nodoCur, Nodo nodoNovo) {
    
    nodoCur.SetEstado(Nodo.EstadosNodo.celulaVazia);
    
    m_pos = nodoNovo.GetPos();
  }

  public List<String> Random_path(Ambiente ambiente) {
  
    List<String> movimentos = new ArrayList<>();

    Nodo ultimoNodo = null;   // ultimo nodo visitado;

    while(!ambiente.AmbienteLimpo()) {
    
      Nodo curNodo = ambiente.GetNodo(m_pos);
      curNodo.setVisitado();

      if(curNodo.estaSujo())
        ambiente.LimparCelula(m_pos);

      curNodo.SetEstado(Nodo.EstadosNodo.agente);

      ambiente.desenhaAmbiente();

      if(ambiente.AmbienteLimpo())
        break;

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

      if(destino != null) { // executa uma acao se ele existir

        ultimoNodo = curNodo;

        SetPosicao(curNodo, destino);

        //ambiente.desenhaAmbiente();
      }
    }
    
    return movimentos;
  }
  
  public int[] A_Star_path() {
  
    //PriorityQueue<Nodo> listaAberta = new PriorityQueue<>(
    
    return new int[] {};
  }
}
