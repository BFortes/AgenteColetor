package agentecoletor;

/**
 *
 * @author 10100335
 */

public class Agente {
    
    enum EstadosAgente { Ocioso, Andando, AndandoUltimaCelulaVisitada, 
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
}
