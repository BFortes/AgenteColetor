package agentecoletor;

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

  public Nodo (EstadosNodo estado){

    SetEstado(estado);
  }

  public void SetEstado(EstadosNodo novoEstado) {

    if(m_estado == novoEstado)
      return;

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

  public boolean estaVazio() {

    return m_estado == EstadosNodo.celulaVazia;
  }

  public void SetCusto(int custoG, int custoH) {

    m_custoG = custoG;
    m_custoH = custoH;

    m_custoF = m_custoG + m_custoH;
  }

  public String ToString() {

    return tipo;
  }
}
