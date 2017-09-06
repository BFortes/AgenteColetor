package agentecoletor;
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
    
    while(!ambiente.AmbienteLimpo()) {
    
      Nodo nodoCur = ambiente.GetNodo(m_pos);
      
      if(nodoCur.estaSujo())
        ambiente.LimparCelula(m_pos);

      if(nodoCur.estaVazio())
        nodoCur.colocarAgente();
      
      if(ambiente.AmbienteLimpo())
        break;
      
      List<Nodo> vizinhos = ambiente.GetVizinhos(nodoCur);
      
      for(int n = 0; n < vizinhos.size(); n++) {
        
        Nodo vizinho = vizinhos.get(n);
        
        if(!vizinho.jaVisitado() && (vizinho.GetPos()[0] > nodoCur.GetPos()[0] && vizinho.GetPos()[1] == nodoCur.GetPos()[1])) {
          
          SetPosicao(nodoCur, vizinho);
        }
      }
      
      ambiente.desenhaAmbiente();
    }
    
    return movimentos;
  }
  
  public int[] A_Star_path() {
  
    //PriorityQueue<Nodo> listaAberta = new PriorityQueue<>(
    
    return new int[] {};
  }
}
