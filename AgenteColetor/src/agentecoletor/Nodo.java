/**
 *
 * @author BrunoPaz e MarlonFontoura
 */
public class Nodo {
    
  public enum EstadosNodo { celulaVazia, celulaSuja, parede, lixeira, recarga, agente }

  private EstadosNodo m_estado;
  private String tipo = "[ ]";

  private int m_custoG;
  private int m_custoH;
  private int m_custoF;

  private int[] m_posicao;
  public int m_posI;
  public int m_posJ;
  
  private boolean m_visitado;
  
  public Nodo (int[] pos){

    m_visitado = false;
    
    m_posicao = pos;
    m_posI = pos[0];
    m_posJ = pos[1];
    
    SetEstado(EstadosNodo.celulaVazia);
  }

  public void SetEstado(EstadosNodo novoEstado) {

    m_estado = novoEstado;

    switch (m_estado) {

      case celulaVazia: tipo = "[ ]"; break;
      case celulaSuja:  tipo = "[s]"; break;
      case parede:      tipo = "[X]"; break;
      case lixeira:     tipo = "[L]"; break;
      case recarga:     tipo = "[R]"; break;
      case agente:      tipo = "[A]"; break;
    }
  }

  public EstadosNodo GetEstado() { return m_estado; }

  public int[] GetPos() { return m_posicao; }
  
  public boolean bloqueado() {
    
    return m_estado == EstadosNodo.parede
        || m_estado == EstadosNodo.lixeira
        || m_estado == EstadosNodo.recarga;
  }

  public boolean ehParede() { return m_estado == EstadosNodo.parede; }

  public boolean estaVazio() { return m_estado == EstadosNodo.celulaVazia; }

  public boolean estaSujo() { return m_estado == EstadosNodo.celulaSuja; }

  public void limpar() { SetEstado(EstadosNodo.agente); }

  public void setVisitado() { m_visitado = true; }
  public boolean jaVisitado() { return m_visitado; }
  
  public void SetCusto(int custoG, int custoH) {

    m_custoG = custoG;
    m_custoH = custoH;

    m_custoF = m_custoG + m_custoH;
  }

  public String ToString() {

    if(m_visitado && m_estado == EstadosNodo.celulaVazia)
      tipo = "[']";


    return tipo;
  }
}
